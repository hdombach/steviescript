package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.commands.MorphCommand;
import steviecompiler.commands.PopCommand;
import steviecompiler.commands.PushCommand;
import steviecompiler.commands.SetCommand;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.LocalAddress;
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

		int length = expression.evaluatedType.getReqMemory();

		LocalAddress address = block.symbols.getValueAddress(name);

		ArrayList<Command> c = new ArrayList<Command>();
		
		c.addAll(expression.makeCommands(block)); //return value is added onto stack

		c.addAll(address.getCommands()); //address for variable is added onto stack
		
		Command set = new SetCommand(0, -length, length); //once this gets called, variable address will have been removed

		c.add(new MorphCommand(set, 1, -4)); //changes the SetCommand

		c.add(new PopCommand(4)); //pops the value address

		c.add(set);
		
		c.add(new PopCommand(length)); //pop the return value
							 

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
