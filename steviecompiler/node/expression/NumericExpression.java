package steviecompiler.node.expression;

import steviecompiler.symbol.SymbolTable;

public abstract class NumericExpression extends Expression {
    public void checkSymbols(SymbolTable scope) {
        return;
    }
}