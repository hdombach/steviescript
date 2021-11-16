package steviecompiler.node;

import java.util.ArrayList;
import java.util.HashMap;

import steviecompiler.Main;
import steviecompiler.Token;
import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;

abstract public class Node {
	protected boolean unexpectedToken = false;
	protected static int index = 0;
	protected static ArrayList<Token> tokens;
	private static HashMap<String, Block> blocks = new HashMap<String, Block>();
	private static HashMap<String, ArrayList<Symbol>> sharedSymbolTable = new HashMap<String, ArrayList<Symbol>>();
	protected static Integer indent = 0;
	private static String printIndentChar = "⦙ ";
	protected boolean isValid = false;
	protected static TokenType expectedToken;
	private int line;

	public Node() {
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
			blocks.put(Main.filePath, new Block(sharedSymbolTable));
		} catch (Exception e) {
			System.out.println(e.getMessage());
			e.printStackTrace();
		}
		index = 0;
	}

	public static void checkScope() {
		for(Block b : blocks.values()) {
			b.checkSymbols(null);
		}
	}
	
	public static Block getCode(String key) {	return blocks.get(key);	};

	public static HashMap<String, Block> getCode() {	return blocks;	};
	
	public static Token currentToken() {
		if (tokens.size() > index) {
			return tokens.get(index);
		} else {
			return new Token(TokenType.END, tokens.get(tokens.size() - 1).getLine());
		}
	}

	/*private static Expression expectExpression() {
		//index++;
		return Expression.expect();
	}*/

	//Checks if symbols exist in current scope and if the data types are interchagable
	public abstract void checkSymbols(SymbolTable scope);

	//Gets the temp memory required to evalute things
	public abstract int getReqMemory();

	public ArrayList<Command> makeCommands(Block block) {
		//TODO: change this to abstract once later on.
		throw new Error("makeCommands is not implimented. Line: " + line);
	};

	public static void getAllReqMemory() {
		for(Block b : blocks.values()) {
			b.getReqMemory();
		} 
	}
	public static void makeAllCommands() {
		for(Block b : blocks.values()) {
			b.makeCommands(null);
		}
	}

	public int getLine() {
		return line;
	}

}
