package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token;
import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;

abstract public class Node {
	protected static int index = 0;
	protected static ArrayList<Token> tokens;
	private static Block block;
	protected static Integer indent = 0;
	private static String printIndentChar = "| ";
	protected boolean isValid = false;
	protected static TokenType expectedToken;
		
	protected static String indentStr() {
		var result = "";
		for (Integer c = 0; indent > c; c++) {
			result += printIndentChar;
		}
		return result;
	}

	public boolean isValid() { return isValid; }
	public static Token expectedToken() { return new Token(expectedToken, currentToken().getLine()); }
	
	public static void parse(ArrayList<Token> tokensIn) {
		tokens = tokensIn;
		try {
			block = new Block();
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}
	
	public static Block getCode() {return block;};
	
	public static Token currentToken() {
		if (tokens.size() > index) {
			return tokens.get(index);
		} else {
			return new Token(TokenType.END, tokens.get(tokens.size() - 1).getLine());
		}
	}

	private static Expression expectExpression() {
		index++;
		return Expression.expect();
	}

}
