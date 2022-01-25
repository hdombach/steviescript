package steviecompiler.commands;

public class PopCommand extends Command {
    int length;

    public PopCommand(int length) {
        this.length = length;
    }

    public String toAssembly() {
        return getAssembly(length);
    }

    public static String getAssembly(int length) {
        String result = "";
        result += "1\n";
        result += parseInt(length);
        return result;
    }

    public int getLength() {
        return 5;
    }

    public String toString() {
        return "Pop " + length;
    }
}
