package steviecompiler.error;

import steviecompiler.node.Statement;
import steviecompiler.Main;

public class InvalidStatementError extends ErrorHandler{
    private String statement;

    public InvalidStatementError(int line) {
        super();
        errorCode = 2;
        errorLine = line;
        statement = Main.getLine(line);
    }

    public String toString() {
        return "ERROR at " + filePath + " line " + errorLine + ": ERR02 Invalid Statement (Recieved: \"" + statement + "\")";
    }
}