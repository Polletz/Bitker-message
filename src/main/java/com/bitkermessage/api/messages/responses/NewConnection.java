package com.bitkermessage.api.messages.responses;

import com.bitkermessage.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class NewConnection extends Response {

    public InetAddress addr;
    public boolean incoming;

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
        incoming = msg.get() == 1;

    }

    @Override
    ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(16 + 1);
        Util.writeAddr(addr,msg);
        msg.put(incoming ? (byte) 1 : (byte) 0);
        return msg;
    }
}
