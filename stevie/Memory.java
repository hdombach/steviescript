package stevie;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;

import javax.naming.spi.DirStateFactory.Result;


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
		stackSize = 0;
		bytes = ByteBuffer.allocate(heapSize + maxStackSize);
		metaData = new ArrayList<MetaData>();
		metaData.add(new MetaData(0, startingHeapSize));
	}

	private static void increaseHeap() {
		bytes.position(0);
		byte[] heapSegment = new byte[heapSize];
		byte[] stackSegment = new byte[maxStackSize];
		bytes.position(0);
		bytes.get(heapSegment);
		bytes.position(heapSize);
		bytes.get(stackSegment);

		int oldHeapSize = heapSize;
		heapSize *= 2;
		ByteBuffer bigger = ByteBuffer.allocate(heapSize + maxStackSize);
		bigger.position(0);
		bigger.put(heapSegment);
		bigger.position(heapSize);
		bigger.put(stackSegment);

		MetaData last = metaData.get(metaData.size() - 1);
		if (last.used) {
			
			metaData.add(new MetaData(last.start + last.length, oldHeapSize));
		} else {
			last.length += oldHeapSize;
		}
		bytes = bigger;
	}

	private static void increaseStack() {
		maxStackSize *= 2;
		ByteBuffer bigger = ByteBuffer.allocate(heapSize + maxStackSize);
		bytes.position(0);
		bigger.put(bytes);
		bytes = bigger;
	}

	//adds to end of stack
	public static void push(int length) {
		push(new byte[length]);
	}
	//adds to end of stack
	public static void push(byte[] data) {
		while (stackSize + data.length > maxStackSize) {
			increaseStack();
		}
		bytes.position(heapSize + stackSize);
		bytes.put(data);
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
		increaseHeap();
		return alloc(length);
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

	
	public static void load(int address, byte[] value) {
		//System.out.println(normAdd(address));
		bytes.position(normAdd(address));
		bytes.put(value);
	}

	public static void set(int address, int value, int length) {
		byte[] v = Memory.get(value, length);
		Memory.load(address, v);
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
		bytes.position(normAdd(address));
		byte[] result = new byte[length];
		bytes.get(result);
		return result;
	}

	public static int getInt(int address) {
		return bytes.getInt(normAdd(address));
	}

	public static void printContents() {
		int c = 0;
		byte[] section;
		while (c < metaData.size()) {
			MetaData d = metaData.get(c);
			System.out.println(d);
			int add = d.start;
			while (d.length > add) {
				byte thing = get(add, 1)[0];
				System.out.println((int) thing);
				add += 1;
			}
			c += 1;
		}

		int add = 0;
		System.out.println("Stack. start: " + heapSize + ", length: " + stackSize);
		while (stackSize > add) {
			System.out.println((int) get(heapSize + add, 1)[0]);
			add += 1;
		}
	}
	public static void printMetData() {
		System.out.println(metaData);
	}

	public static int getHeapSize() {
		return heapSize;
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