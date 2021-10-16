import java.util.ArrayList;

public class Token {
    public static enum TokenType {
        IF,
        ELSE,
        FOR,
        WORD
    }

    private static String doubles = "==&&||";
    private static String whites = " \t\n;";
    private static String specs = "(){}=.|><&";
    private static ArrayList<Token> tokens = new ArrayList<Token>();
    private TokenType type;
    private String content;
    
    public Token(TokenType type){
        this.type = type;
        tokens.add(this);
    }

	public Token(TokenType type, String content){
        this.type = type;
        this.content = content; 
        tokens.add(this);
    }

    public static ArrayList<Token> getTokenList()   { return tokens; }
    public TokenType getType()                      { return type; }
    public String getContent()                      { return content; }
    public String toString()                        { return "{" + type + ", " + content + "}"; }

    /** 
    *
    * @param code - A line of code, to convert into a token
    *
    */
    public static void tokenize(String code) {
        boolean isSpecial = false;
        String accumulator = "";
        
        for(int i = 0; i < code.length(); i++) {
            String c = code.charAt(i) + "";
            if (specs.contains(c) && !isSpecial) {
                createToken(accumulator);
                accumulator = c;
                isSpecial = true;
            } else if (whites.contains(c)) {
                createToken(accumulator);
                accumulator = "";
                isSpecial = false;
            } else {
                if(specs.contains(c)) {
                    accumulator += c;
                } else if (isSpecial) {
                    createToken(accumulator);
                    accumulator = c;
                    isSpecial = false;
                } else {
                    accumulator += c;
                }
            }
        }
        createToken(accumulator);
    }

    private static void createToken(String tokenText) {
        switch(tokenText) {
                case "if":
                    new Token(TokenType.IF, null);
                    break;
                case "else":
                    new Token(TokenType.ELSE, null);
                    break;
                case "for":
                    new Token(TokenType.FOR, null);
                    break;
                case "":
                    break;
                default:
                    new Token(TokenType.WORD, tokenText);
                    break;
            }
    }
    
    private static void createToken(char tokenText) {
        createToken(String.valueOf(tokenText));
    }
}