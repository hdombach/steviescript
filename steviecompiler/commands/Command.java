package steviecompiler.commands;

import java.math.BigInteger;
import java.nio.ByteBuffer;
import java.util.ArrayList;

public abstract class Command {
    
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
        }
        return result;
    }

    public static void test() {
        String result = PushCommand.getAssembly(2);
        System.out.println(result);
    }
}
