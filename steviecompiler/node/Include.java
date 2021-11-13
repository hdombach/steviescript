package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.symbol.SymbolTable;

public class Include extends Statement {
    private String iPackage;

    public Include() {
        int beginIndex = Node.index;
        if(Node.currentToken().getType() != TokenType.INCLUDE) {
            return;
        }

        Node.index++;

        if(Node.currentToken().getType() != TokenType.WORD) {
            unexpectedToken(beginIndex);
            return;
        }

        iPackage = Node.currentToken().getContent();
        isValid = true;
        Node.index++;
    }

    @Override
    public void checkSymbols(SymbolTable scope) {

    }

    @Override
    public int getReqMemory() {
        return 0;
    }

    public String toString() {
        String result = "";
        result += Node.indentStr() + "Import statement: \n";
        Node.indent++;
        result += Node.indentStr() + "package: " + iPackage + "\n";
        Node.indent--;
        Node.indent--;
        return result;
    }
}
