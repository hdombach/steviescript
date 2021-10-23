package steviecompiler.node;

import steviecompiler.node.expression.Expression;

import java.util.ArrayList;

import steviecompiler.Token;
import steviecompiler.Token.TokenType;

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

    //THIS DOES NOT WORK YET
    public Operation() {
        int beggining = Node.index;
        isValid = true;

        while (Node.tokens.size() > Node.index) {
            System.out.println(Node.currentToken());
            Expression expression = Expression.expectNonOperation();
            if (expression.isValid) {
                outStack.add(expression);
            } else if (Node.currentToken().getType() == Token.TokenType.MATH) {
                int precedenct = getPrecedence(Node.currentToken().getContent());

                while (precedenct >= lastPrecedence()) {
                    popOperator();
                }
                operatorStack.add(Node.currentToken().getContent());

                Node.index++;
            } else {
                break;
            }
        }
        System.out.println("Stage 1 complete");
        while (operatorStack.size() > 0) {
            popOperator();
        }
        System.out.println("Stage 2 complete");
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

    
    //TODO: add more operations to list
    private int getPrecedence(String operation) {
        switch (operation) {
            case "*":
            case "/":
                return 4;
            case "+":
            case "-":
                return 3;
            default:
                return 0;
        }
    }
}
