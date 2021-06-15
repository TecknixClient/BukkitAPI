package com.tecknix.api.waypoint;

import com.tecknix.api.TecknixAPI;
import com.tecknix.api.network.packets.TCPacketWaypointAdd;
import com.tecknix.api.network.packets.TCPacketWaypointRemove;
import org.bukkit.entity.Player;

import java.awt.*;

public class TCWaypoint {

    private final TCPacketWaypointAdd addWaypointPacket;
    private final TCPacketWaypointRemove removeWaypointPacket;

    /**
     * Waypoint using red, green and blue as separate integers..
     *
     * @param name The waypoints name. (case insensitive)
     * @param world The world the waypoint should be in. EX: Overworld.
     * @param server The server ip the waypoint should be on.
     * @param x The X coordinate the waypoint should be placed on.
     * @param y The Y coordinate the waypoint should be placed on.
     * @param z The Z coordinate the waypoint should be placed on.
     * @param red The red integer for the waypoints color.
     * @param green The green integer for the waypoints color.
     * @param blue The blue integer for the waypoints color.
     */
    public TCWaypoint(String name, String world, String server, int x, int y, int z, int red, int green, int blue) {
        this.addWaypointPacket = new TCPacketWaypointAdd(name, world, server, x, y, z, red, green, blue);
        this.removeWaypointPacket = new TCPacketWaypointRemove(name, world, server);
    }

    /**
     * Waypoint using red, green and blue as separate integers..
     *
     * @param name The waypoints name. (case insensitive)
     * @param world The world the waypoint should be in. EX: Overworld.
     * @param server The server ip the waypoint should be on.
     * @param x The X coordinate the waypoint should be placed on.
     * @param y The Y coordinate the waypoint should be placed on.
     * @param z The Z coordinate the waypoint should be placed on.
     * @param colorInt The color the waypoint should be as an integer.
     */
    public TCWaypoint(String name, String world, String server, int x, int y, int z, int colorInt) {
        Color color = new Color(colorInt);
        this.addWaypointPacket = new TCPacketWaypointAdd(name, world, server, x, y, z, color.getRed(), color.getGreen(), color.getBlue());
        this.removeWaypointPacket = new TCPacketWaypointRemove(name, world, server);
    }

    /**
     * Sends a waypoint add packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendAddPacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.addWaypointPacket);
    }

    /**
     * Sends a waypoint remove packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendRemovePacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.removeWaypointPacket);
    }
}
