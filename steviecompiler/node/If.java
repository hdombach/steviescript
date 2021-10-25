package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.error.ErrorHandler;

public class If extends Statement {
	public Expression condition;
	public Block block;

	public If() {
		int beginIndex = Node.index;
		if (Node.currentToken().getType() == TokenType.IF) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		if (Node.currentToken().getType() == TokenType.OPENPARAN) {
			Node.index++;
		} else {
			expectedToken = TokenType.OPENPARAN;
			ErrorHandler.generate(001);
		}

		condition = Expression.expect();
		if (!condition.isValid) {
			throw new Error("Expected expression not " + Node.currentToken());
		}

		if (Node.currentToken().getType() == TokenType.CLOSEPARAN) {
			Node.index++;
		} else {
			expectedToken = TokenType.CLOSEPARAN;
			ErrorHandler.generate(001);
		}

		if (Node.currentToken().getType() == TokenType.OPENCURLY) {
			Node.index++;
		} else {
			expectedToken = TokenType.OPENCURLY;
			ErrorHandler.generate(001);
		}

		block = new Block();

		System.out.println(block);

		if (Node.currentToken().getType() == TokenType.CLOSECURLY) {
			Node.index++;
		} else {
			expectedToken = TokenType.CLOSECURLY;
			ErrorHandler.generate(001);
		}

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