package steviecompiler.commands;

public class MulCommand extends Command {
    int result;
    int a;
    int b;

    public String toAssembly() {
        return getAssembly(result, a, b);
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "5\n";
        result += parseInt(resultAddress) + "\n";
        result += parseInt(aAddress) + "\n";
        result += parseInt(bAddress) + "\n";

        return result;
    }

    public int getLength() {
        return 16;
    }
}
