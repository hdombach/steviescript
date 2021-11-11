package steviecompiler.error;

import java.util.ArrayList;

import steviecompiler.Main;
import steviecompiler.Token;
import steviecompiler.node.Node;

public abstract class ErrorHandler {
    protected int errorCode;
    protected int errorLine;
    protected String filePath;
    protected static ArrayList<ErrorHandler> errors = new ArrayList<ErrorHandler>();

    public abstract String toString();

    public ErrorHandler() {
        errors.add(this);
        filePath = Main.filePath;
        errorLine = Node.currentToken().getLine();
    }

    public static void generate(int key) {
        switch(key) {
            case 1:
                generateUnexpectedTokenError();
                break;
            case 2:
                generateInvalidStatementError();
                break;
            case 3:
                generateInvalidExpressionError();
                break;
            default:
        }

    }

    private static void generateUnexpectedTokenError() {
        new UnexpectedTokenError();
    }

    private static void generateInvalidStatementError() {
        new InvalidStatementError();
    }

    private static void generateInvalidExpressionError() {
        new InvalidExpressionError();
    }

    /*GenerateXYZError(param p) {
        new XYZError(p);
    }*/

    public static int errorCount() {
        return  errors.size();
    }

    public static void throwErrors() {
        for(ErrorHandler e : errors) {
            System.out.println(e);
        }
        System.out.println("Parsed with " + errors.size() + " errors.");
        System.exit(1);
    }
}
