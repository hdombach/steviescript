package steviecompiler.symbol;

import steviecompiler.node.DataType;

public class OperatorSymbol extends Symbol {
    DataType left;
    DataType right;
    String operation;

    public OperatorSymbol(DataType l, DataType r, DataType t) {
        super(t);
        type = SymbolType.OPERATOR;
        left = l;
        right = r;
    }

    public boolean match(DataType l, DataType r, DataType t) {
        boolean isL = l.compare(left);
        boolean isR = r.compare(right);
        boolean isT = t.compare(dataType);

        return isL && isR && isT;
    }
}
