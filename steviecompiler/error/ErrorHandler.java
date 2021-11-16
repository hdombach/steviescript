package steviecompiler.error;

import java.util.ArrayList;

import steviecompiler.Main;
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
        if(Node.currentToken() != null) {
            errorLine = Node.currentToken().getLine();
        }
    }

    public static void generate(int key) {
        switch(key) {
            case 1:
                new UnexpectedTokenError();
                break;
            case 2:
                new InvalidStatementError();
                break;
            case 3:
                new InvalidExpressionError();
                break;
            case 4:
                new NoInputError();
                break;
            case 5:
                new InvalidFileError();
                break;
            default:
        }

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
