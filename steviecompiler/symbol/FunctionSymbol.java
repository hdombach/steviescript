package steviecompiler.symbol;

import java.util.ArrayList;

import steviecompiler.node.Block;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.DefParam;

public class FunctionSymbol extends Symbol {
    public  ArrayList<DataType> params;
    public Block block;
    public FunctionSymbol(DefFunction f) {
        super(f.returnType);
        params = new ArrayList<DataType>();
        for (DefParam param : f.params) {
            params.add(param.type);
        }
        type = SymbolType.FUNCTION;
        block = f.code;
    }
}
