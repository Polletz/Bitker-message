package com.bitkermessage.api.messages.responses;

import java.nio.ByteBuffer;

public abstract class Response {

    private long id;

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    abstract void read(ByteBuffer msg);
    abstract ByteBuffer write();

}
