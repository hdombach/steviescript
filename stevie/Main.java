package stevie;

import java.io.File;
import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static String filePath;
	private static int programCounter;
	private static Boolean shouldExit;

	public static int getInstruction(int offset) {
		BigInteger big = new BigInteger(Memory.get(programCounter + offset));

		return big.intValue();
	}

	public static void stop() {
		shouldExit = true;
	}
	public static void jump(int newCounter) {
		programCounter = newCounter;
	}

	public static void main(String[] args) {
		Memory.init(128, 128);

		Memory.alloc(4);
		Memory.alloc(9);
		Memory.alloc(5);
		Memory.alloc(2);
		Memory.free(4);
		Memory.free(13);
		Memory.printMetData();
		Memory.printContents();

		if (true) {
			return;
		}
		shouldExit = false;
		programCounter = 0;
		filePath = "test.s";
		if (args.length > 0) {
			filePath = args[0];
		}
		readFile(filePath);

		//atually run code
		while (!shouldExit) {
			programCounter += Commands.run(Memory.getInt(programCounter));
			//System.out.println(getInstruction(0));
		}

		//Memory.printContents();
	}

	public static void readFile(String path){
		try {
			Scanner reader = new Scanner(new File(path));
			ArrayList<Byte> commands = new ArrayList<Byte>();
			while (reader.hasNextLine()) {
				String thisLine = reader.nextLine();
				int value = Integer.parseInt(thisLine);
				byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
				int c = 0;
				while (4 > c) {
					commands.add(bytes[c]);
					c += 1;
				}
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
