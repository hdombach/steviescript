package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.symbol.SymbolTable;

public abstract class Statement extends Node {

	public static Statement expect(SymbolTable symbols, ArrayList<Statement> statements) {
		Statement temp;

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

		temp = new Set();
		if (temp.isValid) {
			return temp;
		}
		temp = new While();
		if (temp.isValid) {
			return temp;
		}

		temp = new If();
		if (temp.isValid) {
			return temp;
		}

		temp = new End();
		if(temp.isValid) {
			return temp;
		}

		return new InvalidStatement();
	}

}