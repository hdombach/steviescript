package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.node.Operation;

public abstract class Expression extends Node {
	public Node content;
	protected String expressionText = "";

	public static Expression expect() {
		return _expect(true);
	}
	public static Expression expectNonOperation() {
		return _expect(false);
	}

	private static Expression _expect(Boolean expectOperation) {
		int beginIndex = Node.index;
		Expression e;

		if (expectOperation) {
			e = new Operation();
			if (e.isValid) {
				return e;
			}
		}
		Node.index = beginIndex;

		e = new FloatExpression();
		if (e.isValid) { return e; }
		Node.index = beginIndex;
		e = new IntegerExpression();

		if (e.isValid) { return e; }
		Node.index = beginIndex;

		return e;
		
    }

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Expression\n";
		Node.indent++;
		result += Node.indentStr() + "content: \n";
		Node.indent++;
		if (content != null) {
			result += content.toString();
		}
		Node.indent -= 2;
		return result;
	}
}