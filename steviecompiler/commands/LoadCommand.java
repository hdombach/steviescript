package steviecompiler.commands;

import java.math.BigInteger;

import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;

public class LoadCommand extends Command {
    Symbol a;
    int ivalue;
    String svalue;

    public String toAssembly() {
        if (svalue != null) {
            return getAssembly(a.getAddress(), svalue);
        } else {
            return getAssembly(a.getAddress(), ivalue);
        }
    }

    public static String getAssembly(int resultAddress, int value) {
        String result = "12\n";
        result += parseInt(resultAddress);
        result += parseInt(4);
        result += parseInt(value);
        return result;
    } 
    public static String getAssembly(int resultAddress, String value) {
        byte[] data = value.getBytes();
        String result = "12\n";
        result += parseInt(resultAddress);
        result += parseInt(data.length);
        result += parseBytes(data);
        return result;
    }
}
