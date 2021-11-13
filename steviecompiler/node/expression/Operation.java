package steviecompiler.node.expression;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import steviecompiler.Token.TokenType;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.symbol.OperatorSymbol;
import steviecompiler.symbol.SymbolTable;

public class Operation extends Expression {
    public String operator;
    public Expression left;
    public Expression right;

    private ArrayList<String> operatorStack = new ArrayList<String>();
    private ArrayList<Expression> outStack = new ArrayList<Expression>();

    private void popOperator() {
        if (operatorStack.size() > 0) {
            String operator = operatorStack.remove(operatorStack.size() - 1);
            Expression right = outStack.remove(outStack.size() - 1);
            Expression left = outStack.remove(outStack.size() - 1);
            outStack.add(new Operation(operator, left, right));
        }
    }
    private int lastPrecedence() {
        if (operatorStack.isEmpty()) {
            return 0;
        } else {
            return getPrecedence(operatorStack.get(operatorStack.size() - 1));
        }
    }

    public Operation() {
        int beginning = Node.index;
        isValid = true;

        while (Node.tokens.size() > Node.index) {
            Expression expression = Expression.expectNonOperation();
            if (expression.isValid()) {
                outStack.add(expression);
            } else if (currentToken().getType() == TokenType.MATH || currentToken().getType() == TokenType.CONDITIONAL) {
                int precedence = getPrecedence(Node.currentToken().getContent());

                while (precedence <= lastPrecedence()) {
                    popOperator();
                }
                operatorStack.add(Node.currentToken().getContent());

                Node.index++;
            } else {
                break;
            }
        }
        while (operatorStack.size() > 0) {
            popOperator();
        }
		try {
			Operation result = (Operation) outStack.get(0);
			this.operator = result.operator;
			this.left = result.left;
			this.right = result.right;
		} catch (Exception e) {
			//means that current expression is actually a constant and not a equation
			Node.index = beginning;
			this.isValid = false;
		}
    }
    Operation(String operator, Expression left, Expression right) {
        this.operator = operator;
        this.left = left;
        this.right = right;
    }

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

    public void checkSymbols(SymbolTable scope) {
        left.checkSymbols(scope);
        right.checkSymbols(scope);
        OperatorSymbol s = scope.getOperator(operator, left.evaluatedType, right.evaluatedType);
        if (s == null) {
            throw new Error("Line " + getLine() + ": operation does not exist. Recieved: " + left.evaluatedType.getType() + " " + this.operator + " " + right.evaluatedType.getType());
        }
        evaluatedType = s.dataType;
    }
    
    //TODO: add more operations to list
    private int getPrecedence(String operation) {
        switch (operation) {
            case "*":
            case "/":
                return 4;
            case "+":
            case "-":
                return 3;
            case "!=":
            case ">=":
            case ">":
            case "<":
            case "<=":
            case "&&":
            case "||":
            case "^^":
            case "==":
                return 2;
            default:
                return 0;
        }
    }

    public int getReqMemory() {
        int result = 0;
        int templ;
        int tempr;
        result += left.evaluatedType.getReqMemory();
        result += right.evaluatedType.getReqMemory();
        templ = left.getReqMemory();
        tempr = right.getReqMemory();
        if (templ > tempr) {
            return result + templ;
        } else {
            return result + tempr;
        }
    }
}
