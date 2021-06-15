package com.tecknix.api.event;

import com.tecknix.api.network.TCPacket;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class TCPacketReceivedEvent extends PlayerEvent {

    @Getter private static final HandlerList handlerList = new HandlerList();

    @Getter private final TCPacket packet;

    /**
     * @param player The bukkit player entity.
     * @param packet The packet that is being received.
     */
    public TCPacketReceivedEvent(Player player, TCPacket packet) {
        super(player);
        this.packet = packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
}
