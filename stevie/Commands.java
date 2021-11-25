package stevie;

import java.io.StreamTokenizer;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.function.Function;

public class Commands {
	//returns the length of the commmand. Ect, pop command is 1 long, add command is 4 long.
	public static int run(Integer command) {
		int aInt;
		int bInt;
		int cInt;
		byte[] a;
		byte[] b;
		byte[] c;
		int size;
		switch (command) {
			case 0: //push
				size = Main.getField(1);
				//System.out.println(size);
				Memory.push(size);
				return 5;
			case 1: //pop
				size = Main.getField(1);
				Memory.pop(size);
				return 5;
			case 2: //add
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt + bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 3: //sub
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt - bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 4: //divide
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt / bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 5: //multiply
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt * bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 6: //mod
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt % bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 7: //compare
				a = Memory.get(Main.getField(5), 4);
				aInt = ByteBuffer.wrap(a).getInt();
				b = Memory.get(Main.getField(9), 4);
				bInt = ByteBuffer.wrap(b).getInt();
				cInt = aInt - bInt;
				c = ByteBuffer.allocate(4).putInt(cInt).array();
				Memory.load(Main.getField(1), c);
				return 13;
			case 8: //out
				a = Memory.get(Main.getField(1), Main.getField(5));
				System.out.println(new BigInteger(a));
				return 9;
			case 9: //in
				//TODO: i don't know how i wanna do this yet
				return 2;
			case 10: //if
				a = Memory.get(Main.getField(1), 1);
				if (0 > a[0]) {
					Main.jump(Main.getField(2));
					return 0;
				} else {
					return 6;
				}
			case 11: //go
				Main.jump(Main.getField(1));
				return 0;
			case 12: //load
				int length = Main.getField(5);
				a = new byte[length];

				int i = 0;
				while (length > i) {
					a[i] = Integer.valueOf(Main.getInstruction(9 + i, 1)).byteValue();
					i += 1;
				}
				Memory.load(Main.getField(1), a);
				return 9 + length;
			case 13: //set
				size = Main.getField(9);
				Memory.set(Main.getField(1), Main.getField(5), size);
				return 13;
			case 14: //appends stuff
				/*a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = new byte[a.length + b.length];
				System.arraycopy(a, 0, c, 0, a.length);
				System.arraycopy(b, 0, c, a.length, b.length);
				Memory.set(Main.getInstruction(1), c);*/
				return 4;
			case 15: //get
			//deprecated
				return 5;
			case 16: //alloc
				size = Memory.getInt(Main.getField(5));
				int address = Memory.alloc(size);
				Memory.load(Main.getField(1), ByteBuffer.allocate(4).putInt(address).array());
				return 9;
			case 17: //free
				Memory.free(Main.getField(1));
				return 5;
			case 99: //exit
				Main.stop();
				return 0;
			default:
				throw new Error("Unkown command");
		}
	}

}
