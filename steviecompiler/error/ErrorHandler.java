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
    }

    public static void generate(int key) {
        switch(key) {
            case 1:
                generateUnexpectedTokenError();
                break;
            case 2:
                generateInvalidStatementError(Node.currentToken().getLine());
                break;
            default:
        }

    }

    private static void generateUnexpectedTokenError() {
        new UnexpectedTokenError();
    }

    private static void generateInvalidStatementError(int line) {
        new InvalidStatementError(line);
    }

    /*GenerateXYZError(param p) {
        new XYZError(p);
    }*/

    public static void throwErrors() {
        for(ErrorHandler e : errors) {
            System.out.println(e);
        }
        System.out.println("Parsed with " + errors.size() + " errors.");
    }
}
