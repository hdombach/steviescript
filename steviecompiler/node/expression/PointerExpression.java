package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.Token;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.symbol.SymbolTable;

public class PointerExpression extends Expression {
    private String name;
    private static TokenType[] tokenSequence = { TokenType.POINTER, TokenType.WORD };

    public PointerExpression() {
        Token token = null;
		for(int i = 0; i < 2; i++, Node.index++) {
            token = Node.currentToken();
            if(token.getType() != tokenSequence[i]) {
                return;
            }
        }
        name = token.getContent();
        isValid = true;
	}

    public void checkSymbols(SymbolTable scope) {
        if (!scope.inScope(name)) {
            throw new Error("Symbol " + name + " does not exist in scope");
        }
        evaluatedType = new DataType("pointer");
    }

    public String toString() {
        return Node.indentStr() + "Pointer: " + name + "\n";
    }
}