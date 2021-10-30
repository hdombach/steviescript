package steviecompiler.node;

import steviecompiler.symbol.SymbolTable;

public abstract class Statement extends Node {

	public static Statement expect(SymbolTable symbols) {
		Statement temp;

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

		temp = new CreateVar();
		if (temp.isValid) {
			symbols.symbolize((CreateVar) (temp));
			return temp;
		}

		return new InvalidStatement();
	}

}