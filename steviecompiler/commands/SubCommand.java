package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class SubCommand extends Command {
    Symbol result;
    Symbol a;
    Symbol b;

    public String toAssembly() {
        return getAssembly(result.getAddress(), a.getAddress(), b.getAddress());
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "3\n";
        result += parseInt(resultAddress);
        result += parseInt(aAddress);
        result += parseInt(bAddress);
        return result;
    }
}
