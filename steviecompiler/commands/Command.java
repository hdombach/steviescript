package steviecompiler.commands;

import java.nio.ByteBuffer;
import java.util.ArrayList;

public abstract class Command {
    //location will be set by Block after all the commands have been added.
    int location;
    
    public String toAssembly() {
        return "You should not be seeing this message";
    }

    //converts int to 4 byte long list of ints
    protected static String parseInt(int value) {
        String result = "";
        byte[] bytes = ByteBuffer.allocate(4).putInt(value).array();
        int i = 0;
        while (4 > i) {
            result += Byte.valueOf(bytes[i]).intValue() + "\n";
            i += 1;
        }
        return result;
    }

    protected static String parseByte(byte b) {
        return Byte.valueOf(b).intValue() + "\n";
    }
    protected static String parseBytes(byte[] b) {
        int i = 0;
        String result = "";
        while (b.length > i) {
            result += parseByte(b[i]);
            i += 1;
        }
        return result;
    }

    public static ArrayList<Command> generate() {
       return null; //TODO: final generation of commands
    }

    public static void test() {
        String result = "";
        result += PushCommand.getAssembly(4); //a  -12
        result += PushCommand.getAssembly(4); //b -8
        result += PushCommand.getAssembly(4); //r -4
        result += LoadCommand.getAssembly(-12, 5);
        result += LoadCommand.getAssembly(-8, 7);
        result += AddCommand.getAssembly(-4, -8, -12);
        result += OutCommand.getAssembly(-4, 4);
        result += ExitCommand.getAssembly();
        System.out.println(result);
    }
}
