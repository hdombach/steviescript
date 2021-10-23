package steviecompiler.node.expression;

import steviecompiler.node.Node;
import steviecompiler.Token.TokenType;

public class FloatExpression extends NumericExpression {
    private static TokenType[] tokenSequence = {TokenType.NUMBER, TokenType.WORD /* period */, TokenType.NUMBER};
    private double value;

    public FloatExpression() {
        int beginIndex = Node.index;
        isValid = true;

        for(int i = 0; i <= 2; i++, Node.index++) {
            if(currentToken().getType() == tokenSequence[i]) {
                expressionText += currentToken().getContent();
            }
            else {
                Node.index = beginIndex;
                isValid = false;
                return;
            }
        }
        try {
            value = Double.parseDouble(expressionText);
        }
        catch(Exception e) {
            Node.index = beginIndex;
            isValid = false;
        }
    }

    public double value() {
        return value;
    }
    public String toString() {
        return Node.indentStr() + "Float: " + value + "\n";
    }
}
