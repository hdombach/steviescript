package steviecompiler.node.expression;

import steviecompiler.Token;
import steviecompiler.node.Node;
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
		if (!scope.inScope(name)) {
			throw new Error("Symbol " + name + " does not exist in scope");
		}
		evaluatedType = scope.get(name).dataType;
	}

	public String content() {
		return name;
	}
}
