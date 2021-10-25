package steviecompiler;

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
        CONDITIONAL,
        MATH,
        EQUALS,
        RETURN,
        OPENCURLY,
        CLOSECURLY,
        OPENPARAN,
        CLOSEPARAN,
        OPENBRACK,
        CLOSEBRACK,
        END
    }

    private static String whites = " \t\n;";
    private static String specs = "(){}[]=.|><&-";
    private static ArrayList<Token> tokens = new ArrayList<Token>();
    private TokenType type;
    private String content;
    private int line;
    
    public Token(TokenType type, int line){
        this.type = type;
        this.line = line;
        tokens.add(this);
    }

	public Token(TokenType type, String content, int line){
        this(type, line);
        this.content = content; 
    }

    public static ArrayList<Token> getTokenList()   { return tokens; }
    public TokenType getType()                      { return type; }
    public String getContent()                      { return content; }
    public int getLine()                            { return line; }

    public String toString() {
        String asString = "{" + type;
        if(content != null) {
            return asString + ", " + content + ", line " + line + "}";
        }
        return asString + ", line " + line + "}"; 
    }

    public boolean equals(Token token) {
        return type == token.getType() && content == token.getContent();
    }

    /** 
    *
    * @param code - A line of code, to convert into a token
    *
    */
    public static void tokenize(String code, int line) {
        boolean isSpecial = false;
        String accumulator = "";
        boolean isString = false;
        boolean isBackSlash = false;
        
        for(int i = 0; i < code.length(); i++) {
            if(i < code.length() - 2 && code.substring(i, i + 2).equals("//")) {
                break;
            }
            String c = code.charAt(i) + "";
            if (isString) {
                if (isBackSlash) {
                    accumulator += c;
                    isBackSlash = false;
                } else {
                    if (c.equals("\"")) {
                        isString = false;
                        createString(accumulator, line);
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
                    createToken(accumulator, line);
                    accumulator = "";
                } else if (specs.contains(c) && !isSpecial) {
                    createToken(accumulator, line);
                    accumulator = c;
                    isSpecial = true;
                } else if (whites.contains(c)) {
                    createToken(accumulator, line);
                    accumulator = "";
                    isSpecial = false;
                } else {
                    if(specs.contains(c)) {
                        accumulator += c;
                    } else if (isSpecial) {
                        createToken(accumulator, line);
                        accumulator = c;
                        isSpecial = false;
                    } else {
                        accumulator += c;
                    }
                }
            }
            
        }
        if (!accumulator.equals("")) {
            createToken(accumulator, line);
        }
    }

    private static void createString(String content, int line) {
        new Token(TokenType.STRING, content, line);
    }

    private static void createToken(String tokenText, int line) {
        switch(tokenText) {
            case "if":
                new Token(TokenType.IF, line);
                break;
            case "else":
                new Token(TokenType.ELSE, line);
                break;
            case "for":
                new Token(TokenType.FOR, line);
                break;
            case "while":
                new Token(TokenType.WHILE, line);
                break;
            case "function":
                new Token(TokenType.FUNCTION, line);
                break;
            case "struct":
                new Token(TokenType.STRUCT, line);
                break;
            case "false":
                new Token(TokenType.FALSE, line);
                break;
            case "true":
                new Token(TokenType.TRUE, line);
                break;
            case "return":
                new Token(TokenType.RETURN, line);
                break;
            case "int":
            case "float":
            case "boolean":
            case "char":
            //add any other primitive data types here
            case "var":
                new Token(TokenType.VAR, tokenText, line);
                break;
            case "<=":
            case "!=":
            case ">=":
            case "&&":
            case "||":
            case "^^":
            case "==":
                new Token(TokenType.CONDITIONAL, tokenText, line);
                break;
            case "+":
            case "-":
            case "*":
            case "/":
                new Token(TokenType.MATH, tokenText, line);
                break;
            case "=":
                new Token(TokenType.EQUALS, line);
            case "":
                break;
            case "{":
                new Token(TokenType.OPENCURLY, line);
                break;
            case "}":
                new Token(TokenType.CLOSECURLY, line);
                break;
            case "(":
                new Token(TokenType.OPENPARAN, line);
                break;
            case ")":
                new Token(TokenType.CLOSEPARAN, line);
                break;
            case "[":
                new Token(TokenType.OPENBRACK, line);
                break;
            case "]":
                new Token(TokenType.CLOSEBRACK, line);
                break;
            default:
                try{
                    Integer.parseInt(tokenText);
                    Double.parseDouble(tokenText);
                    new Token(TokenType.NUMBER, tokenText, line);
                } catch(Exception e) {
                    new Token(TokenType.WORD, tokenText, line);
                }
        }
    }
}
