package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class SubCommand extends Command {
    int result;
    int a;
    int b;

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
}
