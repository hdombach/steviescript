package stevie;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.Function;

public class Commands {
	
	//returns the length of the commmand. Ect, pop command is 1 long, add command is 4 long.
	public static int run(Integer command) {
		byte[] a;
		byte[] b;
		byte[] c;
		switch (command) {

			case 0: //push
				int size = Main.getInstruction(1);
				Memory.push(size);
				return 2;
			case 1: //pop
				Memory.pop();
				return 1;
			case 2: //add
				a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = (new BigInteger(a).add(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 4;
			case 3: //sub
				a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = (new BigInteger(a).subtract(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 4;
			case 4: //divide
				a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = (new BigInteger(a).divide(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 4;
			case 5: //multiply
				a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = (new BigInteger(a).multiply(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 4;
			case 6: //mod
				a = Memory.get(Main.getInstruction(2));
				b = Memory.get(Main.getInstruction(3));
				c = (new BigInteger(a).mod(new BigInteger(b))).toByteArray();
				Memory.set(Main.getInstruction(1), c);
				return 4;
				
			case 21: //exit
				Main.stop();
				return 0;
			default:
				return 0;
		}
	}

}
