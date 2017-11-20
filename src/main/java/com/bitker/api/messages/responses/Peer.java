package com.bitker.api.messages.responses;

import com.bitker.Util;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;

public class Peer {

    public InetAddress addr;
    public byte service;
    public boolean incoming;
    public String agent;

    public void read(ByteBuffer msg) throws UnknownHostException {
        byte [] buf = new byte [16];
        msg.get(buf);
        addr = InetAddress.getByAddress(buf);
        service = msg.get();
        incoming = msg.get() == 1;
        int l = msg.getInt();
        buf = new byte [l];
        msg.get(buf);
        agent = String.valueOf(buf);
    }

    public void write(ByteBuffer msg){
        Util.writeAddr(addr,msg);
        msg.put(service);
        msg.put(incoming ? (byte) 1 : (byte) 0);
        msg.putInt(agent.length());
        msg.put(agent.getBytes());
    }

    public int size() {
        return 16 + 1 + 1 + 4 + agent.length();
    }
}
