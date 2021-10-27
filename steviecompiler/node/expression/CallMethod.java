package steviecompiler.node.expression;

import steviecompiler.error.*;

import steviecompiler.node.Node;
import steviecompiler.node.expression.Expression;
import steviecompiler.node.Param;
import steviecompiler.Token.TokenType;

public class CallMethod extends Expression {
	Expression body;
	String name;
	Param param;


	public CallMethod(Expression body) {
		int beginIndex = Node.index;

		if (Node.currentToken().getType() == TokenType.PERIOD) {
			Node.index++;
		} else {
			return;
		}

		if (Node.currentToken().getType() == TokenType.WORD) {
			name = Node.currentToken().getContent();
			Node.index++;
		} else {
			expectedToken = TokenType.WORD;
			ErrorHandler.generate(001);
		}
		param = new Param();
		if (!param.isValid()) {
			ErrorHandler.generate(001);
		}
		isValid = true;
		this.body = body;
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Call Method:\n";
		Node.indent++;
		result += Node.indentStr() + "Body:\n";
		Node.indent++;
		result += body;
		Node.indent--;
		result += Node.indentStr() + "Name: " + name + "\n";
		result += Node.indentStr() + "Params: \n" ;
		Node.indent++;
		result += param;
		return result;
	};
}
