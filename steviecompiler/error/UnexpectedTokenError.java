package steviecompiler.error;

import steviecompiler.Token;
import steviecompiler.node.Node;

public class UnexpectedTokenError extends ErrorHandler {
    private String expected;
    private String recieved;



    public UnexpectedTokenError() {
        super();
        errorCode = 1;
        expected = extractText(Node.expectedToken());
        recieved = extractText(Node.currentToken());
        errorLine = Node.currentToken().getLine();
    }

    public String extractText(Token t) {
        String extracted;
        if (t.getType() == null) {
            return "NULL";
        }
        switch(t.getType()) {
            case TRUE:
                extracted = "true";
                break;
            case FALSE:
                extracted = "false";
                break;
            case EQUALS:
                extracted = "=";
                break;
            case RETURN:
                extracted = "return";
                break;
            case OPENPARAN:
                extracted = "(";
                break;
            case CLOSEPARAN:
                extracted = ")";
                break;
            case OPENBRACK:
                extracted = "[";
                break;
            case CLOSEBRACK:
                extracted = "]";
                break;
            case OPENCURLY:
                extracted = "{";
                break;
            case CLOSECURLY:
                extracted = "}";
                break;
            case PERIOD:
                extracted = ".";
                break;
            case COMMA:
                extracted = ",";
                break;
            default:
            extracted = t.getContent();
        }
        return extracted;
    }

    public String toString() {
        return "ERROR at " + filePath + " line " + errorLine + ": ERR01 Unexpected Token (Expected \"" + expected + "\", found \"" + recieved + "\")";
    }
}
