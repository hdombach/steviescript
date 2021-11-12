package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.symbol.SymbolTable;
import steviecompiler.Token.TokenType;

public class For extends Statement {
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
            statements.add(index);
            setIndex = new Set();
            if(!setIndex.isValid) {
                unexpectedToken = true; //Assuming that the CreateVar being invalid will throw an error on its own and this statement doesn't need to
                Node.index = beginIndex;
                return;
            }
            statements.add(setIndex);
        }
        else {
            symbols.symbolize(index);
		    Node.index--;
            Set tempSet = new Set();
		    if(tempSet.isValid) {
                setIndex = tempSet;
		    }
            else {
                unexpectedToken = true;
                return;
            }
        }

        if(Node.currentToken().getType() != TokenType.COMMA) {
            Node.expectedToken = TokenType.COMMA;
            unexpectedToken(beginIndex);
        }

        Node.index++;

        condition = Expression.expect();
        if(!condition.isValid) {
            Expression.invalid();
			Node.index = beginIndex;
            //throw new Error("Expected expression not " + Node.currentToken()); //Replace with invalid expression error
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
    public void checkSymbols(SymbolTable scope) {
        setIndex.checkSymbols(scope);
        condition.checkSymbols(scope);
        increment.checkSymbols(scope);
        loop.checkSymbols(scope);
    }

    public int getReqMemory() {
        int biggest;
        int temp;
        biggest = setIndex.getReqMemory();
        
        temp = condition.getReqMemory();
        if (temp > biggest) {
            biggest = temp;
        }

        temp = increment.getReqMemory();
        if (temp > biggest) {
            biggest = temp;
        }

        loop.getReqMemory();
        return biggest + condition.evaluatedType.getReqMemory();
    }

    public String toString() {
		String result = "";
		result += Node.indentStr() + "For: \n";
		Node.indent++;
        result += Node.indentStr() + "Index: \n";
        Node.indent++;
        if(index.isValid) {
            result += index;
        }
        result += setIndex;
        Node.indent--;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
        result += Node.indentStr() + "increment: \n";
        Node.indent++;
        result += increment;
        Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += loop;
		Node.indent-= 2;
		return result;
	}
}