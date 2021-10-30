package stevie;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Main {
	public static String filePath;
	public static ArrayList<Integer> instructions;

	public static void main(String[] args) {
		instructions = new ArrayList<Integer>();
		filePath = "test.s";
		if (args.length > 0) {
			filePath = args[0];
		}
		readFile(filePath);
		System.out.println(instructions);
	}

	public static void readFile(String path){
		try {
			Scanner reader = new Scanner(new File(path));
			while (reader.hasNextLine()) {
				String thisLine = reader.nextLine();
				commands.add(Integer.parseInt(thisLine));
			}
			reader.close();
		} catch (Exception e) {
			System.out.println(e);
			System.exit(1);
		}
	}
}
