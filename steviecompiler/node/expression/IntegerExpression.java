package steviecompiler.node.expression;

import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.Token.TokenType;

public class IntegerExpression extends NumericExpression {
    private static TokenType[] tokenSequence = {TokenType.NUMBER};
    private int value;

    public IntegerExpression() {
        evaluatedType = new DataType("int");
        int beginIndex = Node.index;
        isValid = true;

        for(int i = 0; i < 1; i++, Node.index++) {
            if(Node.currentToken().getType() == tokenSequence[i]) {
                expressionText += Node.currentToken().getContent();
            }
            else {
                Node.index = beginIndex;
                isValid = false;
                return;
            }
        }
        try {
            value = Integer.parseInt(expressionText);
        }
        catch(Exception e) {
            Node.index = beginIndex;
            isValid = false;
        }
    }

    public int value() {
        return value;
    }
    public String toString() {
        return Node.indentStr() + "Int: " + value + "\n";
    }
}
