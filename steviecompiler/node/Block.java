package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token;

public class Block extends Node {
	public ArrayList<Statement> statements = new ArrayList<Statement>();

	public Block(){
		while (true){
			if (Node.tokens.size() <= Node.index)
				break;

			Statement statement = Statement.expect();
			if (statement.isValid){
				statements.add(statement);
			} else {
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