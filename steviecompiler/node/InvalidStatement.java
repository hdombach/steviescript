package steviecompiler.node;
import steviecompiler.error.ErrorHandler;

public class InvalidStatement extends Statement {
    public InvalidStatement() {
        ErrorHandler.generate(002);
        Node.index++;
        isValid = true;
    }
}
