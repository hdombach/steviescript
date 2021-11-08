package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.symbol.SymbolTable;
 

public class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
	protected SymbolTable symbols;
	private static Block currentParent;
	private Block parent;

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
}