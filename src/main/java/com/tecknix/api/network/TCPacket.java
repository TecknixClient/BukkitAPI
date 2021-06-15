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
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package com.tecknix.api.network;

import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.tecknix.api.TecknixAPI;
import com.tecknix.api.network.packets.TCPacketCpsCooldown;
import com.tecknix.api.network.packets.TCPacketNotification;
import com.tecknix.api.network.packets.TCPacketWaypointAdd;
import com.tecknix.api.network.packets.TCPacketWaypointRemove;
import io.netty.buffer.Unpooled;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter @SuppressWarnings("rawtypes")
public abstract class TCPacket {

    private static final BiMap<Class, Integer> PACKET_MAP = HashBiMap.create();

    public abstract void write(PacketBuffer buf);
    public abstract void read(PacketBuffer buf);

    @SuppressWarnings("deprecation")
    public static TCPacket handle(byte[] data) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.wrappedBuffer(data));
        Class packetClass = PACKET_MAP.inverse().get(packetBuffer.readInt());

        if (packetClass == null) {
            return null;
        }

        try {
            // Reads the packets bytes.
            TCPacket tecknixPacket = (TCPacket) packetClass.newInstance();
            tecknixPacket.read(packetBuffer);
            return tecknixPacket;
        } catch (IllegalAccessException | InstantiationException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public static byte[] getPacketContent(TCPacket packet) {
        PacketBuffer packetBuffer = new PacketBuffer(Unpooled.buffer());
        // Writes the packet bytes.
        packetBuffer.writeInt(PACKET_MAP.get(packet.getClass()));
        packet.write(packetBuffer);

        return packetBuffer.getBuf().array();
    }

    private static void register(int packetId, Class packetClass) {
        if (PACKET_MAP.containsKey(packetClass)) {
            TecknixAPI.getInstance().getLogger().warning("This class has already been used by: " + PACKET_MAP.get(packetClass) + ".");
        } else if (PACKET_MAP.containsValue(packetId)) {
            TecknixAPI.getInstance().getLogger().warning("This packet packetId has been used already: " + PACKET_MAP.inverse().get(packetId));
        }
        PACKET_MAP.put(packetClass, packetId);
    }

    static {
        register(0, TCPacketNotification.class);
        register(1, TCPacketWaypointAdd.class);
        register(2, TCPacketWaypointRemove.class);
        register(3, TCPacketCpsCooldown.class);
    }
}
