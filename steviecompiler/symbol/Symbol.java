package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;


public class Symbol {
    private DataType datatype;
    private Object val;

    public Symbol(CreateVar c) {
        datatype = c.type;
        val = c.expression;
    }
    
}
