package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class CompCommand extends Command{
    Symbol result;
    Symbol a;
    Symbol b;
    int length;

    public String toAssembly() {
        return getAssembly(result.getAddress(), a.getAddress(), b.getAddress(), length);
    }

    public static String getAssembly(int resultAddress, int aAddress, int bAddress, int length) {
        String result = "7\n";
        result += parseInt(resultAddress);
        result += parseInt(aAddress);
        result += parseInt(bAddress);
        result += parseInt(length);
        return result;
    }
}
