package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.symbol.SymbolTable;


public class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();
	protected SymbolTable symbols;

	public Block(){
		symbols = new SymbolTable();
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
				return;
			}
		}
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