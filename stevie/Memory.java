package stevie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Memory {
	private static ArrayList<Byte> cont;
	
	public static void init() {
		cont = new ArrayList<Byte>();
	}

	public static ArrayList<Byte> get(int address, Integer length) {
		ArrayList<Byte> result = new ArrayList<Byte>();
		int start;
		if (0 > address) {
			start = cont.size() - address;
		} else {
			start = address;
		}
		int c = 0;
		while (length > c) {
			result.add(cont.get(start + c));
			c += 1;
		}
		return result;
	}
}
