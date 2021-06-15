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

package com.tecknix.api.network;

import com.google.common.base.Charsets;
import io.netty.buffer.ByteBuf;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class PacketBuffer {

    /*
     * Methods taken from net.minecraft.network.PacketBuffer.java.
     * This class is used to read and write values to and from bytes.
     * Example you can use readString() to read a string from bytes.
     */
    private final ByteBuf buf;

    public void writeInt(int b) {
        while ((b & 0xFFFFFF80) != 0x0) {
            this.buf.writeByte((b & 0x7F) | 0x80);
            b >>>= 7;
        }
        this.buf.writeByte(b);
    }

    public int readInt() {
        int i = 0;
        int chunk = 0;
        byte b;
        do {
            b = this.buf.readByte();
            i |= (b & 0x7F) << chunk++ * 7;
            if (chunk > 5) {
                throw new RuntimeException("Integer too big.");
            }
        } while ((b & 0x80) == 0x80);
        return i;
    }

    public void writeString(String s) {
        byte[] arr = s.getBytes(Charsets.UTF_8);
        this.writeInt(arr.length);
        this.buf.writeBytes(arr);
    }

    public void writeUUID(UUID uuid) {
        this.buf.writeLong(uuid.getLeastSignificantBits());
        this.buf.writeLong(uuid.getMostSignificantBits());
    }

    public String readString() {
        int len = this.readInt();
        byte[] buffer = new byte[len];

        this.buf.readBytes(buffer);
        return new String(buffer, Charsets.UTF_8);
    }

    public UUID readUUID() {
        long leastSignificantBits = this.buf.readLong();
        long mostSignificantBits = this.buf.readLong();
        return new UUID(mostSignificantBits, leastSignificantBits);
    }
}
