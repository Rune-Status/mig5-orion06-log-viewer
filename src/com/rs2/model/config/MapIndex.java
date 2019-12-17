package com.rs2.model.config;

import java.io.*;

import com.rs2.cache.Archive;
import com.rs2.cache.Cache;
import com.rs2.util.Stream;

public class MapIndex {

	public static int[] regionId;
	public static int[] floorMap;
	public static int[] objectMap;

	public static int regionCount = 0;
	
	public static void unpack(){
		Cache cache = Cache.getSingleton();
		Stream stream2 = null;
		byte abyte2[] = null;
		try {
			abyte2 = new Archive(cache.getFile(0, 5)).getFile("map_index");
			stream2 = new Stream(abyte2);
		} catch (IOException e) {
			e.printStackTrace();
		}
		int j1;
		j1 = abyte2.length / 7;
		regionCount = j1;
		regionId = new int[j1];
		floorMap = new int[j1];
		objectMap = new int[j1];
		for(int i2 = 0; i2 < j1; i2++){
			regionId[i2] = stream2.readUnsignedWord();
			floorMap[i2] = stream2.readUnsignedWord();
			objectMap[i2] = stream2.readUnsignedWord();
				stream2.readUnsignedByte();
		}
	}
	
}
