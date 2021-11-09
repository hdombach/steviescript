package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

import java.util.ArrayList;

public class If extends Statement{
	/*
		if(expression){
			code
		}

		if(expression){

		} else (

		)

		if (expression){

		} else if {

		}

		if (expression){

		} else if {

		} else {

		}
	*/
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
			Node.index++;

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
			Node.index++;

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
			Node.index++;
	
			if (Node.currentToken().getType() != TokenType.CLOSECURLY){
				Node.expectedToken = TokenType.CLOSECURLY;
				unexpectedToken(Node.index);
			}
			Node.index++;
		}
	
		isValid = true;
	}


	/*
		String result = "";
		result += Node.indentStr() + "If: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += block;
		Node.indent-= 2;
		return result;
	*/
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

/*
package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

public class If extends Statement {
	private static TokenType[] tokenSequence = {TokenType.IF, TokenType.OPENPARAN, TokenType.CLOSEPARAN, TokenType.OPENCURLY, TokenType.CLOSECURLY};
	public Expression condition;
	public Block block;

	public If() {
		int beginIndex = Node.index;
		int i;
		if (Node.currentToken().getType() == tokenSequence[0]) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		for(i = 1; i < 2; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				expectedToken = tokenSequence[i];
				unexpectedToken(beginIndex);
			}
		}

		condition = Expression.expect();
		if(!condition.isValid) {
			Expression.invalid();
		}

		for(i = 2; i < 4; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				expectedToken = tokenSequence[i];
				unexpectedToken(beginIndex);

			}
		}

		//if(!unexpectedToken) {
			block = new Block(); //TODO: fix error where unexpected token causes infinite loop
		//}

		i = 4;
		if (Node.currentToken().getType() != tokenSequence[i]) {
			expectedToken = tokenSequence[i];
			unexpectedToken(beginIndex);
		}
		Node.index++;
		isValid = true;
	}

	public void checkSymbols(SymbolTable scope) {
		condition.checkSymbols(scope);
		block.checkSymbols(scope);
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "If: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += block;
		Node.indent-= 2;
		return result;
	}
}
*/