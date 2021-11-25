package steviecompiler.node.expression;

import java.util.ArrayList;

import steviecompiler.Token;
import steviecompiler.commands.Command;
import steviecompiler.commands.MorphCommand;
import steviecompiler.commands.PopCommand;
import steviecompiler.commands.PushCommand;
import steviecompiler.commands.SetCommand;
import steviecompiler.node.Block;
import steviecompiler.node.Node;
import steviecompiler.symbol.LocalAddress;
import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;

public class VariableName extends Expression {
	public String name;

	public VariableName() {
		Token token = Node.currentToken();
		if (token.getType() == Token.TokenType.WORD){
			name = token.getContent();
			isValid = true;
			Node.index++;
		}
	}

	public String toString() {
		return Node.indentStr() + "Variable: " + name + "\n";
	}

	public void checkSymbols(SymbolTable scope) {
		Symbol s = scope.getValue(name);
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist in scope");
		}
		evaluatedType = s.dataType;
	}

	public String content() {
		return name;
	}
	public int getReqMemory() {
		return 0;
	}
	public ArrayList<Command> makeCommands(Block block) {
		ArrayList<Command> c = new ArrayList<Command>();

		Symbol symbol = block.symbols.getValue(name);
		int length = symbol.getMemSize();
		LocalAddress address = block.symbols.getValueAddress(name);

		c.add(new PushCommand(length));

		c.addAll(address.getCommands()); 

		Command set = new SetCommand(-length, 0, length);

		c.add(new MorphCommand(set, 5, -4));
		c.add(new PopCommand(4));
		c.add(set);

		return c;
	}
}
