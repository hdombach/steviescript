package steviecompiler.node.expression;

import steviecompiler.node.Node;

public abstract class Expression extends Node {
	public Node content;
	protected String expressionText = "";
	protected static int beginIndex;
	private static Boolean includeMethods = true;

	public static Expression expect() {
		return _expect(true);
	}
	public static Expression expectNonOperation() {
		return _expect(false);
	}

	private static Expression loadMethods(Expression e) {
		Expression current = e;
		while (true) {
			CallMethod m = new CallMethod(current);
			if (m.isValid) {
				current = m;
			} else {
				break;
			}
		}
		return current;
	}

	private static Expression _expect(Boolean expectOperation) {
		int beginIndex = Node.index;
		Expression e;

		if (expectOperation) {
			e = new Operation();
			if (e.isValid) {
				return loadMethods(e);
			}
		}
		Node.index = beginIndex;

		e = new FloatExpression();
		if (e.isValid) { return loadMethods(e); }
		Node.index = beginIndex;
		e = new IntegerExpression();

		if (e.isValid) {return loadMethods(e); }
		Node.index = beginIndex;

		e = new BooleanExpression();

		if (e.isValid) { return loadMethods(e); }
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