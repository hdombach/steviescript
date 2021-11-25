package steviecompiler.commands;

public class CompCommand extends Command{
    int result;
    int a;
    int b;

    public String toAssembly() {
        return getAssembly(result, a, b);
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "7\n";
        result += parseInt(resultAddress);
        result += parseInt(aAddress);
        result += parseInt(bAddress);
        return result;
    }

    public int getLength() {
        return 13;
    }

    public String toString() {
        return "Compare " + result + " = " + a + " > " + b;
    }
}
