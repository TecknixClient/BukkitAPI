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
import com.tecknix.api.network.packets.TCPacketNotification;
import org.bukkit.entity.Player;

public class TCNotification {

    private final TCPacketNotification packet;

    /**
     * Notification using type, content and time.
     *
     * @param type Notification type. (Possible types in the below enum)
     * @param content Notification content as a string. This allows a maximum length of 200 characters.
     * @param time Notification time in seconds. This can be as long as you would like.
     */
    public TCNotification(Type type, String content, int time) {
        this.packet = new TCPacketNotification(type, content, time);
    }

    /**
     * Sends a notification packet to a bukkit player.
     *
     * @param player The bukkit player entity.
     */
    public void sendPacket(Player player) {
        TecknixAPI.getInstance().sendPacket(player, packet);
    }

    public enum Type {
        INFO, WARNING, ERROR
    }
}
