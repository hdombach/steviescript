package steviecompiler.node;

import steviecompiler.Token.TokenType;
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
			throw new Error("Expected ( not " + Node.currentToken());
		}

		condition = Expression.expect();
		if(!condition.isValid) {
			throw new Error("Expected expression not " + Node.currentToken());
		}

		if (Node.currentToken().getType() == TokenType.CLOSEPARAN) {
			Node.index++;
		} else {
			throw new Error("Expected ) not " + Node.currentToken());
		}

		if (Node.currentToken().getType() == TokenType.OPENCURLY) {
			Node.index++;
		} else {
			throw new Error("Expected {  not " + Node.currentToken());
		}

		loop = new Block();

		if(Node.currentToken().getType() == TokenType.CLOSECURLY) {
			Node.index++;
		} else {
			throw new Error("Expected } not " + Node.currentToken());
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