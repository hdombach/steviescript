package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class OutCommand extends Command {
    Symbol a;
    int length;

    public static String getAssembly(int a, int length) {
        String result = "8\n";
        result += parseInt(a);
        result += parseInt(length);
        return result;
    }

    public int getLength() {
        return 12;
    }
}
