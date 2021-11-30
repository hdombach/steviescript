package steviecompiler.node;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.*;
import steviecompiler.error.ErrorHandler;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.SymbolTable;

import java.util.ArrayList;

public class While extends Statement {
	private static TokenType[] tokenSequence = {TokenType.WHILE, TokenType.OPENPARAN, TokenType.CLOSEPARAN, TokenType.OPENCURLY, TokenType.CLOSECURLY};
	public Expression condition;
	public Block loop;

	public While() {
		int beginIndex = Node.index;
		int i;
		
		if (Node.currentToken().getType() == tokenSequence[0]) {
			Node.index++;
		} else {
			Node.index = beginIndex;
			return;
		}

		for(i = 1; i < 2; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				Node.index = beginIndex;
				expectedToken = tokenSequence[i];
				ErrorHandler.generate(001);
			}
		}

		condition = Expression.expect();
		if(!condition.isValid) {
			Expression.invalid();
		}

		for(i = 2; i < 4; i++, Node.index++) {
			if (Node.currentToken().getType() != tokenSequence[i]) {
				Node.index = beginIndex;
				expectedToken = tokenSequence[i];
				ErrorHandler.generate(001);
			}
		}

		loop = new Block(null);

		i = 4;
		if (Node.currentToken().getType() != tokenSequence[i]) {
			Node.index = beginIndex;
			expectedToken = tokenSequence[i];
			ErrorHandler.generate(001);
		}
		Node.index++;
		isValid = true;
	}

	public void checkSymbols(SymbolTable scope) {
		//TODO: test to see if condition evaluates to a bool
		condition.checkSymbols(scope);
		loop.checkSymbols(scope);
	}
	public int getReqMemory() {
		loop.getReqMemory();
		return condition.evaluatedType.getReqMemory() + condition.getReqMemory();
	}

	@Override
	public ArrayList<Command> makeCommands(Block block) {
		ArrayList<Command> c = new ArrayList<Command>();

		c.addAll(condition.makeCommands(block)); //conditional

		IfCommand dummy = new IfCommand(0, c.get(0)); //dummy, will eventually be "if conditional false jump to end"
		c.add(dummy);

		c.addAll(loop.makeCommands(loop)); //loop

		c.add(new GoCommand(c.get(0))); //return to conditional

		dummy.reset(0 /*TODO conditional*/, new GoCommand(c.get(c.size() - 1).getLength(), c.get(c.size() - 1)));

		return c;
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "While: \n";
		Node.indent++;
		result += Node.indentStr() + "Condition: \n";
		Node.indent++;
		result += condition;
		Node.indent--;
		result += Node.indentStr() + "Content: \n";
		Node.indent++;
		result += loop;
		Node.indent-= 2;
		return result;
	}
}