import java.util.ArrayList;

public class Token {
    private static int index = 0;
    private static ArrayList<Token> tokens = new ArrayList<Token>();

	public Token(){
        tokens.add(this);
        index++;
    }

    public static void tokenize (data) {
		
    }

    public static ArrayList<Token> getTokenList() {
        return tokens;
    }

    public String toString(){
        return "" + index;
    }
}