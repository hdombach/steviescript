package stevie;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;

public class Memory {
	private static ArrayList<byte[]> cont;
	private static ArrayList<Integer> freeThingies;
	private static int stackStart;
	
	public static void init() {
		cont = new ArrayList<byte[]>();
		freeThingies = new ArrayList<Integer>();
		stackStart = 0;
	}

	public static void var(int length) {
		cont.add(new byte[length]);
		stackStart += 1;
	}
	public static void var(byte[] data) {
		cont.add(data);
		stackStart += 1;
	}

	public static void push(int length) {
		cont.add(new byte[length]);
	}
	public static void push(byte[] data) {
		cont.add(data);
	}

	public static void pop() {
		cont.remove(cont.size() - 1);
	}

	public static int alloc(int length) {
		int result;

		if (freeThingies.size() > 0) {
			result = freeThingies.remove(freeThingies.size() - 1);
		} else {
			result = stackStart;
			stackStart += 1;
		}

		cont.set(result, new byte[length]);

		return result;
	}

	public static void free(int address) {
		freeThingies.add(address);
	}

	public static void set(int address, byte[] value) {
		if (address < 0) {
			cont.set(cont.size() - address, value);
		} else {
			cont.set(address, value);
		}
	}

	public static byte[] get(int address) {
		if (address < 0) {
			return cont.get(cont.size() - address);
		} else {
			return cont.get(address);
		}
	}

	public static int getInt(int address) {
		BigInteger big = new BigInteger(get(address));
		return big.intValue();
	}

	public static void printContents() {
		int c = 0;
		while (cont.size() > c) {
			System.out.println(Arrays.toString(get(c)));
			c++;
		}
	}
}
