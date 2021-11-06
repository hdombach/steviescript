package steviecompiler.commands;

public class ExitCommand extends Command {
    public String toAssembly() {
        return getAssembly();
    }
    
    public static String getAssembly() {
        return "99\n";
    }
}
