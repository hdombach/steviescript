package steviecompiler.commands;

public class GoCommand extends Command {
    int line;

    public String toAssembly() {
        return getAssembly(line);
    }

    public static String getAssembly(int line) {
        String result = "11\n";
        result += parseInt(line);
        return result;
    }
}
  