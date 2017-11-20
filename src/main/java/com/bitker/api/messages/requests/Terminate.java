package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;

public class Terminate extends Request {

    public long precReq;

    public void setPrecReq(long precReq) {
        this.precReq = precReq;
    }

    @Override
    public void read(ByteBuffer msg) {
        precReq = msg.getLong();
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(8);
        msg.putLong(precReq);
        return msg;
    }
}
