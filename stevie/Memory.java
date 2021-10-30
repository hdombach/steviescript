package stevie;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Memory {
	private static ArrayList<ArrayList<Byte>> cont;
	
	public static void init() {
		cont = new ArrayList<ArrayList<Byte>>();
	}

	public static ArrayList<Byte> get(int address, Integer length) {
		if (address < 0) {
			return cont.get(cont.size() - address);
		} else {
			return cont.get(address);
		}
	}
}
