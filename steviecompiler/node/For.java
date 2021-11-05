package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;

public class For extends Statement {
    private static TokenType[] tokenSequence = {TokenType.FOR, TokenType.OPENPARAN, TokenType.COMMA, TokenType.CLOSEPARAN, TokenType.OPENCURLY, TokenType.CLOSECURLY};
    private CreateVar index;
    private Set setIndex;
    private Expression condition;
    private Set increment;
    private Block loop;
    
    public For() {
        int beginIndex = Node.index;
        isValid = true;
        if(Node.currentToken().getType() != TokenType.FOR) {
            isValid = false;
            Node.index = beginIndex;
            return;
        }

        Node.index++;

        if(Node.currentToken().getType() != TokenType.OPENPARAN) {
            Node.expectedToken = TokenType.OPENPARAN;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        index = new CreateVar(); //Still not sure how/if we want to store the index declaration in the Statement ArrayList
        if(!index.isValid) {
            setIndex = new Set();
            if(!setIndex.isValid) {
                unexpectedToken = true; //Assuming that the CreateVar being invalid will throw an error on its own and this statement doesn't need to
                Node.index = beginIndex;
                return;
            }
        }
        else {
            symbols.symbolize(index);
		    Node.index--;
            Set tempSet = new Set();
		    if(tempSet.isValid) {
                setIndex = tempSet;
		    }
        }

        if(Node.currentToken().getType() != TokenType.COMMA) {
            Node.expectedToken = TokenType.COMMA;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        condition = Expression.expect();
        if(!condition.isValid) {
			Node.index = beginIndex;
            throw new Error("Expected expression not " + Node.currentToken()); //Replace with invalid expression error
        }


        if(Node.currentToken().getType() != TokenType.COMMA) {
            Node.expectedToken = TokenType.COMMA;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        increment = new Set();
        if(!increment.isValid) {
			Node.index = beginIndex;
            return;
        }

        if(Node.currentToken().getType() != TokenType.CLOSEPARAN) {
            Node.expectedToken = TokenType.CLOSEPARAN;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        if(Node.currentToken().getType() != TokenType.OPENCURLY) {
            Node.expectedToken = TokenType.OPENCURLY;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        loop = new Block();
                
        if(Node.currentToken().getType() != TokenType.CLOSECURLY) {
            Node.expectedToken = TokenType.CLOSECURLY;
            unexpectedToken(beginIndex);
        }

        Node.index++;
    }
}