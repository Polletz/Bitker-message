package com.bitker.api.messages.responses;

import java.nio.ByteBuffer;

public class MessageSent extends Response {

    @Override
    void read(ByteBuffer msg) {

    }

    @Override
    ByteBuffer write() {
        return null;
    }
}
