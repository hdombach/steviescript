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


	//deafult instruciton size is 4
	public static int getInstruction(int offset) {
		return getInstruction(offset, 4);
	}

	//commands are one byte long
	public static int getInstruction(int offset, int length) {
		BigInteger big = new BigInteger(Memory.get(programCounter + offset, length));

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
		
		shouldExit = false;
		programCounter = 0;
		filePath = "test.s";
		if (args.length > 0) {
			filePath = args[0];
		}
		readFile(filePath);

		System.out.println(Memory.getInt(1));

		if (true) {
			return;
		}

		Memory.printContents();

		//atually run code
		while (!shouldExit) {
			System.out.println(getInstruction(0) + ", " + programCounter);
			if (programCounter > 100) {
				shouldExit = true;
			}
			programCounter += Commands.run(getInstruction(0, 1));
		}

		//Memory.printContents();
	}

	//assumes that memory is empty so will start writing at 0
	public static void readFile(String path){
		try {
			Scanner reader = new Scanner(new File(path));
			ArrayList<Byte> commands = new ArrayList<Byte>();
			int add = 0;
			byte[] bytes = new byte[1];
			while (reader.hasNextLine()) {
				String thisLine = reader.nextLine();
				bytes[0] = (byte) Integer.parseInt(thisLine);
				Memory.set(add, bytes);
				add += 1;
			}
			Memory.alloc(add);
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
