package steviecompiler.commands;

public class AddCommand extends Command {
    int result;
    int a;
    int b;

    public AddCommand(int result, int a, int b) {
        this.result = result;
        this.a = a;
        this.b = b;
    }

    public String toAssembly() {
        return getAssembly(result, a, b);
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "2\n";
        result += parseInt(resultAddress) + "\n";
        result += parseInt(aAddress) + "\n";
        result += parseInt(bAddress) + "\n";
        return result;
    }

    public int getLength() {
        return 16;
    }
}
