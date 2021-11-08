package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;


public class Symbol {
    public static enum SymbolType {
        VALUE,
        FUNCTION
    }

    protected DataType datatype;
    private static int index = 0; //mabye ahve blocks do this
    private int size;
    protected int address;
    private SymbolType type;

    public Symbol(CreateVar c) {
        datatype = c.type;
        address = index;
        index++;
        memLoad();
        type = SymbolType.VALUE;
    }

    public Symbol(DefFunction f) {
        datatype = f.returnType;
        address = index;
        index++;
        memLoad();
        type = SymbolType.FUNCTION;
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

    public int getAddress() {
        return address;
    }
    
    public int getMemSize() {
        return size;
    }

    /**
     * Represents the flag as a string
     * @return A string representation of the flag
     */
    public String toString() {
        return"[" + datatype.getType() + ", " + address + ", " + size + "]";
    }
    
}
