package com.tecknix.api.event;

import com.tecknix.api.network.TCPacket;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class TCPacketSendEvent extends PlayerEvent implements Cancellable {

    @Getter private static final HandlerList handlerList = new HandlerList();

    @Getter private final TCPacket packet;

    private boolean cancelled;

    /**
     * @param player The bukkit player entity.
     * @param packet The packet that is being sent.
     */
    public TCPacketSendEvent(Player player, TCPacket packet) {
        super(player);
        this.packet = packet;
    }

    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
