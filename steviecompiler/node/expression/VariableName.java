package steviecompiler.node.expression;

import steviecompiler.Token;
import steviecompiler.node.Node;
import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;

public class VariableName extends Expression {
	public String name;

	public VariableName() {
		Token token = Node.currentToken();
		if (token.getType() == Token.TokenType.WORD){
			name = token.getContent();
			isValid = true;
			Node.index++;
		}
	}

	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}

	public void checkSymbols(SymbolTable scope) {
		Symbol s = scope.getValue(name);
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist in scope");
		}
		evaluatedType = s.dataType;
	}

	public String content() {
		return name;
	}
	public int getReqMemory() {
		return 0;
	}
}
