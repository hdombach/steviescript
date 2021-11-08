package steviecompiler.node;

import steviecompiler.node.expression.*;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.error.ErrorHandler;

public class DefFunction extends Statement{
    /*
        function exampleFunction(type name, type name) -> type {
            <code>
        }

        function exampleFunction(type name, type name) {
            <code>
        }

        function exampleFunction() {

        }
    */
    private String functionName;

    private ArrayList<DefParam> params = new ArrayList<DefParam>();

    private boolean hasReturn = false;
    private DataType returnType;

    private Block code;

    
    public DefFunction(){
        int beginIndex = Node.index;

        if (Node.currentToken().getType() != TokenType.FUNCTION)
            return;
        
        isValid = true;
        Node.index++;

        if (Node.currentToken().getType() != TokenType.WORD){
            Node.expectedToken = TokenType.WORD;
            unexpectedToken(Node.index);
        } 
        
        functionName = Node.currentToken().getContent();
        Node.index++;

        if (Node.currentToken().getType() != TokenType.OPENPARAN){
            Node.expectedToken = TokenType.OPENPARAN;
            unexpectedToken(Node.index);
        }
        Node.index++;

        while (Node.currentToken().getType() != TokenType.CLOSEPARAN){
            DefParam tempParam = new DefParam();
            if (!tempParam.isValid){
                unexpectedToken = true;
                Node.index = beginIndex;
                return;
            }
            params.add(tempParam);
            Node.index++;
            
            if (Node.currentToken().getType() == TokenType.COMMA)
                Node.index++;
        }
        Node.index++;

        if (Node.currentToken().getType() == TokenType.ARROW){
            hasReturn = true;
            Node.index++;

            returnType = new DataType();
            if (!returnType.isValid)
                return;
            Node.index++;
        }
        
        if (Node.currentToken().getType() != TokenType.OPENCURLY){
            Node.expectedToken = TokenType.OPENCURLY;
            unexpectedToken(Node.index);
        }
        Node.index++;

        code = new Block();
        Node.index++;

        if (Node.currentToken().getType() != TokenType.CLOSECURLY){
            Node.expectedToken = TokenType.CLOSECURLY;
            unexpectedToken(Node.index);
        }
        Node.index++;
    }

    public String toString() {
        String result = "";
        result += Node.indentStr() + "Function Definition: \n";
        Node.indent += 1;
        result += Node.indentStr() + "Name: " + functionName + "\n";
        result += Node.indentStr() + "Paramaters: \n";
        Node.indent += 1;
        for (DefParam param : params) {
            result += param;
        }
        Node.indent -= 1;
        result += Node.indentStr() + "Code: \n";
        Node.indent += 1;
        result += code;
        Node.indent -= 2;
        return result;
    }
}
