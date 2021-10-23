package steviecompiler.node;

import steviecompiler.node.expression.Expression;

class Operation extends Node {
	public String operator;
	public Expression left;
	public Expression right;
	public String toString() {
		String result = Node.indentStr() + "Operation: " + operator + "\n";
		Node.indent++;
		result += Node.indentStr() + "Left: \n";
		Node.indent++;
		result += left.toString();
		Node.indent--;
		result += Node.indentStr() + "Right: \n";
		Node.indent++;
		result += right.toString();
		Node.indent -= 2;
		return result;
	}
}