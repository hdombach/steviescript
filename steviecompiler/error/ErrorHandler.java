package steviecompiler.error;

import steviecompiler.Main;
import steviecompiler.Token;
import steviecompiler.node.Node;

public abstract class ErrorHandler {
    protected int errorCode;
    protected int errorLine;
    protected String filePath;

    public abstract String toString();

    public ErrorHandler() {
        filePath = Main.filePath;
    }

    public static void generate(int key) {
        switch(key) {
            case 1:
                generateUnexpectedTokenError(Node.expectedToken(), Node.currentToken());
            default:
        }

    }

    private static void generateUnexpectedTokenError(Token expected, Token recieved) {
        new UnexpectedTokenError(expected, recieved);
    }

    /*GenerateXYZError(param p) {
        new XYZError(p);
    }*/

    protected void throwError() {
        System.out.println(this);
        System.exit(this.errorCode);
    }
}
