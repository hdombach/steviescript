package steviecompiler.node.expression;

import steviecompiler.node.Node;
import steviecompiler.Token.TokenType;

public class IntegerExpression extends NumericExpression {
    private static TokenType[] tokenSequence = {TokenType.NUMBER};
    private int value;

    public IntegerExpression() {
        isValid = true;

        for(int i = 0; i <= 1; i++, Node.index++) {
            if(currentToken().getType() == tokenSequence[i]) {
                expressionText += currentToken().getContent();
            }
            else {
                isValid = false;
                break;
            }
        }
        try {
            value = Integer.parseInt(expressionText);
        }
        catch(Exception e) {
            isValid = false;
        }
    }
}
