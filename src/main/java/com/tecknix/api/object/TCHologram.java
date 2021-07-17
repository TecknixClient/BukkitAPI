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
import com.tecknix.api.network.packets.TCPacketHologramAdd;
import com.tecknix.api.network.packets.TCPacketHologramRemove;
import org.bukkit.entity.Player;

public class TCHologram {

    private final TCPacketHologramAdd addPacket;
    private final TCPacketHologramRemove removePacket;

    /**
     * Create or remove a hologram.
     *
     * @param identifier This must be special to the hologram!
     * @param content The content of the hologram. (This accepts bukkit color codes)
     * @param x The X coordinate the hologram should be placed on.
     * @param y The Y coordinate the hologram should be placed on.
     * @param z The Z coordinate the hologram should be placed on.
     */
    public TCHologram(Integer identifier, String content, Double x, Double y, Double z) {
        this.addPacket = new TCPacketHologramAdd(identifier, content, x, y, z);
        this.removePacket = new TCPacketHologramRemove(identifier, content);
    }

    /**
     * Sends a hologram add packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendAddPacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.addPacket);
    }

    /**
     * Sends a hologram remove packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendRemovePacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, this.removePacket);
    }
}
