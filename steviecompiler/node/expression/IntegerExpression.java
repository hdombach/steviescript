package steviecompiler.node.expression;

import steviecompiler.node.Block;
import steviecompiler.node.DataType;
import steviecompiler.node.Node;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.Command;
import steviecompiler.commands.LoadCommand;
import steviecompiler.commands.PopCommand;
import steviecompiler.commands.PushCommand;

public class IntegerExpression extends NumericExpression {
    private static TokenType[] tokenSequence = {TokenType.NUMBER};
    private int value;

    public IntegerExpression() {
        evaluatedType = new DataType("int");
        int beginIndex = Node.index;
        isValid = true;

        for(int i = 0; i < 1; i++, Node.index++) {
            if(Node.currentToken().getType() == tokenSequence[i]) {
                expressionText += Node.currentToken().getContent();
            }
            else {
                Node.index = beginIndex;
                isValid = false;
                return;
            }
        }
        try {
            value = Integer.parseInt(expressionText);
        }
        catch(Exception e) {
            Node.index = beginIndex;
            isValid = false;
        }
    }

    public ArrayList<Command> makeCommands(Block block) {
        ArrayList<Command> c = new ArrayList<Command>();

        c.add(new PushCommand(4));

        c.add(new LoadCommand(-4, value));

        return c;
    }

    public int value() {
        return value;
    }
    public String toString() {
        return Node.indentStr() + "Int: " + value + "\n";
    }
}
