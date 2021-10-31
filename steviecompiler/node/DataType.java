package steviecompiler.node;

import steviecompiler.Token;

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

	public String name() {
		return name;
	}
}