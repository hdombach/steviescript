package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;


public class Flag {
    protected DataType datatype;
    private static int index = 0;
    protected int address;

    public Flag(CreateVar c) {
        datatype = c.type;
        address = index;
        index++;
    }
    
}
