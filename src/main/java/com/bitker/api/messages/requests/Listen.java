package com.bitker.api.messages.requests;

import java.nio.ByteBuffer;
import java.util.HashSet;
import java.util.Set;

public class Listen extends Request {

    public Set<String> types;
    public byte what;

    public Listen(){
        types = new HashSet<>();
    }

    public void addType(String type){
        types.add(type);
    }

    public void setWhat(byte what){
        this.what = what;
    }

    public int getWhat() {
        return what;
    }

    @Override
    public void read(ByteBuffer msg) {
       int n = msg.getInt();
       types = new HashSet<>(n);
       for(int i = 0; i < n; i++)
       {
           byte [] bytes = new byte[12];
           msg.get(bytes);
           String s = new String(bytes).trim();
           types.add(s);
       }
       what = msg.get();
    }

    @Override
    public ByteBuffer write() {
        ByteBuffer msg = ByteBuffer.allocate(4 + 12*types.size() + 1);
        msg.putInt(types.size());
        types.forEach(s -> {
            byte [] bytes = new byte[12];
            int i = 0;
            for(byte b : s.getBytes())
            {
                if(i == 12)
                    return;
                bytes[i] = b;
                i++;
            }
            msg.put(bytes);
        });
        msg.put(what);
        return msg;
    }
}
