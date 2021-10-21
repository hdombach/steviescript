
import java.util.ArrayList;


abstract public class Node {
	private static int index = 0;
	private static ArrayList<Token> tokens;
	private static Block block;
	protected static Integer printIndent = 0;
	private static String printIndentChar = "| ";
	protected static String printIndentString() {
		var result = "";
		for (Integer c = 0; printIndent > c; c++) {
			result += printIndentChar;
		}
		return result;
	}
	public static void parse(ArrayList<Token> tokensIn) {
		tokens = tokensIn;
		try {
			block = expectBlock();
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	public static Block getCode() {return block;};
	private static Token currentToken() {
		return tokens.get(index);
	}

	private static Block expectBlock() {
		Block result = new Block();
		while (true) {
			if (tokens.size() <= index) {
				break;
			}

			Statement statement = expectStatement();
			if (statement != null) {
				result.statements.add(statement);
			} else {
				break;
			}
		}
		return result;
	}
	private static Statement expectStatement() {
		Statement temp = expectSet();
		if (temp != null) {
			return temp;
		}
		return null;
	}
	private static Set expectSet() {
		Set result = new Set();
		if (currentToken().getType() == Token.TokenType.WORD) {
			result.name = currentToken().getContent();
			index++;
		} else {
			return null;
		}

		if (currentToken().getType() == Token.TokenType.EQUALS) {
			index++;
		} else {
			return null;
		}

		Expression expression = expectExpression();
		if (expression != null) {
			result.expression = expression;
		} else {
			return null;
		}
		return result;
	}
	private static Expression expectExpression() {
		index++;
		return new Expression();
	}
}

class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
	public String toString() {
		String result = "";
		result += Node.printIndentString() + "Block:\n";
		Node.printIndent++;
		result += Node.printIndentString() + "statements: [\n";
		Node.printIndent++;
		for (Integer c = 0; statements.size() > c; c++) {
			result += statements.get(c).toString();
		}
		Node.printIndent--;
		result += Node.printIndentString() + "]\n";
		Node.printIndent--;
		return result;
		
	}
}

abstract class Statement extends Node { }

class Set extends Statement {
	public String name;
	public Expression expression;
	public String toString() {
		String result = "";
		result += Node.printIndentString() + "Statement " + name + ": \n";
		Node.printIndent++;
		result += Node.printIndentString() + "expression: \n";
		Node.printIndent++;
		result += expression.toString();
		Node.printIndent--;
		Node.printIndent--;
		return result;
	}
}

class Expression extends Node {
	public String toString() {
		return Node.printIndentString() + "Expression\n";
	}
}