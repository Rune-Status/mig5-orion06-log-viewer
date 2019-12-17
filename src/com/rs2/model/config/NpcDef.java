package com.rs2.model.config;
import java.io.*;
import com.rs2.cache.Archive;
import com.rs2.cache.Cache;
import com.rs2.util.Stream;

public class NpcDef {
	
	private static Stream stream;
	private static int[] streamIndices;
	
	public static void loadNpcDat()
	{
		Cache cache = Cache.getSingleton();
		Stream stream2 = null;
		try {
			stream = new Stream(new Archive(cache.getFile(0, 2)).getFile("npc.dat"));
			stream2 = new Stream(new Archive(cache.getFile(0, 2)).getFile("npc.idx"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int totalNPCs = stream2.readUnsignedWord();
		streamIndices = new int[totalNPCs];
		cacheo = new NpcDef[20];
		int i = 2;
		for(int j = 0; j < totalNPCs; j++)
		{
			streamIndices[j] = i;
			i += stream2.readUnsignedWord();
		}
		for (int k = 0; k < 20; k++) {
            cacheo[k] = new NpcDef();
        }
	}
	
	public static NpcDef forId(int i) {
        if (i < 0) {
            return null;
        }
        for (int j = 0; j < 20; j++) {
            if (cacheo[j].type == i) {
                return cacheo[j];
            }
        }
        cacheIndex = (cacheIndex + 1) % 20;
        NpcDef class46 = cacheo[cacheIndex];
        stream.currentOffset = streamIndices[i];
        class46.type = i;
        class46.setDefaults();
        class46.readValues(stream, i);
        return class46;
    }
	
	private void setDefaults() {
        name = null;
        examine = null;
        combat = 0;
        size = 0;
	}
	
	private void readValues(Stream stream, int id)
	{
		do
		{
			int i = stream.readUnsignedByte();
			if(i == 0){
				return;
			}	
			if(i == 1)
			{
				int j = stream.readUnsignedByte();
				models = new int[j];
				for(int j1 = 0; j1 < j; j1++)
					models[j1] = stream.readUnsignedWord();
			} else
			if(i == 2){
				name = stream.readString();
			}else
			if(i == 3){
				String str = new String(stream.readBytes());
				examine = str;
			}else
			if(i == 12)
				size = stream.readSignedByte();
			else
			if(i == 13)
				standAnim = stream.readUnsignedWord();
			else
			if(i == 14)
				walkAnim = stream.readUnsignedWord();
			else
			if(i == 17)
			{
				stream.readUnsignedWord();
				stream.readUnsignedWord();
				stream.readUnsignedWord();
				stream.readUnsignedWord();
			} else
			if(i >= 30 && i < 40)
			{
				if(actions == null) {
					actions = new String[5];
		        }
				actions[i - 30] = stream.readString();
		        if(this.actions[i - 30].equalsIgnoreCase("hidden")) {
		        	this.actions[i - 30] = null;
		        }
			} else
			if(i == 40)
			{
				int k = stream.readUnsignedByte();
				for(int k1 = 0; k1 < k; k1++)
				{
					stream.readUnsignedWord();
					stream.readUnsignedWord();
				}

			} else
			if(i == 60)
			{
				int l = stream.readUnsignedByte();
				for(int l1 = 0; l1 < l; l1++)
					stream.readUnsignedWord();

			} else
			if(i == 90)
				stream.readUnsignedWord();
			else
			if(i == 91)
				stream.readUnsignedWord();
			else
			if(i == 92)
				stream.readUnsignedWord();
			else
			if(i == 93){
			}else
			if(i == 95)
				combat = stream.readUnsignedWord();
			else
			if(i == 97)
				stream.readUnsignedWord();
			else
			if(i == 98)
				stream.readUnsignedWord();
			else
			if(i == 99){
			}else
			if(i == 100)
				stream.readSignedByte();
			else
			if(i == 101)
				stream.readSignedByte();
			else
			if(i == 102)
				stream.readUnsignedWord();
			else
			if(i == 103)
				stream.readUnsignedWord();
			else
			if(i == 106)
			{
						
				stream.readUnsignedWord();
				stream.readUnsignedWord();
				int i1 = stream.readUnsignedByte();
				for(int i2 = 0; i2 <= i1; i2++)
				{
					stream.readUnsignedWord();
				}
			} else
			if(i == 107){
			}
		} while(true);
	}
	
    public String name;
    public String examine;
    public int standAnim;
    public int walkAnim;
    public int models[];
    public String actions[];
    public int combat;
    public int size;
    public int type;
    private static NpcDef[] cacheo;
    private static int cacheIndex;
	
}
