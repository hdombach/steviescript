package stevie;

import java.io.StreamTokenizer;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.function.Function;

public class Commands {
	//TODO: update some of the commands so that it takes into account length can be address
	//returns the length of the commmand. Ect, pop command is 1 long, add command is 4 long.
	public static int run(Integer command) {
		byte[] a;
		byte[] b;
		byte[] c;
		int size;
		switch (command) {
			case 0: //push
				size = Main.getInstruction(1);
				System.out.println(size);
				Memory.push(size);
				return 5;
			case 1: //pop
				size = Main.getInstruction(1);
				Memory.pop(size);
				return 5;
			case 2: //add
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				c = (new BigInteger(a).add(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 3: //sub
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				c = (new BigInteger(a).subtract(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 4: //divide
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				c = (new BigInteger(a).divide(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 5: //multiply
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				c = (new BigInteger(a).multiply(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 6: //mod
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				c = (new BigInteger(a).mod(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 7: //compare
				size = Main.getInstruction(13);
				a = Memory.get(Main.getInstruction(5), size);
				b = Memory.get(Main.getInstruction(9), size);
				int tempC = new BigInteger(a).compareTo(new BigInteger(b));
				c = ByteBuffer.allocate(4).putInt(tempC).array();
				Memory.set(Main.getInstruction(1), c);
				return 17;
			case 8: //out
				a = Memory.get(Main.getInstruction(1), Main.getInstruction(5));
				System.out.println(new BigInteger(a));
				return 9;
			case 9: //in
				//TODO: i don't know how i wanna do this yet
				return 2;
			case 10: //if
				a = Memory.get(Main.getInstruction(1), 1);
				if (0 > a[0]) {
					Main.jump(Main.getInstruction(2));
					return 0;
				} else {
					return 6;
				}
			case 11: //go
				Main.jump(Main.getInstruction(1));
				return 0;
			case 12: //load
				int length = Main.getInstruction(5);
				a = new byte[length];

				int i = 0;
				while (length > i) {
					a[i] = Integer.valueOf(Main.getInstruction(9 + i)).byteValue();
					i += 1;
				}
				Memory.set(Main.getInstruction(1), a);
				return 9 + length;
			case 13: //set
				Memory.set(Main.getInstruction(1), Memory.get(Main.getInstruction(5), Main.getInstruction(9)));
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
				int address = Memory.alloc(Main.getInstruction(5));
				Memory.set(Main.getInstruction(1), ByteBuffer.allocate(4).putInt(address).array());
				return 9;
			case 17: //free
				Memory.free(Main.getInstruction(1));
				return 5;
			case 99: //exit
				Main.stop();
				return 0;
			default:
				throw new Error("Unkown command");
		}
	}

}
