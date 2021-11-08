package steviecompiler.commands;

import steviecompiler.symbol.Symbol;

public class IfCommand extends Command {
    Symbol a;
    Command command;

    public String toAssembly() {
        return getAssembly(a.getAddress(), command.location);
    }

    public static String getAssembly(int a, int line) {
        String result = "10\n";
        result += parseInt(a);
        result += parseInt(line);

        return result;
    }
}
