package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.error.ErrorHandler;

public class If extends Statement {
	private static TokenType[] tokenSequence = {TokenType.IF, TokenType.OPENPARAN, TokenType.CLOSEPARAN, TokenType.OPENCURLY, TokenType.CLOSECURLY};
	public Expression condition;
	public Block block;

	public If() {
		int beginIndex = Node.index;
		int i;
		if (Node.currentToken().getType() == tokenSequence[0]) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		for(i = 1; i < 2; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				expectedToken = tokenSequence[i];
				unexpectedToken(beginIndex);
			}
		}

		condition = Expression.expect();
		if(!condition.isValid) {
			throw new Error("Expected expression not " + Node.currentToken());
		}

		for(i = 2; i < 4; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				expectedToken = tokenSequence[i];
				unexpectedToken(beginIndex);

			}
		}

		//if(!unexpectedToken) {
			block = new Block(); //TODO: fix error where unexpected token causes infinite loop
		//}

		i = 4;
		if (Node.currentToken().getType() != tokenSequence[i]) {
			expectedToken = tokenSequence[i];
			unexpectedToken(beginIndex);
		}
		Node.index++;
		isValid = true;
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "If: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += block;
		Node.indent-= 2;
		return result;
	}
}