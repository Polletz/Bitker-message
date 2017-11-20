package com.bitker.api.messages.requests;

import com.bitker.protocol.bitio.LittleEndianOutputStream;
import com.bitker.protocol.messages.Message;
import io.nayuki.bitcoin.crypto.Sha256;

import java.io.IOException;
import java.nio.ByteBuffer;

abstract class Send extends Request {

    public ByteBuffer msg;

    public void setMsg(ByteBuffer msg) {
        this.msg = msg;
    }

    public void setMsg(Message m){
        msg = ByteBuffer.allocate((int) (24 + m.getLength()));
        msg.put(new byte [] {(byte) 0xF9, (byte) 0xBE, (byte) 0xB4, (byte) 0xD9});
        msg.put(m.getCommand().getBytes());
        msg.position(16);
        msg.put(intToByteArray(m.getLength()));
        msg.position(24);
        try
        {
            m.write(LittleEndianOutputStream.wrap(msg));
        } catch (IOException e)
        {
            e.printStackTrace();
        }
        msg.position(20);
        ByteBuffer tmp = ByteBuffer.wrap(msg.array(),24, (int) m.getLength());
        msg.put(Sha256.getDoubleHash(tmp.array()).getChecksum());
    }

    private byte [] intToByteArray(long i ){
        return new byte [] {(byte) (i & 0xFF), (byte) ((i >>> 8) & 0xFF), (byte) ((i >>> 16) & 0xFF), (byte) ((i >>> 24) & 0xFF)};
    }

}
