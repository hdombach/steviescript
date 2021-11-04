package steviecompiler.Command;

import java.math.BigInteger;
import java.nio.ByteBuffer;

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
}
