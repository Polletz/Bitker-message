package com.bitker;


import java.net.InetAddress;
import java.nio.ByteBuffer;

public class Util {

    public static ByteBuffer readInfo(ByteBuffer msg){
        ByteBuffer tmp = ByteBuffer.allocate(msg.remaining());
        tmp.put(msg);
        tmp.reset();
        return tmp;
    }

    public static void writeAddr(InetAddress addr, ByteBuffer msg){
        if(addr.getAddress().length == 4)
            msg.put(new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                    (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                    (byte)0x00,(byte)0xFF,(byte)0xFF});
        msg.put(addr.getAddress());
    }
}
