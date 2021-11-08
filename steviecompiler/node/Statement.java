package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token;
import steviecompiler.error.ErrorHandler;
import steviecompiler.symbol.SymbolTable;

public abstract class Statement extends Node {
	protected static SymbolTable symbols;
	protected static ArrayList<Statement> statements;

	public static Statement expect(SymbolTable symbols, ArrayList<Statement> statements) {
		Statement.statements = statements;
		Statement temp;
		Statement.symbols = symbols;

		temp = new CreateVar();
		if (temp.isValid) {
			symbols.symbolize((CreateVar) (temp));
			Node.index--;
			Set tempSet = new Set();
			if (tempSet.isValid) {
				statements.add(temp);
				return tempSet;
			}
			Node.index++;
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}

		temp = new DefFunction();
		if (temp.isValid) {
			//TODO: do things with scope.
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}

		temp = new Set();
		if (temp.isValid) {
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}
		
		temp = new PointerSet();
		if (temp.isValid) {
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}

		temp = new While();
		if (temp.isValid) {
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}

		temp = new If();
		if (temp.isValid || temp.unexpectedToken) {
			return temp;
		}

		temp = new For();
		if (temp.isValid) {
			return temp;
		} else if (temp.unexpectedToken) {
			return null;
		}

		temp = new Return();
		if(temp.isValid) {
			return temp;
		}
		
		temp = new End();
		if (temp.isValid) {
			return temp;
		}

		if (!temp.unexpectedToken && Node.currentToken().getType() != Token.TokenType.CLOSECURLY) {
			return new InvalidStatement();
		}
		return null;
	}

	protected void unexpectedToken(int beginIndex) {
		ErrorHandler.generate(001);
		this.unexpectedToken = true;
	}
}