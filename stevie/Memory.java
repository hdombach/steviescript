package stevie;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;


/*

Memory

[heap] //heap includes code and memory provided by aloc
[heap]
[heap]
[heap]
[...] //empty space for if heap grows later on
[...]
[...]
[stack] //items are pushed onto end of stack
[stack]
[stack]
[...] //empty space
[...]
[...]

if add >= 0 then count from start of heap.
if add < 0 then count backwards from end of stack.

*/

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

	//adds to end of stack
	public static void push(int length) {
		bytes.put(stackSize, new byte[length]);
		stackSize += length;
	}
	//adds to end of stack
	public static void push(byte[] data) {
		bytes.put(stackSize, data);
		stackSize += data.length;
	}

	//removes form end of stack.
	public static void pop(int length) {
		stackSize -= length;
	}

	//Finds memory in heap.
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

	//frees memory in heap and simplifies the metadata
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
		bytes.put(normAdd(address), value);
	}

	//Normalizes the adress
	//takes into account negative numbers
	private static int normAdd(int address) {
		if (address < 0) {
			return heapSize + stackSize + address;
		} else {
			return address;
		}
	}

	public static byte[] get(int address, int length) {
		return bytes.get(new byte[length], normAdd(address), length).array();
	}

	public static int getInt(int address) {
		return bytes.getInt(normAdd(address));
	}

	public static void printContents() {
		int c = 0;
		byte[] section;
		while (c < metaData.size()) {
			MetaData d = metaData.get(c);
			section = get(d.start, d.length);
			System.out.println(d.start + ": " + section);
		}

		section = get(heapSize, stackSize);
		System.out.println("Stack: " + section);
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