package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.symbol.SymbolTable;

public class BooleanExpression extends Expression {
    
    public BooleanExpression() {
        evaluatedType = new DataType("bool");
        int beginIndex = Node.index;
        if(currentToken().getType() == TokenType.TRUE) {
            expressionText += "True";
        }
        else if(currentToken().getType() == TokenType.FALSE) {
            expressionText += "False";
        } else {
            return;
        }
        try {
            Boolean.parseBoolean(expressionText);
        }
        catch(Exception e) {
                Node.index = beginIndex;
                isValid = false;
                return;
        }
        isValid = true;
        Node.index += 1;
    }

    public void checkSymbols(SymbolTable scope) {
        return;
    }
}
