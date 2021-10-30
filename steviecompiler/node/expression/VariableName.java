package steviecompiler.node.expression;

import steviecompiler.node.expression.Expression;

import steviecompiler.Token;
import steviecompiler.node.Node;

public class VariableName extends Expression {
	public String name;

	public VariableName() {
		Token token = Node.currentToken();
		if (token.getType() == Token.TokenType.WORD){
			name = token.getContent();
			isValid = true;
			Node.index++;
		}
	}

	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}

	public String content() {
		return name;
	}
}
