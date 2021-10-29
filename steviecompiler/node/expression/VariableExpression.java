package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.node.Node;

public class VariableExpression extends Node {
	public String name;

	public VariableExpression() {
		if (Node.currentToken().getType() == TokenType.WORD) {
			name = Node.currentToken().getContent();
			isValid = true;
		}
	}

	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}
}
