package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;;

public class Set extends Statement {
	private static TokenType[] tokenSequence = {TokenType.WORD, TokenType.EQUALS};
	public String name;
	public Expression expression;

	public Set(){
		isValid = true;
		int beginIndex = Node.index;
		name = currentToken().getContent();
		for(int i = 0; i < 2; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				isValid = false;
				Node.index = beginIndex;
				return;
			}
		}

		expression = Expression.expect();
		if (!expression.isValid) {
			isValid = false;
			return;
		}
		
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Statement " + name + ": \n";
		Node.indent++;
		result += Node.indentStr() + "expression: \n";
		Node.indent++;
		result += expression.toString();
		Node.indent--;
		Node.indent--;
		return result;
	}
}
