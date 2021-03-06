package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class OutCommand extends Command {
    int a;
    int length;

    public OutCommand(int a, int length) {
        this.a = a;
        this.length = length;
    }

    public String toAssembly() {
        return OutCommand.getAssembly(a, length);
    }

    public static String getAssembly(int a, int length) {
        String result = "8\n";
        result += parseInt(a);
        result += parseInt(length);
        return result;
    }

    public int getLength() {
        return 9;
    }

    public String toString() {
        return "Output " + a + "length: " + length;
    }
}
