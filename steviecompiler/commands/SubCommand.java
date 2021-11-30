package steviecompiler.commands;

public class SubCommand extends Command {
    int result;
    int a;
    int b;

    public SubCommand(int result, int a, int b){
        this.result = result;
        this.a = a;
        this.b = b;
    }

    public String toAssembly() {
        return getAssembly(result, a, b);
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "3\n";
        result += parseInt(resultAddress);
        result += parseInt(aAddress);
        result += parseInt(bAddress);
        return result;
    }

    public int getLength() {
        return 13;
    }

    public String toString() {
        return "Subtract " + result + " = " + a + " - " + b;
    }
}
