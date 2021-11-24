package steviecompiler.symbol;

import java.util.ArrayList;

import steviecompiler.commands.AddCommand;
import steviecompiler.commands.Command;
import steviecompiler.commands.LoadCommand;
import steviecompiler.commands.PushCommand;
import steviecompiler.commands.SetCommand;

public class LocalAddress {
    int frame;
    int offset;

    LocalAddress(int frame, int offset) {
        this.frame = frame;
        this.offset = offset;
    }
    LocalAddress increased() {
        this.frame += 1;
        return this;
    }

    
    /**
     * Generates insturctions that will append correct address onto the stack.
     * @return
     */
    public ArrayList<Command> getCommands() {
        ArrayList<Command> c = new ArrayList<Command>();
        c.add(new PushCommand(4));
        if (this.frame == 0) {
            c.add(new SetCommand(-4, 0, 4));
        } else {
            c.add(new LoadCommand(-4, offset));
            c.add(new AddCommand(-4, 0, -4));
        }
        return c;
    }
}
