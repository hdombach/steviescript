package steviecompiler.commands;

import steviecompiler.node.Block;

public class GoCommand extends Command {
    Command command;
    Block block;
    int offset = 0;
    
    public GoCommand(Command command) {
        this.command = command;
    }
    public GoCommand(Block block) {
        this.block = block;
    }
    public GoCommand() {
        this.command = null;
    }
    public GoCommand(int offset, Command command) {
        this(command);
        this.offset = offset;
    }

    private int getLocationValue() {
        if (block != null) {
            Command first = block.getFirstCommand();
            if (first == null) {
                return 0;
            } else {
                return block.getFirstCommand().location + offset;
            }
        } else if (command != null) {
            return command.location + offset;
        } else {
            return 0;
        }
    }

    public String toAssembly() {
        return getAssembly(getLocationValue());
    }

    public static String getAssembly(int line) {
        String result = "11\n";
        result += parseInt(line);
        return result;
    }

    public int getLength() {
        return 5;
    }

    public void addCommand(Command command, int offset) {
        this.command = command;
        this.offset = offset;
    }

    public String toString() {
        return "Goto " + command + " (" + getLocationValue() + ")";
    }
}
  