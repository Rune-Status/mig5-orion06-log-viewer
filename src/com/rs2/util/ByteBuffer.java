package com.rs2.util;


import java.math.BigInteger;
import java.util.Random;


public final class ByteBuffer {


	public static ByteBuffer allocate(int amount) {
		return new ByteBuffer(new byte[amount]);
	}


	public static ByteBuffer wrap(byte[] b) {
		return new ByteBuffer(b);
	}


	public ByteBuffer(byte[] data) {
		buffer = data;
		position = 0;
	}


	/**
	 * Also known as get24Bits, or get3Bytes.
	 *
	 * @return
	 */
	public int getMediumInt() {
		position += 3;
		return ((buffer[position - 3] & 0xff) << 16) + ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] & 0xff);
	}


	public void putBoolean(boolean bool) {
		buffer[position++] = (byte) (bool ? 1 : 0);
	}


	public void putByte(int val) {
		buffer[position++] = (byte) val;
	}


	public String getJagString() {
		int off = position;
		while (buffer[position++] != 0) {
		}
		return new String(buffer, off, position - off - 1);
	}


	public int getUSmart2() {
		int baseVal = 0;
		int lastVal = 0;
		while ((lastVal = getUnsignedSmart()) == 32767) {
			baseVal += 32767;
		}
		return baseVal + lastVal;
	}


	public void putShort(int val) {
		buffer[position++] = (byte) (val >> 8);
		buffer[position++] = (byte) val;
	}


	public void putMediumInt(int val) {
		buffer[position++] = (byte) (val >> 16);
		buffer[position++] = (byte) (val >> 8);
		buffer[position++] = (byte) val;
	}


	public void putInt(int val) {
		buffer[position++] = (byte) (val >> 24);
		buffer[position++] = (byte) (val >> 16);
		buffer[position++] = (byte) (val >> 8);
		buffer[position++] = (byte) val;
	}


	public void putLEInt(int val) {
		buffer[position++] = (byte) val;
		buffer[position++] = (byte) (val >> 8);
		buffer[position++] = (byte) (val >> 16);
		buffer[position++] = (byte) (val >> 24);
	}


	public void putLong(long val) {
		buffer[position++] = (byte) (int) (val >> 56);
		buffer[position++] = (byte) (int) (val >> 48);
		buffer[position++] = (byte) (int) (val >> 40);
		buffer[position++] = (byte) (int) (val >> 32);
		buffer[position++] = (byte) (int) (val >> 24);
		buffer[position++] = (byte) (int) (val >> 16);
		buffer[position++] = (byte) (int) (val >> 8);
		buffer[position++] = (byte) (int) val;
	}


	public void putLine(String string) {
		System.arraycopy(string.getBytes(), 0, buffer, position, string.length());
		position += string.length();
		buffer[position++] = 10;
	}


	public void putBytes(byte[] data, int length, int off) {
		for (int i = off; i < off + length; i++) {
			buffer[position++] = data[i];
		}
	}


	public void putSizeByte(int val) {
		buffer[position - val - 1] = (byte) val;
	}


	public int getUnsignedByte() {
		return buffer[position++] & 0xff;
	}


	public byte getByte() {
		return buffer[position++];
	}


	public int getUnsignedShort() {
		position += 2;
		return ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] & 0xff);
	}


	public int getShort() {
		position += 2;
		int val = ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] & 0xff);
		/*if (val > 32767) {
			val -= 0x10000;
		}*/
		return (short) val;
	}


	public int getShort2() {
		position += 2;
		int val = ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] & 0xff);
		if (val > 60000) {
			val = -65536 + val;
		}
		return val;
	}


	public int getInt() {
		position += 4;
		return ((buffer[position - 4] & 0xff) << 24) + ((buffer[position - 3] & 0xff) << 16) + ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] & 0xff);
	}


	public long getLong() {
		long l = getInt() & 0xffffffffL;
		long l1 = getInt() & 0xffffffffL;
		return (l << 32) + l1;
	}


	public void putSmartLong(long val) {
		if (val < 0L) {
			val = -1L;
		}
		if (val < 0x3fL) {
			putByte((int) val + 1);
		} else if (val < 0x3fffL) {
			putShort((int) val | 0x4000);
		} else if (val < 0x3fffffffL) {
			putInt((int) val | 0x80000000);
		} else {
			if (val > 0x3fffffffffffffffL) {
				val = 0x3fffffffffffffffL;
			}
			putLong(val | 0xc000000000000000L);
		}
	}


	public long getSmartLong() {
		int size = (buffer[position] & 0xc0) >>> 6;
		if (size == 0) {
			return getUnsignedByte() - 1;
		}
		if (size == 1) {
			return getUnsignedShort() & 0x3fff;
		}
		if (size == 2) {
			return getInt() & 0x3fffffff;
		}
		return getLong() & 0x3fffffffffffffffL;
	}


	public String getLine() {
		int off = position;
		while (buffer[position++] != 10) {
		}
		return new String(buffer, off, position - off - 1);
	}


	public byte[] getLineBytes() {
		int i = position;
		while (buffer[position++] != 10) {
		}
		byte[] data = new byte[position - i - 1];
		System.arraycopy(buffer, i, data, i - i, position - 1 - i);
		return data;
	}


	public void getBytesFromBuffer(byte[] data, int off, int length) {
		for (int index = off; index < off + length; index++) {
			data[index - off] = buffer[position++];
		}
	}


	public void getBytes(byte[] data, int off, int length) {
		System.arraycopy(buffer, position, data, off, length);
		position += length;
	}


	public void startBitAccess() {
		bitPosition = position * 8;
	}


	public int getBits(int off) {
		int offset = bitPosition >> 3;
		int max_bit_len = 8 - (bitPosition & 7);
		int dest = 0;
		bitPosition += off;
		for (; off > max_bit_len; max_bit_len = 8) {
			dest += (buffer[offset++] & bitMasks[max_bit_len]) << off - max_bit_len;
			off -= max_bit_len;
		}
		if (off == max_bit_len) {
			dest += buffer[offset] & bitMasks[max_bit_len];
		} else {
			dest += buffer[offset] >> max_bit_len - off & bitMasks[off];
		}
		return dest;
	}


	public void endBitAccess() {
		position = (bitPosition + 7) / 8;
	}


	public int getSmart() {
		int i = buffer[position] & 0xff;
		if (i < 128) {
			return getUnsignedByte() - 64;
		} else {
			return getUnsignedShort() - 49152;
		}
	}


	public int getUnsignedSmart() {
		int i = buffer[position] & 0xff;
		if (i < 128) {
			return getUnsignedByte();
		} else {
			return getUnsignedShort() - 32768;
		}
	}


	public void encodeRSA() {
		int off = position;
		position = 0;
		byte[] data = new byte[off];
		getBytes(data, 0, off);
		BigInteger biginteger2 = new BigInteger(data);
		BigInteger biginteger3 = biginteger2/* .modPow(biginteger, biginteger1) */;
		byte[] dest = biginteger3.toByteArray();
		position = 0;
		putByte(dest.length);
		putBytes(dest, dest.length, 0);
	}


	public void encode(Random random) {
		int originalPosition = position;
		position = 0;
		byte[] data = new byte[originalPosition];
		System.arraycopy(buffer, 0, data, 0, originalPosition);
		for (int i = 0; i < originalPosition; i++) {
			data[i] ^= random.nextInt();
		}
		this.putShort(data.length);
		this.putBytes(data, data.length, 0);
	}


	public void putNegativeByte(int val) {
		buffer[position++] = (byte) (-val);
	}


	public void putByteS(int val) {
		buffer[position++] = (byte) (128 - val);
	}


	public int getUnsignedByteA() {
		return buffer[position++] - 128 & 0xff;
	}


	public int getUnsignedNegativeByte() {
		return -buffer[position++] & 0xff;
	}


	public int getUnsignedByteS() {
		return 128 - buffer[position++] & 0xff;
	}


	public byte getNegativeByte() {
		return (byte) (-buffer[position++]);
	}


	public byte getByteS() {
		return (byte) (128 - buffer[position++]);
	}


	public void putLEShort(int val) {
		buffer[position++] = (byte) val;
		buffer[position++] = (byte) (val >> 8);
	}


	public void putShortA(int val) {
		buffer[position++] = (byte) (val >> 8);
		buffer[position++] = (byte) (val + 128);
	}


	public void putLEShortA(int val) {
		buffer[position++] = (byte) (val + 128);
		buffer[position++] = (byte) (val >> 8);
	}


	public int getUnsignedLEShort() {
		position += 2;
		return ((buffer[position - 1] & 0xff) << 8) + (buffer[position - 2] & 0xff);
	}


	public int getUnsignedLEShortA() {
		position += 2;
		return ((buffer[position - 2] & 0xff) << 8) + (buffer[position - 1] - 128 & 0xff);
	}


	public int getUnsignedShortA() {
		position += 2;
		return ((buffer[position - 1] & 0xff) << 8) + (buffer[position - 2] - 128 & 0xff);
	}


	public int getLEShort() {
		position += 2;
		int val = ((buffer[position - 1] & 0xff) << 8) + (buffer[position - 2] & 0xff);
		if (val > 32767) {
			val -= 0x10000;
		}
		return val;
	}


	public int getLEShortA() {
		position += 2;
		int val = ((buffer[position - 1] & 0xff) << 8) + (buffer[position - 2] - 128 & 0xff);
		if (val > 32767) {
			val -= 0x10000;
		}
		return val;
	}


	
	public int getInt_v2() {
		position += 4;
		return ((buffer[position - 2] & 0xff) << 24) + ((buffer[position - 1] & 0xff) << 16) + ((buffer[position - 4] & 0xff) << 8) + (buffer[position - 3] & 0xff);
	}


	public int getInt_v1() {
		position += 4;
		return ((buffer[position - 3] & 0xff) << 24) + ((buffer[position - 4] & 0xff) << 16) + ((buffer[position - 1] & 0xff) << 8) + (buffer[position - 2] & 0xff);
	}


	public void putBytesReverseA(byte[] data, int off, int len) {
		for (int index = (off + len) - 1; index >= off; index--) {
			buffer[position++] = (byte) (data[index] + 128);
		}
	}


	public void getBytesReverse(byte[] data, int off, int len) {
		for (int index = (off + len) - 1; index >= off; index--) {
			data[index] = buffer[position++];
		}
	}


	public byte[] buffer;
	public int position;
	public int bitPosition;
	private static final int[] bitMasks = { 0, 1, 3, 7, 15, 31, 63, 127,
			255, 511, 1023, 2047, 4095, 8191, 16383, 32767, 65535, 0x1ffff,
			0x3ffff, 0x7ffff, 0xfffff, 0x1fffff, 0x3fffff, 0x7fffff, 0xffffff,
			0x1ffffff, 0x3ffffff, 0x7ffffff, 0xfffffff, 0x1fffffff, 0x3fffffff,
			0x7fffffff, -1 };


}
