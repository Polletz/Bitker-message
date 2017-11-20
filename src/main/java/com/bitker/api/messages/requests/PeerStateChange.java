package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;

public class PeerStateChange extends Request {

    byte what;

    public void setWhat(byte what) {
        this.what = what;
    }

    @Override
    public void read(ByteBuffer msg) {
        what = msg.get();
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(1);
        msg.put(what);
        return msg;
    }
}
