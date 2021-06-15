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

package com.tecknix.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.tecknix.api.command.ClientCommand;
import com.tecknix.api.event.TCPacketReceivedEvent;
import com.tecknix.api.event.TCPacketSendEvent;
import com.tecknix.api.listener.PlayerListener;
import com.tecknix.api.network.TCPacket;
import com.tecknix.api.waypoint.TCWaypoint;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.Configuration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.Messenger;

import java.util.*;

@Getter
public final class TecknixAPI extends JavaPlugin {

    public final static JsonParser JSON_PARSER = new JsonParser();

    @Getter private static TecknixAPI instance;

    private final String channel = "Tecknix-Client";
    private final Set<UUID> tecknixPlayers = new HashSet<>();

    // Used to hold all packets if the player hasn't registered yet
    // if they don't register in time all these packets will be voided.
    private final Map<UUID, List<TCPacket>> queuedPackets = new HashMap<>();

    // Store all waypoints the server has created
    private final List<TCWaypoint> waypoints = new ArrayList<>();

    @Override
    public void onEnable() {
        instance = this;

        final Configuration config = this.getConfig();
        final Messenger messenger = this.getServer().getMessenger();
        final PluginManager pluginManager = this.getServer().getPluginManager();

        this.saveDefaultConfig();

        messenger.registerOutgoingPluginChannel(this, channel);
        messenger.registerIncomingPluginChannel(this, channel, (channel, player, bytes) -> {
            TCPacket packet = TCPacket.handle(bytes);

            if (packet != null) {
                pluginManager.callEvent(new TCPacketReceivedEvent(player, packet));
            }
        });

        if (config.contains("waypoints")) {
            final List<Map<?, ?>> waypoints = config.getMapList("waypoints");

            for (Map<?, ?> map : waypoints) {
                for (Map.Entry<?, ?> entry : map.entrySet()) {
                    JsonObject object = JSON_PARSER.parse(String.valueOf(entry.getValue())).getAsJsonObject();

                    this.waypoints.add(new TCWaypoint(
                            object.get("name").getAsString(),
                            object.get("world").getAsString(),
                            object.get("server").getAsString(),
                            object.get("x").getAsInt(),
                            object.get("y").getAsInt(),
                            object.get("z").getAsInt(),
                            object.get("red").getAsInt(),
                            object.get("green").getAsInt(),
                            object.get("blue").getAsInt())
                    );
                }
            }
        }

        this.getCommand("client").setExecutor(new ClientCommand());

        pluginManager.registerEvents(new PlayerListener(this), this);
    }

    /**
     * Sends a packet to a specific player.
     *
     * @param player The bukkit player entity.
     * @param packet The packet that should be sent.
     */
    public void sendPacket(Player player, TCPacket packet) {
        if (this.tecknixPlayers.contains(player.getUniqueId())) {
            TCPacketSendEvent event = new TCPacketSendEvent(player, packet);
            this.getServer().getPluginManager().callEvent(event);

            if (event.isCancelled()) {
                return;
            }
            player.sendPluginMessage(this, this.channel, TCPacket.getPacketContent(packet));
        } else {
            this.queuedPackets.computeIfAbsent(player.getUniqueId(), v -> new ArrayList<>()).add(packet);
        }
    }

    /**
     * Checks if a player from bukkit is running Tecknix Client.
     *
     * @param player The bukkit player entity.
     */
    public boolean isOnTecknix(Player player) {
        return isOnTecknix(player.getUniqueId());
    }

    /**
     * Checks if a player from UUID is running Tecknix Client.
     *
     * @param playerId the players uuid.
     */
    public boolean isOnTecknix(UUID playerId) {
        return this.tecknixPlayers.contains(playerId);
    }
}
