package com.bitkermessage.client.messages.messages;

import com.bitkermessage.client.messages.bitio.LittleEndianInputStream;
import com.bitkermessage.client.messages.bitio.LittleEndianOutputStream;

import java.io.IOException;

public class TransactionWitness implements BitSerializable {


    byte [] stack;

    @Override
    public void read(LittleEndianInputStream leis) throws IOException {
        long length = leis.readVariableSize();
        stack = new byte [(int) length];
        leis.read(stack);
    }

    @Override
    public void write(LittleEndianOutputStream leos) throws IOException {
        leos.writeVariableSize(stack.length);
        leos.write(stack);
    }
}
