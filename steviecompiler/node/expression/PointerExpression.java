package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.Token;
import steviecompiler.node.Node;

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


    public String toString() {
        return Node.indentStr() + "Pointer: " + name + "\n";
    }
}