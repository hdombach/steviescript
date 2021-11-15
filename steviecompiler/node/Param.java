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

	public int getReqMemory() {
		System.out.println("WARNING: Param.getReqMemory should not be called.");
		return 0;
	}

	public static boolean compare(ArrayList<DataType> p1, ArrayList<DataType> p2) {
		if(p1.size() == p2.size()) {
			for(int i = 0; i < p1.size(); i++) {
				if (p1.get(i) == null || p2.get(i) == null) {
					return false;
				}
				if(!p1.get(i).getType().equals(p2.get(i).getType())) {
					return false;
				}
			}
			return true;
		}
		return false;
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
