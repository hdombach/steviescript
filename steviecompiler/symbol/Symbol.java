package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
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

    public int getMemSize() {
        return dataType.getReqMemory();
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
