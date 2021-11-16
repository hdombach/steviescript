package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.commands.SetCommand;
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

	public ArrayList<Command> makeCommands(Block block) {
		ArrayList<Command> c = new ArrayList<Command>();

		block.symbols.pushTemp(expression.evaluatedType.getReqMemory());
		c.addAll(expression.makeCommands(block));
		c.add(new SetCommand(block.symbols.getValueAddress(name),
		                     block.symbols.getCurrentTempAddress(),
							 expression.evaluatedType.getReqMemory()));
							 
		block.symbols.popTemp(expression.evaluatedType.getReqMemory());

		return c;
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
