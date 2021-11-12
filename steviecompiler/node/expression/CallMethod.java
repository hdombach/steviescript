package steviecompiler.node.expression;

import steviecompiler.error.*;

import steviecompiler.node.Node;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;
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

	public void checkSymbols(SymbolTable scope) {
		throw new Error("Method Calls are not implemented yet.");
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
		result += param;
		Node.indent--;
		return result;
	};

	public int getReqMemory() {
		int big = 0;
		int result = 0;
		int temp;
		for (Expression exp : param.expressions) {
			result += exp.evaluatedType.getReqMemory();
			temp = exp.getReqMemory();
			if (temp > big) {
				big = temp;
			}
		}
		return result + big;
	}
}
