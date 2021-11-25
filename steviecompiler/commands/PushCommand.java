package steviecompiler.commands;

public class PushCommand extends Command {
    int length;

    public PushCommand(int length) {
        this.length = length;
    }

    public String toAssembly() {
        return getAssembly(length);
    }

    public static String getAssembly(int length) {
        String result = "";
        result += "0\n";
        result += parseInt(length);
        return result;
    }

    public int getLength() {
        return 5;
    }

    public String toString() {
        return "Push " + length;
    }
}
