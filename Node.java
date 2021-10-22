
import java.util.ArrayList;


abstract public class Node {
	private static int index = 0;
	private static ArrayList<Token> tokens;
	private static Block block;
	protected static Integer indent = 0;
	private static String printIndentChar = "| ";
	protected static String indentStr() {
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
				throw new Error("Unexpected Token" + currentToken().toString());
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
		result += Node.indentStr() + "Block:\n";
		Node.indent++;
		result += Node.indentStr() + "statements: [\n";
		Node.indent++;
		for (Integer c = 0; statements.size() > c; c++) {
			result += statements.get(c).toString();
		}
		Node.indent--;
		result += Node.indentStr() + "]\n";
		Node.indent--;
		return result;
		
	}
}

abstract class Statement extends Node { }

class Set extends Statement {
	public String name;
	public Expression expression;
	public String toString() {
		String result = "";
		result += Node.indentStr() + "Statement " + name + ": \n";
		Node.indent++;
		result += Node.indentStr() + "expression: \n";
		Node.indent++;
		result += expression.toString();
		Node.indent--;
		Node.indent--;
		return result;
	}
}

class Expression extends Node {
	public Node content;
	public String toString() {
		String result = "";
		result += Node.intentStr() + "Expression\n";
		Node.indent++;
		result += Node.indentStr() + "content: \n";
		Node.indent++;
		result += content.toString();
		Node.indnet -= 2;
		return result;
	}
}

class VariableName extends Node {
	public String name;
	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}
}

class Operation extends Node {
	public String operator;
	public Expression left;
	public Expression right;
	public String toString() {
		String result = Node.indentStr() + "Operation: " + operator + "\n";
		Node.indent++;
		result += Node.indentStr() + "Left: \n";
		Node.indent++;
		result += left.toString();
		Node.indent--;
		result += Node.indentStr() + "Right: \n";
		Node.indent++;
		result += right.toString();
		Node.indent -= 2;
		return result;
	}
}