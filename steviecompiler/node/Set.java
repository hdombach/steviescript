package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;

public class Set extends Statement {
	private static TokenType[] tokenSequence = {TokenType.WORD, TokenType.EQUALS};
	public String name;
	public Expression expression;

	public Set(){
		isValid = true;
		int beginIndex = Node.index;
		name = currentToken().getContent();
		for(int i = 0; i < 2; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				isValid = false;
				Node.index = beginIndex;
				return;
			}
		}

		expression = Expression.expect();
		if (!expression.isValid) {
			Expression.invalid();
			isValid = false;
		}
		
	}

	public void checkSymbols(SymbolTable scope) {
		expression.checkSymbols(scope);
		Symbol s = scope.getValue(name);
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist in scope.");
		}
		if (!expression.evaluatedType.compare(s.dataType)) {
			throw new Error("Line " + getLine() + ": cannot set a " + s.dataType.getType() + " to a " + expression.evaluatedType.getType() + ".");
		}
	}
	public int getReqMemory() {
		return expression.evaluatedType.getReqMemory() + expression.getReqMemory();
	}

	public void makeCommands(Block block) {
		
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Set statement " + name + ": \n";
		Node.indent++;
		result += Node.indentStr() + "expression: \n";
		Node.indent++;
		result += expression.toString();
		Node.indent--;
		Node.indent--;
		return result;
	}
}
