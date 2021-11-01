package stevie;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
	private static ArrayList<MetaData> metaData;
	private static ByteBuffer bytes;
	private static int heapSize;
	private static int stackSize;
	private static int maxStackSize;
	
	public static void init(int startingHeapSize, int startingStackSize) {
		heapSize = startingHeapSize;
		maxStackSize = startingStackSize;
		heapSize = 0;
		bytes = ByteBuffer.allocate(heapSize + maxStackSize);
		metaData = new ArrayList<MetaData>();
		metaData.add(new MetaData(0, startingHeapSize));
	}

	public static void push(int length) {
		bytes.put(stackSize, new byte[length]);
		stackSize += length;
	}
	public static void push(byte[] data) {
		bytes.put(stackSize, data);
		stackSize += data.length;
	}

	public static void pop(int length) {
		stackSize -= length;
	}

	public static int alloc(int length) {
		int c = 0;
		while (metaData.size() > c) {
			MetaData d = metaData.get(c);
			if (!d.used) {
				if (d.length == length) {
					d.used = true;
					return d.start;
				} else if (d.length > length) {
					MetaData unused = new MetaData(d.start + length, d.length - length);
					metaData.add(c + 1, unused);
					d.length = length;
					d.used = true;
					return d.start;
				}
			}
			c += 1;
		}
		throw new Error("Not enough memory");
		//TODO: allocated more memory
	}

	public static void free(int address) {
		int c = 0;
		MetaData last = null;
		while (metaData.get(c).start != address) {
			if (c >= metaData.size()) {
				throw new Error("Invalid address passed to memory free.");
			}
			last = metaData.get(c);
			c += 1;
		}
		MetaData current = metaData.get(c);
		current.used = false;

		MetaData next = null;
		if (c < metaData.size() - 1) {
			next = metaData.get(c + 1);
		}

		//collapse neighboring cells
		if (c > 0 && !last.used) {
			last.length += current.length;
			metaData.remove(c);
			current = last;
		}

		if (next != null && !next.used) {
			current.length += next.length;
			metaData.remove(next);
		}
	}

	public static void set(int address, byte[] value) {
		
	}

	public static byte[] get(int address) {
		throw new Error("Not implemented");
	}

	public static int getInt(int address) {
		throw new Error("Not implemented");
	}

	public static void printContents() {
		
	}
	public static void printMetData() {
		System.out.println(metaData);
	}
}

class MetaData {
	int start;
	int length;
	boolean used;

	MetaData(int start, int length) {
		this.used = false;
		this.start = start;
		this.length = length;
	}

	public String toString() {
		if (used) {
			return "[" + start + ", " + length + "]";
		} else {
			return "[FREE " + start + ", " + length + "]";
		}
	}
}