package steviecompiler.node;
import steviecompiler.error.ErrorHandler;

public class InvalidStatement extends Statement {
    public InvalidStatement() {
        Node.index++;
        ErrorHandler.generate(002);
    }
}
