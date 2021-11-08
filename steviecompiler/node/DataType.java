package steviecompiler.node;

import steviecompiler.Token;
import steviecompiler.symbol.SymbolTable;

public class DataType extends Node {
	private String name;

	public DataType(){
		Token token = Node.currentToken();
		if (token.getType() == Token.TokenType.VAR) {
			name = token.getContent();
			isValid = true;
		}
	}

	public String toString(){
		return Node.indentStr() + "DataType: " + name + "\n";
	}

	public String getType() {
		return name;
	}

	public void checkSymbols(SymbolTable scope) {
		return;
	}
}