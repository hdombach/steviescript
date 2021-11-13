package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.symbol.SymbolTable;

public class Break extends Statement {

    public Break() {
        if(Node.currentToken().getType() == TokenType.BREAK) {
            isValid = true;
            Node.index++;
        }
    }

    @Override
    public void checkSymbols(SymbolTable scope) {
       return;
    }

    @Override
    public int getReqMemory() {
        return 0;
    }

    public String toString() {
        String result = "";
		result += Node.indentStr() + "BREAK\n";
        return result;
    }
}
