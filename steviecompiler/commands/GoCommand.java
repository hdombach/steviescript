package steviecompiler.commands;

public class GoCommand extends Command {
    Command command;

    public String toAssembly() {
        return getAssembly(command.location);
    }

    public static String getAssembly(int line) {
        String result = "11\n";
        result += parseInt(line);
        return result;
    }

    public int getLength() {
        return 5;
    }

    public String toString() {
        return "Goto " + command;
    }
}
  