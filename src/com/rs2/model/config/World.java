package com.rs2.model.config;

import java.io.IOException;
import java.nio.ByteBuffer;

import com.rs2.cache.Cache;
import com.rs2.cache.map.CacheObject;
import com.rs2.cache.map.CacheTile;
import com.rs2.cache.util.ByteBufferUtils;
import com.rs2.cache.util.ZipUtils;
import com.rs2.model.Position;
import com.rs2.util.Stream;

public class World {

	private static CacheObject[] objects = new CacheObject[5000000];
	private static CacheTile[] underlayTiles = new CacheTile[5000000];
	private static CacheTile[] overlayTiles = new CacheTile[5000000];
	
	public static int objectCount = 0;
	public static int underlayTileCount = 0;
	public static int overlayTileCount = 0;
	
	public static void readObjectMap(int area, int id) throws IOException {
		Cache cache = Cache.getSingleton();
		int x = (area >> 8 & 0xFF) * 64;
		int y = (area & 0xFF) * 64;
		ByteBuffer buf = ZipUtils.unzip(cache.getFile(4, id));
		int objId = -1;
		while (true) {
			int objIdOffset = ByteBufferUtils.getSmart(buf);
			if (objIdOffset == 0) {
				break;
			} else {
				objId += objIdOffset;
				int objPosInfo = 0;
				while (true) {
					int objPosInfoOffset = ByteBufferUtils.getSmart(buf);
					if (objPosInfoOffset == 0) {
						break;
					} else {
						objPosInfo += objPosInfoOffset - 1;

						int localX = objPosInfo >> 6 & 0x3f;
						int localY = objPosInfo & 0x3f;
						int plane = objPosInfo >> 12;

						int objOtherInfo = buf.get() & 0xFF;

						int type = objOtherInfo >> 2;
						int rotation = objOtherInfo & 3;

						Position loc = new Position(localX + x, localY + y, plane);

						objects[objectCount] = new CacheObject(objId, loc, type, rotation);
						objectCount++;
					}
				}
			}
		}
	}
	
	private static int xMapSize = 64;
    private static int yMapSize = 64;
	
	public static void readFloorMap(int area, int id)
    {
		Cache cache = Cache.getSingleton();
		int xA = (area >> 8 & 0xFF) * 64;
		int yA = (area & 0xFF) * 64;
		Stream stream = null;
		try{
			byte abyte2[] = ZipUtils.unzip1(cache.getFile(4, id));
			stream = new Stream(abyte2);
		} catch (IOException e) {
			e.printStackTrace();
		}
        for(int z = 0; z < 4; z++)
        {
            for(int x = 0; x < 64; x++)
            {
                for(int y = 0; y < 64; y++)
                    readTile(y, stream, x, z, xA, yA);

            }

        }
    }

    private static void readTile(int i, Stream stream, int k, int l, int xA, int yA)
    {
    	Position loc = new Position(k + xA, i + yA, l);
        if(k >= 0 && k < xMapSize && i >= 0 && i < yMapSize)
        {
            do
            {
                int l1 = stream.readUnsignedByte();
                if(l1 == 0)
                    return;
                if(l1 == 1)
                {
                    int j2 = stream.readUnsignedByte();
                    return;
                }
                if(l1 <= 49)
                {
					byte o = stream.readSignedByte();
					int id = unsignedByteToInt(o)-1;
					//overLay[l][k][i] = unsignedByteToInt(o)-1;
					overlayTiles[overlayTileCount] = new CacheTile(id, loc);
					overlayTileCount++;
                } else
                if(l1 > 81){
					byte u = (byte)(l1 - 81);
					int id = unsignedByteToInt(u)-1;
					underlayTiles[underlayTileCount] = new CacheTile(id, loc);
					underlayTileCount++;
					//underLay[l][k][i] = unsignedByteToInt(u)-1;
				}	
            } while(true);
        }
        do
        {
            int i2 = stream.readUnsignedByte();
            if(i2 == 0)
                break;
            if(i2 == 1)
            {
                stream.readUnsignedByte();
                return;
            }
            if(i2 <= 49)
                stream.readUnsignedByte();
        } while(true);
    }

	public static int unsignedByteToInt(byte b) {
		return (int) b & 0xFF;
    }
	
	public static CacheTile getUnderlayTile(int id) {
		if (id < 0) {
			id = 1;
		}
		CacheTile def = underlayTiles[id];
		return def;
	}
	
	public static CacheTile getOverlayTile(int id) {
		if (id < 0) {
			id = 1;
		}
		CacheTile def = overlayTiles[id];
		return def;
	}
	
	public static CacheObject getObject(int id) {
		if (id < 0) {
			id = 1;
		}
		CacheObject def = objects[id];
		return def;
	}
	
}
