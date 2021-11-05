package steviecompiler.commands;

public class PopCommand extends Command {
    int length;

    public String toAssembly() {
        return getAssembly(length);
    }

    public static String getAssembly(int length) {
        String result = "";
        result += "1\0";
        result += parseInt(length);
        return result;
    }
}
