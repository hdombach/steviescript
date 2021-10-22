package steviecompiler.node;

import steviecompiler.Token.TokenType;

abstract class Expression extends Node {
	public Node content;

	public static Expression expect() {
        return null;
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