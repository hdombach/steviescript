package steviecompiler.node.expression;

import java.util.ArrayList;

import steviecompiler.commands.Command;
import steviecompiler.node.Block;
import steviecompiler.symbol.SymbolTable;

public abstract class NumericExpression extends Expression {
    public void checkSymbols(SymbolTable scope) {
        return;
    }

    //memory is already allocated for these data to be palced into
    public int getReqMemory() {
        return 0;
    }
}