package steviecompiler.node;

import steviecompiler.Token;
import steviecompiler.Token.TokenType;;

public class Set extends Statement {
	public String name;
	public Expression expression;

	public Set(){
		int beginIndex = Node.index;
		isValid = true;
		if (currentToken().getType() == Token.TokenType.WORD) {
			name = currentToken().getContent();
			Node.index++;
		} else {
			isValid = false;
		}

		if (currentToken().getType() == Token.TokenType.EQUALS) {
			Node.index++;
		} else {
			isValid = false;
		}

		expression = Expression.expect();
		if (!expression.isValid) {
			isValid = false;
		}

		if(!isValid) {
			Node.index = beginIndex;
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
