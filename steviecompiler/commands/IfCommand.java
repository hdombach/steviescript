package steviecompiler.commands;

public class IfCommand extends Command {
    int a;
    Command command;

    public String toAssembly() {
        return getAssembly(a, command.location);
    }

    public static String getAssembly(int a, int line) {
        String result = "10\n";
        result += parseInt(a);
        result += parseInt(line);

        return result;
    }

    public int getLength() {
        return 9;
    }

    public String toString() {
        return "If " + a + " then goto " + command;
    }
}
