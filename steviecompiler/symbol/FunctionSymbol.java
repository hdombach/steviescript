package steviecompiler.symbol;

import java.util.ArrayList;

import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.DefParam;

public class FunctionSymbol extends Symbol {
    ArrayList<DataType> params;
    public FunctionSymbol(DefFunction f) {
        super(f.returnType);
        params = new ArrayList<DataType>();
        for (DefParam param : f.params) {
            params.add(param.type);
        }
        type = SymbolType.FUNCTION;
    }
}
