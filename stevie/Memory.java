package stevie;

import java.lang.reflect.Array;
import java.math.BigInteger;
import java.util.ArrayList;

public class Memory {
	private static ArrayList<byte[]> cont;
	private static ArrayList<Integer> freeThingies;
	
	public static void init() {
		cont = new ArrayList<byte[]>();
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
			System.out.println(get(c));
			c++;
		}
	}
}
