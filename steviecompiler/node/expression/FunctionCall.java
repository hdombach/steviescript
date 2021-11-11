package steviecompiler.node.expression;

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
			throw new Error("Symbol " + name + " does not exist");
		} else {
			evaluatedType = s.dataType;
		}

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
