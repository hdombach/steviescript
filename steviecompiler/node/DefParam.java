package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;

public class DefParam extends Node {
    private DataType type;
    private String name;

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
}
