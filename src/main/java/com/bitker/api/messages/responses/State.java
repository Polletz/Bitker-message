package com.bitker.api.messages.responses;

import com.bitker.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class State extends Response {

    public InetAddress addr;
    public byte oldState;
    public byte newState;
    public long time;

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
        oldState = msg.get();
        newState = msg.get();
        if(newState == 0)
            time = msg.getLong();
    }

    @Override
    ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(16 + 1 + 1 + newState == 0 ? 8 : 0);
        Util.writeAddr(addr,msg);
        msg.put(oldState);
        msg.put(newState);
        if(newState == 0)
            msg.putLong(time);
        return msg;
    }
}
