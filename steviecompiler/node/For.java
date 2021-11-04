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
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.OPENPARAN;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
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
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.COMMA;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
        }

        Node.index++;

        condition = Expression.expect();
        if(!condition.isValid) {
			Node.index = beginIndex;
            throw new Error("Expected expression not " + Node.currentToken()); //Replace with invalid expression error
        }

        //Node.index += 2;

        if(Node.currentToken().getType() != TokenType.COMMA) {
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.COMMA;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
        }

        Node.index++;

        increment = new Set();
        if(!increment.isValid) {
			Node.index = beginIndex;
            return;
        }

        if(Node.currentToken().getType() != TokenType.CLOSEPARAN) {
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.CLOSEPARAN;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
        }

        Node.index++;

        if(Node.currentToken().getType() != TokenType.OPENCURLY) {
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.OPENCURLY;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
        }

        loop = new Block();
                
        if(Node.currentToken().getType() != TokenType.CLOSECURLY) {
            isValid = false;
            unexpectedToken = true;
            Node.expectedToken = TokenType.CLOSECURLY;
            ErrorHandler.generate(001);
            Node.index = beginIndex;
            return;
        }

        Node.index++;
    }
}