package steviecompiler.node;

import java.util.ArrayList;
import java.util.Arrays;

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

	public DataType(String name) {
		this.name = name;
	}

	public String toString(){
		return Node.indentStr() + "DataType: " + name + "\n";
	}

	public String getType() {
		return name;
	}

	public void checkSymbols(SymbolTable scope) {
		if (!scope.inScope(name)) {
			throw new Error("Symbol " + name + " does not exist in scope.");
		}
	}

	public Boolean compare(DataType t) {
		return compare(t.name);
	}

    public Boolean compare(String t) {
        String[] _1 = {"byte", "char", "bool"};
        String[] _4 = {"int", "pointer"};
        Boolean is1 = Arrays.asList(_1).contains(name) && Arrays.asList(_1).contains(t);
        Boolean is4 = Arrays.asList(_4).contains(name) && Arrays.asList(_4).contains(t);
		Boolean is = name == t;
        return is1 || is4 || is;
    }
}