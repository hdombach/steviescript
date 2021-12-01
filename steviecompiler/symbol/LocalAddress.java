package steviecompiler.symbol;

import java.util.ArrayList;

import steviecompiler.commands.AddCommand;
import steviecompiler.commands.Command;
import steviecompiler.commands.LoadCommand;
import steviecompiler.commands.MorphCommand;
import steviecompiler.commands.PopCommand;
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

    public String toString() {
        return "Frame: " + frame + ", offset: " + offset + "\n";
    }

    
    /**
     * Generates insturctions that will append correct address onto the stack.
     * @return
     */
    public ArrayList<Command> getCommands() {
        ArrayList<Command> c = new ArrayList<Command>();
        c.add(new PushCommand(4));
        c.add(new LoadCommand(-4, offset));
        if (this.frame == 0) {
            c.add(new AddCommand(-4, 0, -4));
        } else {
            c.add(new PushCommand(4)); //load the current frame onto stack
            c.add(new LoadCommand(-4, this.frame + 8));//find the correct offset for the right frame pointer
            c.add(new AddCommand(-4, 0, -4)); //get current address to the right frame pointer
            
            Command add = new AddCommand(-8, 0, -8); //will eventually create correct address by adding the offset
            c.add(new MorphCommand(add, 5, -4));
            c.add(add);
            c.add(new PopCommand(4));
        }
        return c;
    }
}
