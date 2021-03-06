package steviecompiler.commands;

import java.util.ArrayList;

public class SetCommand extends Command{
    int result;
    int value;
    int length;

    public SetCommand(int result, int value, int length) {
        this.result = result;
        this.value = value;
        this.length = length;
    }

    public String toAssembly() {
        return getAssembly(result, value, length);
    }

    public static String getAssembly(int resultAddress, int valueAdress, int lengthAdress) {
        String result = "13\n";
        result += parseInt(resultAddress);
        result += parseInt(valueAdress);
        result += parseInt(lengthAdress);
        return result;
    }

    public int getLength() {
        return 13;
    }

    public String toString() {
        return "Set " + result + " = " + value + " length: " + length;
    }
}
