package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.commands.Command;
import steviecompiler.symbol.SymbolTable;
 

public class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
	protected SymbolTable symbols;
	private static Block currentParent;
	private Block parent;
	private ArrayList<Command> commands = new ArrayList<Command>();

	public Block(){
		parent = currentParent;
		currentParent = this;
		if (parent == null) {
			symbols = new SymbolTable(null);
		} else {
			symbols = new SymbolTable(parent.symbols);
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
		int biggest = 0;
		int temp;
		for (Statement statement : statements) {
			temp = statement.getReqMemory();
			if (temp > biggest) {
				biggest = temp;
			}
		}
		symbols.requiredTempMemory = biggest;
		return 0;
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Block:\n";
		Node.indent++;
		result += Node.indentStr() + "temp meory: " + symbols.requiredTempMemory + "\n";
		result += Node.indentStr() + "statements: [\n";
		Node.indent++;

		for (Integer c = 0; statements.size() > c; c++)
			result += statements.get(c);
		
		Node.indent--;
		result += Node.indentStr() + "]\n";
		Node.indent--;
		return result;
	}

	public void addCommand(Command command) {
		commands.add(command);
	}
	//used for allowing other blocks to reference the start of this block.
	public Command getFirstCommand() {
		return commands.get(0);
	}
}