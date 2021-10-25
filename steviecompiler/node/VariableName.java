package steviecompiler.node;

import steviecompiler.Token;

public class VariableName extends Node {
	public String name;

	public VariableName(){
		Token token = Node.currentToken();
		if (token.getType() == Token.TokenType.WORD){
			name = token.getContent();
			isValid = true;
		}
	}

	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}
}
