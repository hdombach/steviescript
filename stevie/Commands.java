package stevie;

import java.util.ArrayList;
import java.util.function.Function;

public class Commands {
	
	public static int run(Integer command) {
		switch (command) {
			case 0: //push
				int size = Main.getInstruction(1);
				Memory.push(size);
				return 2;
				break;
		
			default:
				break;
		}
	}

}
