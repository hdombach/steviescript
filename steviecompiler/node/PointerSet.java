package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.expression.Expression;

public class PointerSet extends Statement{
    public Expression pointerValue;
    public Expression expression;

    public PointerSet() {
        int beginIndex = Node.index;

        if (Node.currentToken().getType() == TokenType.POINTER) {
                Node.index += 1;
        } else {
            return;
        }

        pointerValue = Expression.expect();
        if (!pointerValue.isValid) {
            throw new Error("Expected expression not" + Node.currentToken());
        }

        if (Node.currentToken().getType() == TokenType.EQUALS) {
            Node.index += 1;
        } else {
            Node.index = beginIndex;
            expectedToken = TokenType.EQUALS;
            ErrorHandler.generate(001);
        }

        expression = Expression.expect();

        if (expression.isValid) {
            isValid = true;
        } else {
            throw new Error("Expected expression not" + Node.currentToken());
        }
    }

    public String toString() {
        String result = "";
        result += Node.indentStr() + "Pointer Set\n";
        Node.indent += 1;
        result += Node.indentStr() + "Pointer: \n";
        Node.indent += 1;
        result += pointerValue;
        Node.indent -= 1;
        result += Node.indentStr() + "Value: \n";
        Node.indent += 1;
        result += expression;
        Node.indent -= 2;
        return result;
    }
}
