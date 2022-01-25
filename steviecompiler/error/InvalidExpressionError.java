package steviecompiler.error;
import steviecompiler.node.expression.Expression;

public class InvalidExpressionError extends ErrorHandler {
    private Expression e;

    public InvalidExpressionError() {
        super();
        errorCode = 3;
        e = Expression.expect();
    }

    public String toString() {
        return "ERROR at " + errorLine + ": ERR03 Invalid Expression Error (Recieved: " + e.toString() + ")";
        //TODO: Make expressions able to print in a way that isn't the parser output
    }
}
