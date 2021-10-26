package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.Node;
import steviecompiler.node.expression.Expression;
import java.util.ArrayList;

//TODO: this is not actaully tested because it requires other things

public class Param extends Node {
	public ArrayList<Expression> expressions = new ArrayList<Expression>()

	public Param() {
		int beginIndex = Node.index;

		if (Node.currentToken().getType() == "(") {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		Expression e = Expression.expect();
		if (e.isValid) {
			expressions.add(e);
			index++;
			while (Node.currentToken().getType() == TokenType.COMMA) {
				index++;
				e = Expression.expect();
				if (e.isValid) {
					expressions.add(e);
					index++;
				} else {
					ErrorHandler.generate(001);
				}
			}
		}
		if (Node.currentToken().getType() == ")") {
			Node.index++;
		} else {
			ErrorHandler.generate(001);
		}

		isValid = true;
	}
}
