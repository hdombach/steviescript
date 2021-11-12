package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.symbol.SymbolTable;

import java.util.ArrayList;

import javax.xml.crypto.Data;

import steviecompiler.Token.TokenType;

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
    public String functionName;

    public ArrayList<DefParam> params = new ArrayList<DefParam>();

    private boolean hasReturn = false;
    public DataType returnType;

    private Block code;

    public ArrayList<DataType> getParamsTypes() {
        ArrayList<DataType> result = new ArrayList<DataType>();
        for (DefParam param : params) {
            result.add(param.type);
        }
        return result;
    }

    
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

        if (Node.currentToken().getType() != TokenType.CLOSECURLY){
            Node.expectedToken = TokenType.CLOSECURLY;
            unexpectedToken(Node.index);
        }
        Node.index++;
    }

    public void checkSymbols(SymbolTable scope) {
        code.checkSymbols(scope);
    }

    public int getReqMemory() {
        code.getReqMemory();
        return 0;
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
