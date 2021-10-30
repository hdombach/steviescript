package stevie;

import java.io.File;
import java.math.BigInteger;
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

	public static void main(String[] args) {
		Memory.init();
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
		}

		Memory.printContents();
	}

	public static void readFile(String path){
		try {
			Scanner reader = new Scanner(new File(path));
			while (reader.hasNextLine()) {
				String thisLine = reader.nextLine();
				BigInteger value = new BigInteger(thisLine);
				Memory.var(value.toByteArray());
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
			e.printStackTrace();
			System.exit(1);
		}
	}
}
