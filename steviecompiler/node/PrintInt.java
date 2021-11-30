package steviecompiler.node;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.commands.OutCommand;
import steviecompiler.commands.PopCommand;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

public class PrintInt extends Statement{
    public Expression exp;

    public PrintInt() {
        int beginIndex = Node.index;

        if (Node.currentToken().getType() != TokenType.WORD) {
            return;
        }

        if (!Node.currentToken().getContent().equals("printInt")) {
            return;
        }
        Node.index++;

        if (Node.currentToken().getType() != TokenType.OPENPARAN) {
            Node.expectedToken = TokenType.OPENPARAN;
            unexpectedToken(Node.index);
        }

        Node.index++;

        exp = Expression.expect();

        if (!exp.isValid) {
            Expression.invalid();
            Node.index = beginIndex;
        }

        if (Node.currentToken().getType() != TokenType.CLOSEPARAN) {
            Node.expectedToken = TokenType.CLOSEPARAN;
            unexpectedToken(Node.index);
        }

        Node.index++;

        isValid = true;
    }

    public void checkSymbols(SymbolTable scope) {
        exp.checkSymbols(scope);

        if (!exp.evaluatedType.compare(new DataType("int"))) {
            throw new Error("Line " + getLine() + exp.evaluatedType.getType() + "is not an int.");
        }
    }

    public int getReqMemory() {
        return exp.getReqMemory();
    }

    public String toString() {
		String result = "";
		result += Node.indentStr() + "Print Int: \n";
		Node.indent++;
		result += Node.indentStr() + "Expression: \n";
		Node.indent++;
		result += exp;
		Node.indent-= 2;
		return result;
	}

    public ArrayList<Command> makeCommands(Block block) {
        
		ArrayList<Command> c = new ArrayList<Command>();

        c.addAll(exp.makeCommands(block));

        c.add(new OutCommand(-4, 4));

        c.add(new PopCommand(4));

        return c;
    }
}
