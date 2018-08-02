package com.bitkermessage.api.messages.responses;

import java.nio.ByteBuffer;

public class Acknowledge extends Response {

    public int n;
    public boolean ok;

    @Override
    public void read(ByteBuffer msg) {
        n = msg.getInt();
        if(msg.remaining() > 0)
        {
            setId(msg.getLong());
            ok = true;
        }
        else
            ok = false;
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(ok ? 4 + 8 : 4);
        msg.putInt(n);
        if(ok)
            msg.putLong(getId());
        return msg;
    }

}
