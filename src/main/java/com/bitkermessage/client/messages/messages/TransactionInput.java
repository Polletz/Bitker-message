package com.bitkermessage.client.messages.messages;

import com.bitkermessage.client.messages.bitio.LittleEndianInputStream;
import com.bitkermessage.client.messages.bitio.LittleEndianOutputStream;
import io.nayuki.bitcoin.crypto.Sha256;
import io.nayuki.bitcoin.crypto.Sha256Hash;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matteo on 08/10/2016.
 */
public class TransactionInput implements BitSerializable {

    private OutPoint previous;
    private byte [] script;
    private int sequence;
    private List<TransactionWitness> transWitnesses;

    public TransactionInput(){
        previous = new OutPoint();
        transWitnesses = null;
    }

    public void setPrevious(byte [] hash,int index) {
        previous.setHash(hash);
        previous.setIndex(index);
    }

    public void setPreviousMessage(byte [] msg,int index) {
        previous.generateHash(msg);
        previous.setIndex(index);
    }

    public void setPrevious(Sha256Hash hash,int index){
        previous.setHash(hash);
        previous.setIndex(index);
    }

    public void setScript(byte [] script){
        this.script = script;
    }

    public void setSequence(int sequence) {
        this.sequence = sequence;
    }

    public List<TransactionWitness> getWitnesses(){
        return transWitnesses;
    }

    public void addWitness(TransactionWitness tw){
        if(transWitnesses == null)
            transWitnesses = new ArrayList<>();
        transWitnesses.add(tw);
    }

    @Override
    public void read(LittleEndianInputStream leis) throws IOException {
        previous.read(leis);
        int length = (int) leis.readVariableSize();
        script = new byte [length];
        leis.read(script);
        sequence = leis.readInt();
    }

    @Override
    public void write(LittleEndianOutputStream leos) throws IOException {
        previous.write(leos);
        leos.writeVariableSize(script.length);
        leos.write(script);
        leos.writeInt(sequence);
    }

    private class OutPoint implements BitSerializable{

        private byte [] hash;
        private int index;

        public void setHash(byte [] hash){
            if(hash.length == 32)
                this.hash = hash;
        }

        public void setHash(Sha256Hash hash){
            this.hash = hash.toBytes();
        }

        public void generateHash(byte [] msg){
            this.hash = Sha256.getHash(msg).toBytes();
        }

        public void setIndex(int index){
            this.index = index;
        }

        @Override
        public void read(LittleEndianInputStream leis) throws IOException {
            hash = new byte [Sha256Hash.HASH_LENGTH];
            leis.read(hash);
            index = leis.readInt();
        }

        @Override
        public void write(LittleEndianOutputStream leos) throws IOException {
            leos.write(hash);
            leos.writeInt(index);
        }
    }

}
