package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.expression.Expression;

public class While extends Statement {
	public Expression condition;
	public Block loop;

	public While() {
		int beginIndex = Node.index;
		if (Node.currentToken().getType() == TokenType.WHILE) {
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
		if(!condition.isValid) {
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

		loop = new Block();

		if(Node.currentToken().getType() == TokenType.CLOSECURLY) {
			Node.index++;
		} else {
			expectedToken = TokenType.CLOSECURLY;
			ErrorHandler.generate(001);
		}
		isValid = true;
	}
	
	public String toString() {
		String result = "";
		result += Node.indentStr() + "While: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += loop;
		Node.indent-= 2;
		return result;
	}
}