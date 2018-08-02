package com.bitkermessage.api.messages.responses;


import java.nio.ByteBuffer;
import java.util.HashMap;

public class ResponseFactory {

    private HashMap<Integer, Class<? extends Response>> map;

    private static final ResponseFactory instance = new ResponseFactory();

    public static ResponseFactory getFactory() {
        return instance;
    }

    private ResponseFactory(){
        map = new HashMap<>();
        map.put(1, Acknowledge.class);
        map.put(2, MessageReceived.class);
        map.put(3, MessageSent.class);
        map.put(4, PeerList.class);
        map.put(5, State.class);
        map.put(6, Connect.class);
        map.put(7, NewConnection.class);
        map.put(8, NewMessageSent.class);
    }


    public Response createInstance(ByteBuffer msg) throws IllegalAccessException, InstantiationException {
        int type = msg.getInt();
        Response r = map.get(type).newInstance();
        long id = 0;
        if(type != 1)
           id = msg.getLong();
        r.read(msg);
        if(type != 1)
            r.setId(id);
        return r;
    }

}
