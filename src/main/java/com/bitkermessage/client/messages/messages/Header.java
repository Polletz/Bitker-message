package Header_Message;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import Utils.LittleEndianInputStream;
import Utils.LittleEndianOutputStream;
import Utils.Message;

public class Header extends Message{
	private static final long serialVersionUID = 1L;
	private List<BlockHeader> blockheaders;

	public Header(List<BlockHeader> iv){
		blockheaders = iv;
	}
	
    public Header(){
    	blockheaders = new ArrayList<>();
    }

    public List<BlockHeader> getblockheader() {
        return blockheaders;
    }

    public String getCommand() {
        return "headers";
    }

    @Override
    public long getLength() {
        int varint = 1;
        if(blockheaders.size() < 0xFD)
            varint = 1;
        else if(blockheaders.size() <= 0xFFFF)
            varint = 3;
        else if(blockheaders.size() <= 0xFFFFFFFF)
            varint = 5;
        return 36*blockheaders.size() + varint;

    }

    @Override
    public void read(LittleEndianInputStream leis) throws IOException {
    	try{
	        long count = leis.readVariableSize();
	        for(long i = 0; i< count; i++)
	        {
	        	BlockHeader blockheader = new BlockHeader();
	        	blockheader.read(leis);
	            blockheaders.add(blockheader);
	        }
    	}catch(RuntimeException e){
    	}
    }

    @Override
    public void write(LittleEndianOutputStream leos) throws IOException {
        leos.writeVariableSize(blockheaders.size());
        for(BlockHeader blockheader : blockheaders)
        {
        	blockheader.write(leos);
        }
    }

    @Override
    public String toString() {
        return super.toString()+"headers: " + blockheaders.toString();
    }
}
