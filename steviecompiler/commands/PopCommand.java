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
        result += "1\0";
        result += parseInt(length);
        return result;
    }

    public int getLength() {
        return 8;
    }
}
