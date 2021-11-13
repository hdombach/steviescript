package steviecompiler.error;

public class NoInputError extends ErrorHandler {

    public NoInputError() {
        System.out.println("ERR04 No input file given");
        System.exit(1);
    }

    @Override
    public String toString() {
        return null;
    }
    
}
