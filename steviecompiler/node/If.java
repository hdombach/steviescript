package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

import java.util.ArrayList;

public class If extends Statement{
	private ArrayList<Expression> conditions = new ArrayList<Expression>();
	private ArrayList<Block> codes = new ArrayList<Block>();
	private boolean hasElse = false;
	private Block elseCode;

	public If(){
		int beginIndex = Node.index;

		if (Node.currentToken().getType() != TokenType.IF)
			return;

		while (Node.currentToken().getType() == TokenType.IF){
			hasElse = false;
			Node.index++;

			if (Node.currentToken().getType() != TokenType.OPENPARAN){
				Node.expectedToken = TokenType.OPENPARAN;
				unexpectedToken(Node.index);
			}
			Node.index++;

			Expression condition = Expression.expect();
			if (!condition.isValid){
				Expression.invalid();
				Node.index = beginIndex;
			}

			if (Node.currentToken().getType() != TokenType.CLOSEPARAN){
				Node.expectedToken = TokenType.CLOSEPARAN;
				unexpectedToken(Node.index);
			}
			Node.index++;

			if (Node.currentToken().getType() != TokenType.OPENCURLY){
				Node.expectedToken = TokenType.OPENCURLY;
				unexpectedToken(Node.index);
			}
			Node.index++;

			conditions.add(condition);
			codes.add(new Block());

			if (Node.currentToken().getType() != TokenType.CLOSECURLY){
				Node.expectedToken = TokenType.CLOSECURLY;
				unexpectedToken(Node.index);
			}
			Node.index++;

			if (Node.currentToken().getType() == TokenType.ELSE){
				hasElse = true;
				Node.index++;
			}
		}

		if (hasElse){
			if (Node.currentToken().getType() != TokenType.OPENCURLY){
				Node.expectedToken = TokenType.OPENCURLY;
				unexpectedToken(Node.index);
			}
			Node.index++;
	
			elseCode = new Block();
	
			if (Node.currentToken().getType() != TokenType.CLOSECURLY){
				Node.expectedToken = TokenType.CLOSECURLY;
				unexpectedToken(Node.index);
			}
			Node.index++;
		}
	
		isValid = true;
	}

	public void checkSymbols(SymbolTable scope){
		
	}

	public String toString(){
		String result = "";
		result += Node.indentStr() + "If: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += conditions.get(0);
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += codes.get(0);
		Node.indent -= 2;

		for (int i = 1; i < codes.size(); i++){
			result += Node.indentStr() + "Else If: \n";
			Node.indent++;
			result += Node.indentStr() + "Condition: \n";
			Node.indent++;
			result += conditions.get(i);
			Node.indent--;
			result += Node.indentStr() + "Content: \n";
			Node.indent++;
			result += codes.get(i);
			Node.indent -= 2;
		}

		if (hasElse){
			result += Node.indentStr() + "Else: \n";
			Node.indent++;
			result += Node.indentStr() + "Content: \n";
			Node.indent++;
			result += elseCode;
			Node.indent -= 2;
		}
		
		return result;
	}
}