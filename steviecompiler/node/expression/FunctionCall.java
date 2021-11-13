package steviecompiler.node.expression;

import javax.naming.spi.DirStateFactory.Result;

import steviecompiler.Token.TokenType;
import steviecompiler.node.Node;
import steviecompiler.node.Param;
import steviecompiler.symbol.FunctionSymbol;
import steviecompiler.symbol.SymbolTable;

public class FunctionCall extends Expression {
	public String name;
	public Param param;

	public FunctionCall() {
		int beginIndex = Node.index;

		if (Node.currentToken().getType() == TokenType.WORD) {
			name = Node.currentToken().getContent();
			Node.index++;
		} else {
			return;
		}

		param = new Param();
		if (param.isValid()) {
			isValid = true;
			return;
		} else {
			Node.index = beginIndex;
		}
	}

	public void checkSymbols(SymbolTable scope) {
		param.checkSymbols(scope);

		FunctionSymbol s = scope.getFunction(name, param.getParamTypes());
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist");
		} else {
			evaluatedType = s.dataType;
		}

	}
	
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

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Function Call: " + name + "\n";
		Node.indent++;
		result += Node.indentStr() + "Params: \n";
		result += param;
		Node.indent--;
		return result;
	}
}
