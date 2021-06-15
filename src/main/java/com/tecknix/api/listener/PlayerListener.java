/*
 * The MIT License (MIT)
 *
 * Copyright (C) 2021 Tecknix Software.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tecknix.api.listener;

import com.tecknix.api.TecknixAPI;
import com.tecknix.api.network.TCPacket;
import com.tecknix.api.network.packets.TCPacketCpsCooldown;
import com.tecknix.api.notification.TCNotification;
import com.tecknix.api.waypoint.TCWaypoint;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRegisterChannelEvent;
import org.bukkit.event.player.PlayerUnregisterChannelEvent;

import java.util.List;

@AllArgsConstructor @Getter
public class PlayerListener implements Listener {

    private final TecknixAPI plugin;
    private final TCNotification thankNotification = new TCNotification(TCNotification.Type.INFO, "Thanks for using Tecknix Client!", 5);

    @EventHandler(priority = EventPriority.LOWEST)
    public void onRegister(PlayerRegisterChannelEvent event) {
        // When a player registers check if the channel is Tecknix-Client. If it is, add the player to the list.
        if (event.getChannel().equals(this.plugin.getChannel()))  {
            this.plugin.getTecknixPlayers().add(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerJoinEvent event) {
        Bukkit.getScheduler().runTaskLater(plugin, () -> {

            final Player player = event.getPlayer();

            if (this.plugin.getTecknixPlayers().contains(player.getUniqueId())) {
                final List<TCPacket> queuedPackets = this.plugin.getQueuedPackets().remove(player.getUniqueId());

                if (queuedPackets != null) {
                    for (TCPacket packet : queuedPackets) {
                        // Send all of the queued packets to the client.
                        this.plugin.sendPacket(player, packet);
                    }
                }

                // Check if the thanks message is enabled.
                if (this.plugin.getConfig().getBoolean("thank-for-using-tecknix")) {
                    // Send the thank notification to the player defined above.
                    this.thankNotification.sendPacket(player);
                    // Send a little chat message.
                    player.sendMessage(ChatColor.DARK_PURPLE + "Thanks for using Tecknix Client!");
                }

                // Send the cps cooldown packet with the state defined in the config
                this.plugin.sendPacket(player, new TCPacketCpsCooldown(this.plugin.getConfig().getBoolean("disable-cps-cooldown")));

                if (!this.plugin.getWaypoints().isEmpty()) {
                    // for all of the loaded waypoints from the config, Send the add packet.
                    for (TCWaypoint waypoint : this.plugin.getWaypoints()) {
                        waypoint.sendAddPacket(player);
                    }
                }
            } else {
                // If the channel is not Tecknix-Client remove the packets from the queue.
                this.plugin.getQueuedPackets().remove(player.getUniqueId());
            }
        }, 2 * 20L);
    }

    @EventHandler
    public void onUnregister(PlayerUnregisterChannelEvent event) {
        // Only try to remove the player if they are on the Tecknix-Client Channel.
        if (event.getChannel().equals(this.plugin.getChannel()))  {
            this.plugin.getTecknixPlayers().remove(event.getPlayer().getUniqueId());
        }
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerQuit(PlayerQuitEvent event) {
        // Remove them once they leave, This is a second check as sometimes bukkit doesnt pass the unregister event.
        this.plugin.getTecknixPlayers().remove(event.getPlayer().getUniqueId());
    }
}
