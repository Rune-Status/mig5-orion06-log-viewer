package com.rs2.model.config;
import java.io.*;

import com.rs2.cache.Archive;
import com.rs2.cache.Cache;
import com.rs2.util.Stream;

public class ItemDef {
	
	private static Stream stream;
	private static int[] streamIndices;
	
	public static void loadItemDat()
	{
		Cache cache = Cache.getSingleton();
		Stream stream2 = null;
		mruNodes1 = null;
		mruNodes2 = null;
		try {
			stream = new Stream(new Archive(cache.getFile(0, 2)).getFile("obj.dat"));
			stream2 = new Stream(new Archive(cache.getFile(0, 2)).getFile("obj.idx"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		int totalItems = stream2.readUnsignedWord();
		streamIndices = new int[totalItems];
		cacheo = new ItemDef[10];
		int i = 2;
		for(int j = 0; j < totalItems; j++)
		{
			streamIndices[j] = i;
			i += stream2.readUnsignedWord();
		}
		for (int k = 0; k < 10; k++) {
            cacheo[k] = new ItemDef();
        }
	}
	
	public static ItemDef forId(int i) {
        if (i < 0) {
            return null;
        }
        for (int j = 0; j < 10; j++) {
            if (cacheo[j].type == i) {
                return cacheo[j];
            }
        }
        cacheIndex = (cacheIndex + 1) % 10;
        ItemDef class46 = cacheo[cacheIndex];
        stream.currentOffset = streamIndices[i];
        class46.type = i;
        class46.setDefaults();
        class46.readValues(stream, i);
        return class46;
    }
	
	private void setDefaults() {
		name = null;
	    examine = null;
	    noted = false;
	    members = false;
	    stackable = false;
	    parentId = -1;
	    //
	    modelID = 0;
		modifiedModelColors = null;
		originalModelColors = null;
		modelZoom = 2000;
		modelRotation1 = 0;
		modelRotation2 = 0;
		anInt204 = 0;
		modelOffset1 = 0;
		modelOffset2 = 0;
		value = 1;
		anInt165 = -1;
		anInt188 = -1;
		aByte205 = 0;
		anInt200 = -1;
		anInt164 = -1;
		aByte154 = 0;
		anInt185 = -1;
		anInt162 = -1;
		anInt175 = -1;
		anInt166 = -1;
		anInt197 = -1;
		anInt173 = -1;
		stackIDs = null;
		stackAmounts = null;
		certID = -1;
		certTemplateID = -1;
		anInt167 = 128;
		anInt192 = 128;
		anInt191 = 128;
		anInt196 = 0;
		anInt184 = 0;
	    //
	}
	
	private void readValues(Stream stream, int id) {
        do {
            int i = stream.readUnsignedByte();
            if(i == 0){
                return;
            }if(i == 1){
				modelID = stream.readUnsignedWord();
            }else if(i == 2){
				name = stream.readString();
            }else if(i == 3){
				String str = new String(stream.readBytes());
				examine = str;
            }else if(i == 4){
				modelZoom = stream.readUnsignedWord();
            }else if(i == 5){
				modelRotation1 = stream.readUnsignedWord();
            }else if(i == 6){
				modelRotation2 = stream.readUnsignedWord();
            }else if(i == 7) {
            	modelOffset1 = stream.readUnsignedWord();
				if(modelOffset1 > 32767)
					modelOffset1 -= 0x10000;
            } else if(i == 8) {
            	modelOffset2 = stream.readUnsignedWord();
				if(modelOffset2 > 32767)
					modelOffset2 -= 0x10000;
            } else if(i == 10)
                stream.readUnsignedWord();
            else if(i == 11){
				stackable = true;
            }else if(i == 12){
            	value = stream.readDWord();
            }else if(i == 16){
				members = true;
            }else if(i == 23) {
            	anInt165 = stream.readUnsignedWord();
				aByte205 = stream.readSignedByte();
            } else if(i == 24){
            	anInt188 = stream.readUnsignedWord();
            }else if(i == 25) {
            	anInt200 = stream.readUnsignedWord();
				aByte154 = stream.readSignedByte();
            } else if(i == 26){
            	anInt164 = stream.readUnsignedWord();
            }else if(i >= 30 && i < 35) {
				stream.readString();
            } else if(i >= 35 && i < 40) {
				stream.readString();
            } else if(i == 40) {
            	int j = stream.readUnsignedByte();
				modifiedModelColors = new int[j];
				originalModelColors = new int[j];
				for(int k = 0; k < j; k++)
				{
					modifiedModelColors[k] = stream.readUnsignedWord();
					originalModelColors[k] = stream.readUnsignedWord();
				}
            } else if(i == 78){
            	anInt185 = stream.readUnsignedWord();
            }else if(i == 79){
            	anInt162 = stream.readUnsignedWord();
            }else if(i == 90){
            	anInt175 = stream.readUnsignedWord();
            }else if(i == 91){
            	anInt197 = stream.readUnsignedWord();
            }else if(i == 92){
            	anInt166 = stream.readUnsignedWord();
            }else if(i == 93){
            	anInt173 = stream.readUnsignedWord();
            }else if(i == 95){
            	anInt204 = stream.readUnsignedWord();
            }else if(i == 97){
            	certID = stream.readUnsignedWord();
				noted = true;
				parentId = certID;
            }else if(i == 98){
            	certTemplateID = stream.readUnsignedWord();
            }else if(i >= 100 && i < 110) {
            	if(stackIDs == null)
				{
					stackIDs = new int[10];
					stackAmounts = new int[10];
				}
				stackIDs[i - 100] = stream.readUnsignedWord();
				stackAmounts[i - 100] = stream.readUnsignedWord();
            } else if(i == 110){
            	anInt167 = stream.readUnsignedWord();
            }else if(i == 111){
            	anInt192 = stream.readUnsignedWord();
            }else if(i == 112){
            	anInt191 = stream.readUnsignedWord();
            }else if(i == 113){
            	anInt196 = stream.readSignedByte();
            }else if(i == 114){
            	anInt184 = stream.readSignedByte() * 5;
            }else if(i == 115){
				stream.readUnsignedByte();
			}else if(i == 121){
				stream.readUnsignedWord();
			}else if(i == 122){
				stream.readUnsignedWord();
			} else if(i == 140) {
                int j = stream.readUnsignedByte();
                for(int k = 0; k < j; k++) {
					stream.readUnsignedWord();
					stream.readUnsignedWord();
                }
			}else if(i == 177){
				//members = true;//isOnGe = true;
			}
        } while(true);
    }
	
    public String name;
    public String examine;
    public boolean noted;
    public boolean members;
    public boolean stackable;
    public int parentId;
    public int type;
    //
    public int[] originalModelColors;
    public int[] modifiedModelColors;
    public int[] stackAmounts;
    public int[] stackIDs;
    public int modelID;
    public int modelZoom;
    public int modelRotation1;
    public int modelRotation2;
    public int modelOffset1;
    public int modelOffset2;
    public int value;
    public int certID;
    public int certTemplateID;
    public byte aByte154;
    public byte aByte205;
    public int anInt165;
    public int anInt188;
    public int anInt200;
    public int anInt164;
    public int anInt185;
    public int anInt162;
    public int anInt175;
    public int anInt197;
    public int anInt166;
    public int anInt173;
    public int anInt204;
    public int anInt167;
    public int anInt192;
    public int anInt191;
    public int anInt196;
    public int anInt184;
    //
    private static ItemDef[] cacheo;
    private static int cacheIndex;
    
    static MRUNodes mruNodes1 = new MRUNodes(100);
	public static MRUNodes mruNodes2 = new MRUNodes(50);
    
	/*public Model method201(int i)
	{
		if(stackIDs != null && i > 1)
		{
			int j = -1;
			for(int k = 0; k < 10; k++)
				if(i >= stackAmounts[k] && stackAmounts[k] != 0)
					j = stackIDs[k];

			if(j != -1)
				return forId(j).method201(1);
		}
		Model model = (Model) mruNodes2.insertFromCache(id);
		if(model != null)
			return model;
		//model = Model.method462(modelID);
		if(model == null)
			return null;
		if(anInt167 != 128 || anInt192 != 128 || anInt191 != 128)
			model.method478(anInt167, anInt191, anInt192);
		if(modifiedModelColors != null)
		{
			for(int l = 0; l < modifiedModelColors.length; l++)
				model.method476(modifiedModelColors[l], originalModelColors[l]);

		}
		model.method479(64 + anInt196, 768 + anInt184, -50, -10, -50, true);
		model.aBoolean1659 = true;
		mruNodes2.removeFromCache(model, id);
		return model;
	}
	
    public static Sprite getSprite(int i, int j, int k)
	{
		if(k == 0)
		{
			Sprite sprite = (Sprite) mruNodes1.insertFromCache(i);
			if(sprite != null && sprite.anInt1445 != j && sprite.anInt1445 != -1)
			{
				sprite.unlink();
				sprite = null;
			}
			if(sprite != null)
				return sprite;
		}
		ItemDef itemDef = forId(i);
		if(itemDef.stackIDs == null)
			j = -1;
		if(j > 1)
		{
			int i1 = -1;
			for(int j1 = 0; j1 < 10; j1++)
				if(j >= itemDef.stackAmounts[j1] && itemDef.stackAmounts[j1] != 0)
					i1 = itemDef.stackIDs[j1];

			if(i1 != -1)
				itemDef = forId(i1);
		}
		Model model = itemDef.method201(1);
		if(model == null)
			return null;
		Sprite sprite = null;
		if(itemDef.certTemplateID != -1)
		{
			sprite = getSprite(itemDef.certID, 10, -1);
			if(sprite == null)
				return null;
		}
		Sprite sprite2 = new Sprite(32, 32);
		int k1 = Texture.textureInt1;
		int l1 = Texture.textureInt2;
		int ai[] = Texture.anIntArray1472;
		int ai1[] = DrawingArea.pixels;
		int i2 = DrawingArea.width;
		int j2 = DrawingArea.height;
		int k2 = DrawingArea.topX;
		int l2 = DrawingArea.bottomX;
		int i3 = DrawingArea.topY;
		int j3 = DrawingArea.bottomY;
		Texture.aBoolean1464 = false;
		DrawingArea.initDrawingArea(32, 32, sprite2.myPixels);
		DrawingArea.method336(32, 0, 0, 0, 32);
		Texture.method364();
		int k3 = itemDef.modelZoom;
		if(k == -1)
			k3 = (int)((double)k3 * 1.5D);
		if(k > 0)
			k3 = (int)((double)k3 * 1.04D);
		int l3 = Texture.anIntArray1470[itemDef.modelRotation1] * k3 >> 16;
		int i4 = Texture.anIntArray1471[itemDef.modelRotation1] * k3 >> 16;
		model.method482(itemDef.modelRotation2, itemDef.anInt204, itemDef.modelRotation1, itemDef.modelOffset1, l3 + model.modelHeight / 2 + itemDef.modelOffset2, i4 + itemDef.modelOffset2);
		for(int i5 = 31; i5 >= 0; i5--)
		{
			for(int j4 = 31; j4 >= 0; j4--)
				if(sprite2.myPixels[i5 + j4 * 32] == 0)
					if(i5 > 0 && sprite2.myPixels[(i5 - 1) + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else
					if(j4 > 0 && sprite2.myPixels[i5 + (j4 - 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else
					if(i5 < 31 && sprite2.myPixels[i5 + 1 + j4 * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;
					else
					if(j4 < 31 && sprite2.myPixels[i5 + (j4 + 1) * 32] > 1)
						sprite2.myPixels[i5 + j4 * 32] = 1;

		}

		if(k > 0)
		{
			for(int j5 = 31; j5 >= 0; j5--)
			{
				for(int k4 = 31; k4 >= 0; k4--)
					if(sprite2.myPixels[j5 + k4 * 32] == 0)
						if(j5 > 0 && sprite2.myPixels[(j5 - 1) + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else
						if(k4 > 0 && sprite2.myPixels[j5 + (k4 - 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else
						if(j5 < 31 && sprite2.myPixels[j5 + 1 + k4 * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;
						else
						if(k4 < 31 && sprite2.myPixels[j5 + (k4 + 1) * 32] == 1)
							sprite2.myPixels[j5 + k4 * 32] = k;

			}

		} else
		if(k == 0)
		{
			for(int k5 = 31; k5 >= 0; k5--)
			{
				for(int l4 = 31; l4 >= 0; l4--)
					if(sprite2.myPixels[k5 + l4 * 32] == 0 && k5 > 0 && l4 > 0 && sprite2.myPixels[(k5 - 1) + (l4 - 1) * 32] > 0)
						sprite2.myPixels[k5 + l4 * 32] = 0x302020;

			}

		}
		if(itemDef.certTemplateID != -1)
		{
			int l5 = sprite.anInt1444;
			int j6 = sprite.anInt1445;
			sprite.anInt1444 = 32;
			sprite.anInt1445 = 32;
			sprite.drawSprite(0, 0);
			sprite.anInt1444 = l5;
			sprite.anInt1445 = j6;
		}
		if(k == 0)
			mruNodes1.removeFromCache(sprite2, i);
		DrawingArea.initDrawingArea(j2, i2, ai1);
		DrawingArea.setDrawingArea(j3, k2, l2, i3);
		Texture.textureInt1 = k1;
		Texture.textureInt2 = l1;
		Texture.anIntArray1472 = ai;
		Texture.aBoolean1464 = true;
		if(itemDef.stackable)
			sprite2.anInt1444 = 33;
		else
			sprite2.anInt1444 = 32;
		sprite2.anInt1445 = j;
		return sprite2;
	}*/
	
}
