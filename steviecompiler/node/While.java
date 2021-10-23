package steviecompiler.node;

import steviecompiler.Token.TokenType;

public class While extends Statement {
	public Expression condition;
	public Block loop;

	public While() {
		int beginIndex = Node.index;
		isValid = true;

		if (currentToken().getType() == TokenType.WHILE) {
			Node.index++;
		} else {
			isValid = false;
		}

		if (currentToken().getType() == TokenType.OPENPARAN) {
			Node.index++;
		} else {
			isValid = false;
		}

		condition = Expression.expect();
		if(!condition.isValid) {
			isValid = false;
		} else {
			Node.index++;
		}

		if (currentToken().getType() == TokenType.CLOSEPARAN) {
			Node.index++;
		} else {
			isValid = false;
		}

		if (currentToken().getType() == TokenType.OPENBRACK) {
			Node.index++;
		} else {
			isValid = false;
		}

		loop = new Block();

		if(currentToken().getType() == TokenType.CLOSEBRACK) {
			Node.index++;
		} else {
			isValid = false;
		}

		if(!isValid) {
			Node.index = beginIndex;
		}
	}
	
	public String toString() {
		String result = "";
		//result +=
		return null;
	}
}