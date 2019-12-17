package com.rs2.model.config;

import java.io.*;
import com.rs2.cache.Archive;
import com.rs2.cache.Cache;
import com.rs2.util.Stream;

public class Flo {

	public static int FLO_COUNT = 0;
	private static Flo[] definitions = new Flo[0];
	
	private void readFloValues(Stream stream, int c)
	{
		do
		{
			int i = stream.readUnsignedByte();
			if(i == 0){
				return;
			}else
			if(i == 1){
				this.color = stream.read3Bytes();
			} 
			else
			if(i == 2){
				this.texture = stream.readUnsignedByte();
			}else
			if(i == 3){
				//dummy
			}
			else
			if(i == 4){
				this.overlay = stream.read3Bytes();
			}else
			if(i == 5){
				this.occlude = false;
			}else
			if(i == 6){
				this.name = stream.readString();
			}else
			if(i == 7){
				this.color2 = stream.read3Bytes();
			}
			else
			{
				System.out.println("Error unrecognised config code: " + i);
			}
		} while(true);
	}
	
	public static void unpack(){
		Cache cache = Cache.getSingleton();
		Stream stream = null;
		try {
			stream = new Stream(new Archive(cache.getFile(0, 2)).getFile("flo.dat"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int cacheSize = stream.readUnsignedWord();
	    cacheo = new Flo[cacheSize];
		for(int j = 0; j < cacheSize; j++)
		{
			if(cacheo[j] == null) {
	            cacheo[j] = new Flo();
	         }

	         cacheo[j].readFloValues(stream, j);
		}
	}
	
	public int id;
	public int color;
    public int texture;
    public int overlay;
    public boolean occlude;
    public String name;
    public int color2;
    public static Flo[] cacheo;
    
}
