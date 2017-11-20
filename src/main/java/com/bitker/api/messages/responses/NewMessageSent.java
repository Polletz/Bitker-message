package com.bitker.api.messages.responses;

import com.bitker.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class NewMessageSent extends Response {

    InetAddress addr;
    String type;

    @Override
    void read(ByteBuffer msg) {
        byte [] buf = new byte [16];
        msg.get(buf);
        try
        {
            addr = InetAddress.getByAddress(buf);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        buf = new byte [12];
        msg.get(buf);
        type = String.valueOf(buf).trim();
    }

    @Override
    ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(16 + 12);
        Util.writeAddr(addr, msg);
        byte [] buf = new byte [12];
        int i = 0;
        for(byte b : type.getBytes())
        {
            buf[i] = b;
            i++;
        }
        msg.put(buf);
        return msg;
    }
}