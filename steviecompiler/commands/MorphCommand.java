package steviecompiler.commands;

//this command edits the isntructions during runtime.
//This is how pointers will be handled.

public class MorphCommand extends Command {
    Command commnad;
    int offset;
    int value;

    public MorphCommand(Command command, int offset, int value) {
        this.commnad = command;
        this.offset = offset;
        this.value = value;
    }

    public String toAssembly() {
        return getAssembly(commnad.location + offset, value);
    }

    public static String getAssembly(int address, int value) {
        return SetCommand.getAssembly(address, value, 4);
    }

    public int getLength() {
        return 16;
    }
}