package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

import java.util.ArrayList;

//TODO: this is not actaully tested because it requires other things

public class Param extends Node {
	public ArrayList<Expression> expressions = new ArrayList<Expression>();

	public ArrayList<DataType> getParamTypes() {
		ArrayList<DataType> r = new ArrayList<DataType>();
		for (Expression exp : expressions) {
			r.add(exp.evaluatedType);
		}
		return r;
	}

	public Param() {
		int beginIndex = Node.index;

		if (Node.currentToken().getType() == TokenType.OPENPARAN) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}
		Expression e = Expression.expect();
		if (e.isValid) {
			expressions.add(e);
			while (Node.currentToken().getType() == TokenType.COMMA) {
				Node.index++;
				e = Expression.expect();
				if (e.isValid) {
					expressions.add(e);
				} else {
					Expression.invalid();
				}
			}
		} else {
			Node.index = beginIndex;
		}
		if (Node.currentToken().getType() == TokenType.CLOSEPARAN) {
			Node.index++;
		} else {

			expectedToken = TokenType.CLOSEPARAN;
			ErrorHandler.generate(001);
		}

		isValid = true;
	}

	public void checkSymbols(SymbolTable scope) {
		for (Expression exp : expressions) {
			exp.checkSymbols(scope);
		}
	}

	public String toString() {
		String result = "";
		Node.indent++;
		result += Node.indentStr() + "Paramaters:\n";
		Node.indent++;
		for (int i = 0; i < expressions.size(); i++) {
			result += expressions.get(i);
		}
		Node.indent -= 2;
		return result;
	}
}
