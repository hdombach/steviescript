package steviecompiler.symbol;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;


public class Symbol {
    DataType datatype;
    Object val;

    public Symbol(CreateVar c) {
        datatype = c.type;
        this.val = c.expression;
    }
    
}
