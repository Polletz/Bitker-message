package com.bitkermessage.api.messages.requests;

import java.nio.ByteBuffer;
import java.util.HashMap;

public class RequestFactory {

    private HashMap<Integer, Class<? extends Request>> map;

    private static final RequestFactory instance = new RequestFactory();

    public static RequestFactory getFactory() {
        return instance;
    }

    private RequestFactory(){
        map = new HashMap<>();
        map.put(1,Listen.class);
        map.put(2,ListenFrom.class);
        map.put(3,SendTo.class);
        map.put(4,SendToAll.class);
        map.put(5,List.class);
        map.put(6,Terminate.class);
        map.put(8,PeerStateChange.class);
        map.put(9,Connection.class);
        map.put(10,ListenOut.class);

    }

    public Request createInstance(ByteBuffer msg) throws IllegalAccessException, InstantiationException {
        int type = msg.getInt();
        long id = msg.getLong();
        Request r = map.get(type).newInstance();
        r.read(msg);
        r.setId(id);
        return r;
    }

}
