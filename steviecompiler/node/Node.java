package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token;
import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

abstract public class Node {
	protected boolean unexpectedToken = false;
	protected static int index = 0;
	protected static ArrayList<Token> tokens;
	private static Block block;
	protected static Integer indent = 0;
	private static String printIndentChar = "⦙ ";
	protected boolean isValid = false;
	protected static TokenType expectedToken;
	private int line;

	Node() {
		line = currentToken().getLine();
	}
		
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
			block = new Block(null);
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
	}

	public static void checkScope() {
		block.checkSymbols(null);
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
		//index++;
		return Expression.expect();
	}

	//Checks if symbols exist in current scope and if the data types are interchagable
	public abstract void checkSymbols(SymbolTable scope);

	//Gets the temp memory required to evalute things
	public abstract int getReqMemory();

	public int makeCommands(Block block) {
		//TODO: change this to abstract once later on.
		throw new Error("makeCommands is not implimented. Line: " + line);
	};

	public static void getAllReqMemory() {
		block.getReqMemory();
	}
	public static void makeAllCommands() {
		block.makeCommands(null);
	}
	public int getLine() {
		return line;
	}

}
