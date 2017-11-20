package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;

public abstract class Request {

   private long id;

   public void setId(long id) {
      this.id = id;
   }

   public long getId() {
      return id;
   }

   public abstract void read(ByteBuffer msg);
   public abstract ByteBuffer write();

}
