package com.bitkermessage.api.messages.requests;

import com.bitkermessage.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class SendTo extends Send {

    public InetAddress addr;

    public void setAddr(InetAddress addr) {
        this.addr = addr;
    }

    @Override
    public void read(ByteBuffer msg) {
        byte [] bytes = new byte [16];
        msg.get(bytes);
        try
        {
            addr = InetAddress.getByAddress(bytes);
        } catch (UnknownHostException e)
        {
            e.printStackTrace();
        }
        this.msg = Util.readInfo(msg);
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer tmp = ByteBuffer.allocate(16 + msg.limit());
        Util.writeAddr(addr,tmp);
        tmp.put(msg);
        return tmp;
    }
}
