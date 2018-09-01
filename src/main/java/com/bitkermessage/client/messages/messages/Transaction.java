package com.bitkermessage.client.messages.messages;

import com.bitkermessage.client.messages.bitio.LittleEndianInputStream;
import com.bitkermessage.client.messages.bitio.LittleEndianOutputStream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Matteo on 08/10/2016.
 */
public class Transaction extends Message {

    private int version;
    private int flag;
    private List<TransactionInput> transactionsIn;
    private List<TransactionOutput> transactionsOut;
    private int lock_time;


    public Transaction(){
        transactionsIn = new ArrayList<>();
        transactionsOut = new ArrayList<>();
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TransactionInput> getTransactionsIn() {
        return transactionsIn;
    }

    public List<TransactionOutput> getTransactionsOut() {
        return transactionsOut;
    }

    public void setLock_time(int lock_time) {
        this.lock_time = lock_time;
    }

    @Override
    public String getCommand() {
        return "tx";
    }

    @Override
    public void read(LittleEndianInputStream leis) throws IOException {
        version = leis.readInt();
        if(version != 1)
        {
            leis.mark(20);
            int first = leis.readUnsignedByte();
            int second = leis.readUnsignedByte();
            if(first == 0 && second == 1)
                flag = 1;
            else
                leis.reset();
        }
        long inLength = leis.readVariableSize();
        for(long i = 0; i < inLength; i++)
        {
            TransactionInput tranIn = new TransactionInput();
            tranIn.read(leis);
            transactionsIn.add(tranIn);
        }
        long outLength = leis.readVariableSize();
        for(long i = 0; i < outLength; i++)
        {
            TransactionOutput tranOut = new TransactionOutput();
            tranOut.read(leis);
            transactionsOut.add(tranOut);
        }
        if(flag == 1)
            for(long i = 0; i < inLength; i++ )
            {
                long witsize = leis.readVariableSize();
                if(witsize == 0)
                    continue;
                for(long j = 0; j < witsize; j++)
                {
                    TransactionWitness tranWit = new TransactionWitness();
                    tranWit.read(leis);
                    transactionsIn.get((int) i).addWitness(tranWit);
                }
            }
        lock_time = leis.readInt();
    }

    @Override
    public void write(LittleEndianOutputStream leos) throws IOException {
        leos.writeInt(version);
        if(flag == 1)
        {
            leos.write(0);
            leos.write(1);
        }
        leos.writeVariableSize(transactionsIn.size());
        for(TransactionInput in : transactionsIn)
            in.write(leos);
        leos.writeVariableSize(transactionsOut.size());
        for(TransactionOutput out : transactionsOut)
            out.write(leos);
        if(flag == 1)
            for(TransactionInput in : transactionsIn)
            {
                if(in.getWitnesses() == null)
                {
                    leos.write(0);
                    continue;
                }
                leos.writeVariableSize(in.getWitnesses().size());
                for (TransactionWitness tw : in.getWitnesses())
                    tw.write(leos);
            }
        leos.writeInt(lock_time);
    }
}
