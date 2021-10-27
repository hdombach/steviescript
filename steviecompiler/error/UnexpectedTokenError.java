package steviecompiler.error;

import steviecompiler.Token;

public class UnexpectedTokenError extends ErrorHandler {
    private String expected;
    private String recieved;



    public UnexpectedTokenError(Token expected, Token recieved) {
        super();
        errorCode = 1;
        this.expected = extractText(expected);
        this.recieved = extractText(recieved);
        errorLine = recieved.getLine();
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
