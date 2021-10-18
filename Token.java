import java.util.ArrayList;

public class Token {
    public static enum TokenType {
        IF,
        ELSE,
        FOR,
	    NUMBER,
        WORD,
        STRING,
        WHILE,
        FUNCTION,
        STRUCT,
        FALSE,
        TRUE,
        VAR,

    }

    private static String whites = " \t\n;";
    private static String specs = "(){}=.|><&-";
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

    public String toString() {
        String asString = "{" + type;
        if(!content.equals("")) {
            return asString + ", " + content + "}";
        }
        return asString + "}"; 
    }

    /** 
    *
    * @param code - A line of code, to convert into a token
    *
    */
    public static void tokenize(String code) {
        boolean isSpecial = false;
        String accumulator = "";
        boolean isString = false;
        boolean isBackSlash = false;

        for(int i = 0; i < code.length(); i++) {
            String c = code.charAt(i) + "";
            if (isString) {
                if (isBackSlash) {
                    accumulator += c;
                    isBackSlash = false;
                } else {
                    if (c.equals("\"")) {
                        isString = false;
                        createString(accumulator);
                        accumulator = "";
                    } else if (c.equals("\\")) { //doesn't properly recognize "\n", "\t"
                        isBackSlash = true;
                    } else {
                        accumulator += c;
                    }
                }
            } else {
                if (c.equals("\"")) {
                    isString = true;
                    isBackSlash = false;
                    createToken(accumulator);
                    accumulator = "";
                } else if (specs.contains(c) && !isSpecial) {
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
            
        }
        if (!accumulator.equals("")) {
            createToken(accumulator);
        }
    }

    private static void createString(String content) {
        new Token(TokenType.STRING, content);
    }

    private static void createToken(String tokenText) {
        switch(tokenText) {
                case "if":
                    new Token(TokenType.IF, "");
                    break;
                case "else":
                    new Token(TokenType.ELSE, "");
                    break;
                case "for":
                    new Token(TokenType.FOR, "");
                    break;
                case "while":
                    new Token(TokenType.WHILE, "");
                    break;
                case "function":
                    new Token(TokenType.FUNCTION, "");
                    break;
                case "struct":
                    new Token(TokenType.STRUCT, "");
                    break;
                case "false":
                    new Token(TokenType.FALSE, "");
                    break;
                case "true":
                    new Token(TokenType.TRUE, "");
                    break;
                case "int":
                case "float":
                case "boolean":
                case "char":
                //add any other primitive data types here
                case "var":
                    new Token(TokenType.VAR, tokenText);
                    break;
                case "":
                    break;
                default:
                    try{
                        Integer.parseInt(tokenText);
                        Double.parseDouble(tokenText);
                        new Token(TokenType.NUMBER, tokenText);
                    } catch(Exception e) {
                        new Token(TokenType.WORD, tokenText);
                    }
                    break;
            }
    }
}
