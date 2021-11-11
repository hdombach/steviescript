package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.symbol.SymbolTable;
import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;

public class DefParam extends Node {
    public DataType type;
    public String name;

    public DefParam(){
        int beginIndex = Node.index;

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
}
