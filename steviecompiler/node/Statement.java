package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.symbol.SymbolTable;

public abstract class Statement extends Node {
	protected static SymbolTable symbols;

	public static Statement expect(SymbolTable symbols, ArrayList<Statement> statements) {
		Statement temp;
		Statement.symbols = symbols;

		temp = new CreateVar();
		if (temp.isValid) {
			symbols.symbolize((CreateVar) (temp));
			Node.index--;
			Set tempSet = new Set();
			if(tempSet.isValid) {
				statements.add(temp);
				return tempSet;
			}
			Node.index++;
			return temp;
		}
		else if(temp.unexpectedToken) {
			return null;
		}

		temp = new Set();
		if (temp.isValid) {
			return temp;
		}
		else if(temp.unexpectedToken) {
			return null;
		}	
		
		temp = new While();
		if (temp.isValid) {
			return temp;
		}
		else if(temp.unexpectedToken) {
			return null;
		}

		temp = new If();
		if (temp.isValid) {
			return temp;
		}
		else if(temp.unexpectedToken) {
			return null;
		}

		temp = new For();
		if(temp.isValid) {
			return temp;
		}
		else if(temp.unexpectedToken) {
			return null;
		}

		temp = new End();
		if(temp.isValid) {
			return temp;
		}

		if(!temp.unexpectedToken) {
			return new InvalidStatement();
		}
		return null;
	}

}