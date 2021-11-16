package steviecompiler.commands;

public class SetCommand extends Command{
    int result;
    int value;
    int length;

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
}
