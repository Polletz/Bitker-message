package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;

public class Connection extends Request {


    @Override
    public void read(ByteBuffer msg) {

    }

    @Override
    public ByteBuffer write() {
        return null;
    }

}
