package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;

/**
 * The Flag class
 * 
 * @author
 */
public class Flag {
    /**
     * 
     */
    protected DataType datatype;
    
    /* */
    private static int index = 0;
    
    /**
     * 
     */
    protected int address;

    /**
     * 
     * @param c
     */
    public Flag(CreateVar c) {
        datatype = c.type;
        address = index;
        index++;
    }

    /**
     * Represents the flag as a string
     * @return A string representation of the flag
     */
    public String toString() {
        return"[" + datatype.name() + ", " + address + "]";
    }
    
}
