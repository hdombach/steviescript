package steviecompiler;

import java.util.ArrayList;

/**
 * The Token class implements methods to convert a line of code into tokens.
 * The Token object represents tokens in the stevieScript language.
 * 
 * @author Benjamin Boardman
 * @author Hezekiah Dombach
 */
public class Token {

    /** 
     * An enum of all the types of tokens stevieScript supports 
     */
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
        RETURN,
        BREAK,
        VAR,
        IMPORT,
        INCLUDE,
        CONDITIONAL,
        MATH,
        EQUALS,
        OPENCURLY,
        CLOSECURLY,
        OPENPARAN,
        CLOSEPARAN,
        OPENBRACK,
        CLOSEBRACK,
        PERIOD,
        COMMA,
        POINTER,
        ARROW,
        END,
    }

    /* Whitespace characters stevieScript supports */
    private static String whites = " \t\n;";
    
    /* Operators stevieScript supports */
    private static String specs = "@=.|><&-,";

    /* Enclosures stevieScript supports */
    private static String enclosures = "(){}[]";

    /* An ArrayList of tokens. Tokens will be added to the ArrayList when a code is tokenized. A new ArrayList is not generated. */
    private static ArrayList<Token> tokens = new ArrayList<Token>();

    /* Type of token as described by enum TokenType */
    private TokenType type;

    /* The token text */
    private String content;
    
    /* The line of code the token came from*/
    private int line;
    
    /**
     * Constructs a new token and adds it to the static ArrayList of tokens
     * 
     * @param type The type of token
     * @param line The line of code the token came from
     */
    public Token(TokenType type, int line){
        this.type = type;
        this.line = line;
        tokens.add(this);
    }

    /**
     * Constructs a new token and adds it to the static ArrayList of tokens
     * 
     * @param type The type of token
     * @param content The token text
     * @param line The line of code the token came from
     */
	public Token(TokenType type, String content, int line){
        this(type, line);
        this.content = content; 
    }

    /**
     * Gets the ArrayList of all the tokens
     * @return the ArrayList of all the tokens
     */
    public static ArrayList<Token> getTokenList()   { return tokens; }
    
    /**
     * Gets the type of the token
     * @return the type of token
     */
    public TokenType getType()                      { return type; }
    
    /**
     * Gets the text of the token
     * @return The token text, if any
     */
    public String getContent()                      { return content; }
    
    /**
     * Gets the line of code where the token came from
     * @return the line of code where the token came from
     */
    public int getLine()                            { return line; }

    /**
     * Represents the token as a String
     * 
     * @return A string representation of the token
     */
    public String toString() {
        String asString = "{" + type;
        if(content != null) {
            return asString + ", " + content + ", line " + line + "}";
        }
        return asString + ", line " + line + "}"; 
    }

    /**
     * Checks if two tokens are the same type and have the same text
     * 
     * @param token Another Token object
     * @return true if the two tokens are the same
     */
    public boolean equals(Token token) {
        return type == token.getType() && content == token.getContent();
    }

    /**
     * Takes in a line of code and splits it into tokens.
     * The code takes in a line of code as a String and cycles through the String by character.
     * It appends the character to an accumulator string.
     * When the character hits a whitespace or a new token, the accumulated string is made into a new token and cleared.
     * The loop continues until the end of the code.
     * 
     * @param code A line of code to convert into tokens
     * @param line The line number of the code
     */
    public static void tokenize(ArrayList<String> code) {
        boolean isSpecial = false;
        String accumulator = "";
        boolean isString = false;
        boolean isBackSlash = false;
        for(int i = 0; i < code.size(); i++) {
            if(code.get(i).length() == 0) {
                accumulator = "";
            }
            for(int j = 0; j < code.get(i).length(); j++) {
                if(j < code.get(i).length() - 2 && code.get(i).substring(j, j + 2).equals("//")) {
                    break;
                }
                String c = code.get(i).charAt(j) + "";
                if (isString) {
                    if (isBackSlash) {
                        accumulator += c;
                        isBackSlash = false;
                    } else {
                        if (c.equals("\"")) {
                            isString = false;
                            createString(accumulator, i++);
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
                        createToken(accumulator, i++);
                        accumulator = "";
                    } else if (specs.contains(c) && !isSpecial) {
                        createToken(accumulator, i++);
                        accumulator = c;
                        isSpecial = true;
                    } else if (whites.contains(c)) {
                        createToken(accumulator, i++);
                        accumulator = "";
                        isSpecial = false;
                    } else if(enclosures.contains(c)) {
                        createToken(accumulator, i++);
                        createToken(c, i++);
                        accumulator = "";
                        isSpecial = false;
                    } else {
                        if(specs.contains(c)) {
                            accumulator += c;
                        } else if (isSpecial) {
                            createToken(accumulator, i++);
                            accumulator = c;
                            isSpecial = false;
                        } else {
                            accumulator += c;
                        }
                    }
                }
            }
            if (!accumulator.equals("")) {
                createToken(accumulator, i++);
            }
        }
    }

    /**
     * Creates a new Token from content.
     * 
     * @param content The text in the string
     * @param line The line of code where the string is from
     */
    private static void createString(String content, int line) {
        new Token(TokenType.STRING, content, line);
    }

    /**
     * Creates a new Token object given tokenText.
     * 
     * @param tokenText The text of the token
     * @param line THe line of code where the token is from
     */
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
            case "break":
                new Token(TokenType.BREAK, line);
                break;
            case "import":
                new Token(TokenType.IMPORT, line);
                break;
            case "include":
                new Token(TokenType.INCLUDE, line);
                break;
            case "int":
            case "float":
            case "boolean":
            case "char":
            //add any other primitive data types here
            case "var":
                new Token(TokenType.VAR, tokenText, line);
                break;
            case ">":
            case "<":
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
                break;
            case "":
                break;
            case ",":
                new Token(TokenType.COMMA, line);
                break;
            case ".":
                new Token(TokenType.PERIOD, line);
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
            case "@":
                new Token(TokenType.POINTER, line);
                break;
            case "->":
                new Token(TokenType.ARROW, line);
                break;
            default:
                try{
                    Double.parseDouble(tokenText);
                    new Token(TokenType.NUMBER, tokenText, line);
                } catch(Exception e) {
                    new Token(TokenType.WORD, tokenText, line);
                }
        }
    }

    public static void clear() {
        tokens = new ArrayList<Token>();
    }
}
