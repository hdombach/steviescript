package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;;

public class Set extends Statement {
	public String name;
	public Expression expression;

	public Set(){
		int beginIndex = Node.index;
		if (Node.currentToken().getType() == TokenType.WORD) {
			name = currentToken().getContent();
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		if (Node.currentToken().getType() == TokenType.EQUALS) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		expression = Expression.expect();
		if (!expression.isValid) {
			isValid = false;
		}
		isValid = true;
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
