package steviecompiler.node;

import steviecompiler.symbol.SymbolTable;
import steviecompiler.Token.TokenType;

public class DefParam extends Node {
    public DataType type;
    public String name;

    public DefParam(){

        type = new DataType();
        if (!type.isValid){
            Node.expectedToken = TokenType.VAR;
            unexpectedToken = true;
            return;
        }

        Node.index++;
        if (Node.currentToken().getType() != TokenType.WORD){
            Node.expectedToken = TokenType.WORD;
            unexpectedToken = true;
            return;
        }
        name = Node.currentToken().getContent();
        isValid = true;
    }

    public String toString() {
        String result = "";
        result += Node.indentStr() + "Paramater Definition: \n";
        Node.indent += 1;
        result += Node.indentStr() + "Name: " + name + "\n";
        result += Node.indentStr() + "Type: \n";
        Node.indent += 1;
        result += type;
        Node.indent -= 2;
        return result;
    }

    public void checkSymbols(SymbolTable scope) {
        return;
    }
    public int getReqMemory() {
        return 0;
    }
}
