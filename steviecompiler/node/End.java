package steviecompiler.node;

import steviecompiler.Token.TokenType;

public class End extends Statement {
    public End() {
        if(Node.currentToken().getType() == TokenType.END) {
            isValid = true;
            Node.index++;
            return;
        }
    }

    public String toString() {
        String result = "";
		result += Node.indentStr() + "END\n";
        return result;
    }
}
