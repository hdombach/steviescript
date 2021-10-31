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

    //Needs toString()
}
