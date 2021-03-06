package com.bitkermessage.api.messages.responses;

import com.bitkermessage.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class MessageReceived extends Response {

    public InetAddress addr;
    public ByteBuffer msg;

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
        this.msg = Util.readInfo(msg);
    }


    @Override
    ByteBuffer write() {
        ByteBuffer tmp = ByteBuffer.allocate(16 + msg.limit());
        Util.writeAddr(addr,tmp);
        tmp.put(msg);
        return tmp;
    }
}
