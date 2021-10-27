package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.node.Node;

public class BooleanExpression extends Expression {
    
    public BooleanExpression() {
        int beginIndex = Node.index;
        if(currentToken().getType() == TokenType.TRUE) {
            expressionText += "True";
        }
        else if(currentToken().getType() == TokenType.FALSE) {
            expressionText += "False";
        }
        try {
            Boolean.parseBoolean(expressionText);
        }
        catch(Exception e) {
                Node.index = beginIndex;
                isValid = false;
        }
    }
}
