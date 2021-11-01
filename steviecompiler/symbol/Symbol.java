package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;


public class Symbol {
    protected DataType datatype;
    private static int index = 0;
    private int size;
    protected int address;

    public Symbol(CreateVar c) {
        datatype = c.type;
        address = index;
        index++;
        memLoad();
    }

    public void memLoad() {
        switch(datatype.getType()) {
            case "int":
            case "float":
            case "pointer":
                size = 4;
                break;
            case "char":
            case "boolean":
            default:
                size = 1;
        }
    }

    public void malloc(int n) {
        size = n;
    }
    
    public int getMemSize() {
        return size;
    }

    public String toString() {
        return"[" + datatype.getType() + ", " + address + ", " + size + "]";
    }
    
}
