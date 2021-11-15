package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.DefParam;


public class Symbol {
    public static enum SymbolType {
        VALUE,
        FUNCTION,
        DATATYPE,
        OPERATOR
    }

    //Addresses are handeled by SymbolTable.getValueAdress(name)
    public DataType dataType;
    private int size;
    protected SymbolType type;

    public Symbol(DefParam p) {
        dataType = p.type;
        type = SymbolType.VALUE;
    }

    public Symbol(CreateVar c) {
        dataType = c.type;
        //memLoad();
        type = SymbolType.VALUE;
    }
    
    public Symbol(DataType t) {
        dataType = t;
        type = SymbolType.DATATYPE;
    }

    /*public void memLoad() {
        switch(dataType.getType()) {
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
    }*/ //moving to DataType

    public void malloc(int n) {
        size = n;
    }
    
    public int getMemSize() {
        return size;
    }

    /**
     * Represents the flag as a string
     * @return A string representation of the flag
     */
    public String toString() {
        if (dataType == null) {
            return "[null]";
        }
        return"[" + dataType.getType() + ", " + size + "]";
    }
    
}
