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

	private static int programStart = 4;

	//deafult field size is 4
	public static int getField(int offset) {
		return getInstruction(offset, 4);
	}

	public static int getCommand() {
		return getInstruction(0, 1);
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
		Memory.init(512, 512);

		Memory.alloc(4);
		
		shouldExit = false;
		programCounter = programStart;
		filePath = "test.s";
		if (args.length > 0) {
			filePath = args[0];
		}
		readFile(filePath);

		Memory.load(0, ByteBuffer.allocate(4).putInt(Memory.getHeapSize()).array());


		//atually run code
		while (!shouldExit) {
			if (programCounter > 1000) {
				shouldExit = true;
			}
			try {
				programCounter += Commands.run(getInstruction(0, 1));
			} catch(Exception e) {
				Memory.printContents();
				e.printStackTrace();
				shouldExit = true;
			}
		}

		//Memory.printContents();
	}

	//assumes that memory is empty so will start writing at 0
	public static void readFile(String path){
		try {
			System.out.println("read file");
			Scanner reader = new Scanner(new File(path));
			ArrayList<Byte> commands = new ArrayList<Byte>();
			int add = programStart;
			byte[] bytes = new byte[1];
			while (reader.hasNextLine()) {
				String thisLine = reader.nextLine();
				bytes[0] = (byte) Integer.parseInt(thisLine);
				Memory.load(add, bytes);
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
