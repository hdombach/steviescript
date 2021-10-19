
import java.util.ArrayList;
import java.util.Optional;

public class Node {
	private static int index = 0;
	private static ArrayList<Token> tokens;
	public static void parse(ArrayList<Token> tokensIn) {
		tokens = tokensIn;
		try {
			block();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	public String toString() {
		return "Nodes";
	}

	private static Block block() {
		
		return null;
	}

	private static boolean expectToken(Token token) {
		if (tokens.get(index).compare(token)) {
			index++;
			return true;
		} else {
			return false;
		}
	}
	private static boolean expectTokenType(Token.TokenType type) {
		if (tokens.get(index).getType() == type) {
			index++;
			return true;
		} else {
			return false;
		}
	}
}

class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
}

class Statement extends Node {

}