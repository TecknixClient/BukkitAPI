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

package com.tecknix.api.object;

import com.tecknix.api.TecknixAPI;
import com.tecknix.api.network.packets.TCPacketWaypointAdd;
import com.tecknix.api.network.packets.TCPacketWaypointRemove;
import org.bukkit.entity.Player;

import java.awt.*;

public class TCWaypoint {

    private final TCPacketWaypointAdd addPacket;
    private final TCPacketWaypointRemove removePacket;

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
    public TCWaypoint(String name, String world, String server, Integer x, Integer y, Integer z, Integer red, Integer green, Integer blue) {
        this.addPacket = new TCPacketWaypointAdd(name, world, server, x, y, z, red, green, blue);
        this.removePacket = new TCPacketWaypointRemove(name, world, server);
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
    public TCWaypoint(String name, String world, String server, Integer x, Integer y, Integer z, Integer colorInt) {
        Color color = new Color(colorInt);
        this.addPacket = new TCPacketWaypointAdd(name, world, server, x, y, z, color.getRed(), color.getGreen(), color.getBlue());
        this.removePacket = new TCPacketWaypointRemove(name, world, server);
    }

    /**
     * Sends a waypoint add packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendAddPacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.addPacket);
    }

    /**
     * Sends a waypoint remove packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendRemovePacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.removePacket);
    }
}
