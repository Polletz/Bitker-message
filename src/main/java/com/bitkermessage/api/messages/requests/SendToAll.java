package com.bitkermessage.api.messages.requests;

import com.bitkermessage.Util;

import java.nio.ByteBuffer;

public class SendToAll extends Send {

    public int n;

    public void setN(int n) {
        this.n = n;
    }

    @Override
    public void read(ByteBuffer msg) {
        n = msg.getInt();
        this.msg = Util.readInfo(msg);
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer tmp = ByteBuffer.allocate(4 + msg.limit());
        tmp.putInt(n);
        tmp.put(msg);
        return tmp;
    }
}
