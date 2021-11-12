package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

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
            Expression.invalid();
        }

        if (Node.currentToken().getType() == TokenType.EQUALS) {
            Node.index += 1;
        } else {
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            expectedToken = TokenType.EQUALS;
        }

        expression = Expression.expect();

        if (expression.isValid) {
            isValid = true;
        } else {
            expression.invalid();
        }
    }

    public void checkSymbols(SymbolTable scope) {
        //TODO: check to see if pointerValue is a pointer data type
        pointerValue.checkSymbols(scope);
        expression.checkSymbols(scope);
    }

    public int getReqMemory() {
        int biggest;
        int sum;
        int temp;

        sum = pointerValue.evaluatedType.getReqMemory();
        biggest = pointerValue.getReqMemory();

        sum += expression.evaluatedType.getReqMemory();
        temp = expression.getReqMemory();
        if (temp > biggest) {
            biggest = temp;
        }

        return biggest+ sum;
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
