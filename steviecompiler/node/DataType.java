package steviecompiler.node;

import java.util.Arrays;

import steviecompiler.Token;
import steviecompiler.symbol.Symbol;
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
		Symbol s = scope.getDataType(name);
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist in scope.");
		}
	}

	public int getReqMemory() {
		switch(getType()) {
            case "int":
            case "float":
            case "pointer":
                return 4;
            case "char":
            case "boolean":
            default:
                return 1;
        }
	}

	public Boolean compare(DataType t) {
		return compare(t.name);
	}

    public Boolean compare(String t) {
        String[] _1 = {"byte", "char", "boolean"};
        String[] _4 = {"int", "pointer"};
        Boolean is1 = Arrays.asList(_1).contains(name) && Arrays.asList(_1).contains(t);
        Boolean is4 = Arrays.asList(_4).contains(name) && Arrays.asList(_4).contains(t);
		Boolean is = name == t;
        return is1 || is4 || is;
    }
}