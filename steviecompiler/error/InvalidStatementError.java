package steviecompiler.error;

import steviecompiler.node.Statement;
import steviecompiler.Main;

public class InvalidStatementError extends ErrorHandler{
    private String statement;

    public InvalidStatementError() {
        super();
        errorCode = 2;
        statement = Main.getLine(errorLine);
    }

    public String toString() {
        return "ERROR at " + filePath + " line " + errorLine + ": ERR02 Invalid Statement (Recieved: \"" + statement + "\")";
    }
}