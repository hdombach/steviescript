package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class ModCommand extends Command {
    Symbol result;
    Symbol a;
    Symbol b;

    public String toAssembly() {
        return getAssembly(result.getAddress(), a.getAddress(), b.getAddress());
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress) {
        String result = "6\n";
        result += parseInt(resultAddress);
        result += parseInt(aAddress);
        result += parseInt(bAddress);
        return result;
    }
}
