package steviecompiler.node.expression;

import steviecompiler.Token.TokenType;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;

public abstract class Expression extends Node {
	public Node content;
	protected String expressionText = "";

	public static Expression expect() {
		int beginIndex = Node.index;
		Expression e = new FloatExpression();

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