package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

public class Return extends Statement {
    public Expression expression;

    public Return() {
        int beginIndex = Node.index;
        isValid = true;
        
        if(Node.currentToken().getType() != TokenType.RETURN) {
            isValid = false;
            return;
        }
        
        Node.index++;

        expression = Expression.expect();
        if(!expression.isValid) {
            isValid = false;
            Expression.invalid();
        }
    }

    public void checkSymbols(SymbolTable scope) {
        expression.checkSymbols(scope);
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
