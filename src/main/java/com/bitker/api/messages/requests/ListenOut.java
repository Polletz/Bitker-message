package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;

public class ListenOut extends Request {


    @Override
    public void read(ByteBuffer msg) {

    }

    @Override
    public ByteBuffer write() {
        return null;
    }
}
