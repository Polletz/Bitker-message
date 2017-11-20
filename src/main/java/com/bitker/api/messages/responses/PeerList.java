package com.bitker.api.messages.responses;

import java.net.UnknownHostException;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public class PeerList extends Response {

    public ArrayList<Peer> list;

    PeerList(){
        list = new ArrayList<>();
    }

    @Override
    void read(ByteBuffer msg) {
        int n = msg.getInt();
        for(int i = 0; i < n; i++)
        {
            Peer p = new Peer();
            try
            {
                p.read(msg);
            } catch (UnknownHostException e)
            {
                e.printStackTrace();
            }
            list.add(p);
        }
    }

    @Override
    ByteBuffer write() {
        int count = 0;
        for(Peer p : list)
            count+= p.size();
        ByteBuffer msg = ByteBuffer.allocate(4 + count);
        msg.putInt(list.size());
        for(Peer p : list)
            p.write(msg);
        return msg;

    }
}
