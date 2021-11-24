package steviecompiler.commands;

public class IfCommand extends Command {
    int a;
    Command command;

    public String toAssembly() {
        return getAssembly(a, command.location);
    }

    public static String getAssembly(int a, int line) {
        String result = "10\n";
        result += parseInt(a) + "\n";
        result += parseInt(line) + "\n";

        return result;
    }

    public int getLength() {
        return 12;
    }
}
