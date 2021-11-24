package steviecompiler.node;

import java.util.ArrayList;
import java.util.HashMap;

import steviecompiler.commands.Command;
import steviecompiler.commands.PushCommand;
import steviecompiler.symbol.Symbol;
import steviecompiler.symbol.SymbolTable;
 

public class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
	protected SymbolTable symbols;
	private static Block currentParent;
	private Block parent;
	private ArrayList<Command> commands = new ArrayList<Command>();
	public DataType returnType;

	public Block(HashMap<String, ArrayList<Symbol>> shared){
		parent = currentParent;
		currentParent = this;
		if (parent == null) {
			symbols = new SymbolTable(null, this, shared);
		} else {
			symbols = new SymbolTable(parent.symbols, this, shared);
		}
		while (true){
			if (Node.tokens.size() <= Node.index)
				break;

			Statement statement = Statement.expect(symbols, statements);
			if (statement != null && statement.isValid){
				statements.add(statement);
			}
			else if (statement != null && statement.unexpectedToken) {

			}
			else {
				currentParent = parent;
				return;
			}
		}
		currentParent = parent;
    }

	public void checkSymbols(SymbolTable scope) {
		for (Statement statement : statements) {
			statement.checkSymbols(this.symbols);
		}
	}

	public int getReqMemory() {
		/*int biggest = 0;
		int temp;
		for (Statement statement : statements) {
			temp = statement.getReqMemory();
			if (temp > biggest) {
				biggest = temp;
			}
		}
		int sum = 0;
		for (Symbol s : symbols.getLocalVariables()) {
			sum += s.getMemSize();
		}
		if (returnType != null) {
			sum += returnType.getReqMemory();
			sum += new DataType("pointer").getReqMemory();
		}
		symbols.requiredTempMemory = biggest;
		symbols.stackSize = biggest + sum;*/
		return 0;
	}

	public ArrayList<Command> makeCommands(Block block) {
		ArrayList<Command> result = new ArrayList<Command>();
		
		result.add(new PushCommand(symbols.getStackSize()));

		for (Statement statement : statements) {
			result.addAll(statement.makeCommands(this));
		}
		return result;
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Block:\n";
		Node.indent++;
		result += Node.indentStr() + "statements: [\n";
		Node.indent++;

		for (Integer c = 0; statements.size() > c; c++)
			result += statements.get(c);
		
		Node.indent--;
		result += Node.indentStr() + "]\n";
		Node.indent--;
		return result;
	}
	//used for allowing other blocks to reference the start of this block.
	public Command getFirstCommand() {
		return commands.get(0);
	}
}