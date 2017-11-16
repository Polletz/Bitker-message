package com.bitker.messages;

import com.bitker.bitio.LittleEndianInputStream;
import com.bitker.bitio.LittleEndianOutputStream;

import java.io.IOException;

/**
 * Created by Matteo on 08/10/2016.
 *
 */
public class FilterClear extends Message {


    @Override
    public String getCommand() {
        return "filterclear";
    }

    @Override
    public void read(LittleEndianInputStream leis) throws IOException {

    }

    @Override
    public void write(LittleEndianOutputStream leos) throws IOException {

    }
}
