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
		
	protected static String indentStr() {
		var result = "";
		for (Integer c = 0; indent > c; c++) {
			result += printIndentChar;
		}
		return result;
	}
	
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
	
	protected static Token currentToken() {
		if (tokens.size() > index) {
			return tokens.get(index);
		} else {
			return new Token(Token.TokenType.END, tokens.get(tokens.size() - 1).getLine());
		}
	}

	private static Expression expectExpression() {
		index++;
		return Expression.expect();
	}
}
