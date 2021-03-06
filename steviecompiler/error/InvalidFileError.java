package steviecompiler.error;

public class InvalidFileError extends ErrorHandler {

    public InvalidFileError() { 
        super();
        errorCode = 5;
    }
    
    @Override
    public String toString() {
        return "ERR05 Invalid file given (recieved: " + filePath + ")";
    }
    
    
}
