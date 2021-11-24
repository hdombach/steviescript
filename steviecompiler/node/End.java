package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.symbol.SymbolTable;

public class End extends Statement {
    public End() {
        if(Node.currentToken().getType() == TokenType.END) {
            isValid = true;
            Node.index++;
            return;
        }
    }

    public void checkSymbols(SymbolTable scope) {
        return;
    }
    public int getReqMemory() {
        return 0;
    }

    public String toString() {
        String result = "";
		result += Node.indentStr() + "END\n";
        return result;
    }
    public ArrayList<Command> makeCommands(Block block) {
        return new ArrayList<Command>();
    }
}
