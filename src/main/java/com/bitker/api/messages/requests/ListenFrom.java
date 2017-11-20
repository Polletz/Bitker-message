package com.bitker.api.messages.requests;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public class ListenFrom extends Request {

    public Set<InetAddress> addrs;
    public byte what;

    public ListenFrom(){
        addrs = new HashSet<>();
    }

    public void addAddr(InetAddress addr){
        addrs.add(addr);
    }

    public void setWhat(byte what) {
        this.what = what;
    }

    @Override
    public void read(ByteBuffer msg) {
        int n = msg.getInt();
        addrs = new HashSet<>(n);
        for(int i = 0; i < n; i++)
        {
            byte [] bytes = new byte[16];
            msg.get(bytes);
            try
            {
                addrs.add(InetAddress.getByAddress(bytes));
            } catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
        }
        what = msg.get();
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(4 + 16*addrs.size() + 1);
        msg.putInt(addrs.size());
        addrs.forEach(inetAddress -> {
            if(inetAddress.getAddress().length == 4)
                msg.put(new byte[]{(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                        (byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,(byte)0x00,
                        (byte)0x00,(byte)0xFF,(byte)0xFF});
            msg.put(inetAddress.getAddress());
        });
        msg.put(what);
        return msg;
    }
}
