package steviecompiler.node.expression;

import steviecompiler.node.Node;
import steviecompiler.Token.TokenType;

public class FloatExpression extends NumericExpression {
    private static TokenType[] tokenSequence = {TokenType.NUMBER, TokenType.WORD /* period */, TokenType.NUMBER};
    private double value;

    public FloatExpression() {
        isValid = true;

        for(int i = 0; i <= 2; i++, Node.index++) {
            if(currentToken().getType() == tokenSequence[i]) {
                expressionText += currentToken().getContent();
            }
            else {
                isValid = false;
                break;
            }
        }
        try {
            value = Double.parseDouble(expressionText);
        }
        catch(Exception e) {
            isValid = false;
        }
        System.out.println(expressionText + ", " + isValid);
    }
}
