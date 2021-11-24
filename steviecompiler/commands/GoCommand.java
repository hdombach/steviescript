package steviecompiler.commands;

public class GoCommand extends Command {
    Command command;

    public String toAssembly() {
        return getAssembly(command.location);
    }

    public static String getAssembly(int line) {
        String result = "11\n";
        result += parseInt(line) + "\n";
        return result;
    }

    public int getLength() {
        return 8;
    }
}
  