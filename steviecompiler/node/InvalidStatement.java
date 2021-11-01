package steviecompiler.node;
import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;

public class InvalidStatement extends Statement {
    public InvalidStatement() {
        ErrorHandler.generate(002);
        Node.index++;
        isValid = true;

        int line = Node.currentToken().getLine();

        while(Node.currentToken().getLine() == line && !(Node.currentToken().getType() == TokenType.END)) {
            Node.index++;
        }

    }
}
