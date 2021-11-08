package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;

public class Return extends Statement {
    public Expression expression;

    public Return() {
        int beginIndex = Node.index;
        if(Node.currentToken().getType() != TokenType.RETURN) {
            return;
        }
        
        Node.index++;

        expression = Expression.expect();
        if(!expression.isValid) {
            Node.index = beginIndex;
            throw new Error("Expected expression not " + Node.currentToken()); //Replace with invalid expression error
        }

        isValid = true;
    }

    public String toString() {
		String result = "";
		result += Node.indentStr() + "Statement return: \n";
		Node.indent++;
		result += Node.indentStr() + "expression: \n";
		Node.indent++;
		result += expression.toString();
		Node.indent--;
		Node.indent--;
		return result;
	}
}
