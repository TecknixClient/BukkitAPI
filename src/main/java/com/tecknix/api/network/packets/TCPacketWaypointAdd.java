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

package com.tecknix.api.network.packets;

import com.tecknix.api.network.PacketBuffer;
import com.tecknix.api.network.TCPacket;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class TCPacketWaypointAdd extends TCPacket {

    private final String name;
    private final String world;
    private final String server;

    private final int x;
    private final int y;
    private final int z;

    private final int red;
    private final int green;
    private final int blue;

    @Override
    public void write(PacketBuffer buf) {
        buf.writeString(this.name);
        buf.writeString(this.world);
        buf.writeString(this.server);
        buf.writeInt(this.x);
        buf.writeInt(this.y);
        buf.writeInt(this.z);
        buf.writeInt(this.red);
        buf.writeInt(this.green);
        buf.writeInt(this.blue);
    }

    @Override public void read(PacketBuffer buf) {}
}
