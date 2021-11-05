package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class SetCommand extends Command{
    Symbol result;
    Symbol value;
    Symbol length;

    public String toAssembly() {
        return getAssembly(result.getAddress(), value.getAddress(), length.getAddress());
    }

    public static String getAssembly(int resultAddress, int valueAdress, int lengthAdress) {
        String result = "13\n";
        result += parseInt(resultAddress);
        result += parseInt(valueAdress);
        result += parseInt(lengthAdress);
        return result;
    }
}
