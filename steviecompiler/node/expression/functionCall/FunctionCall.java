package steviecompiler.node.expression.functionCall;

import java.util.ArrayList;

import steviecompiler.Token.TokenType;
import steviecompiler.commands.AddCommand;
import steviecompiler.commands.Command;
import steviecompiler.commands.GoCommand;
import steviecompiler.commands.LoadCommand;
import steviecompiler.commands.MorphCommand;
import steviecompiler.commands.NormalizeCommand;
import steviecompiler.commands.PopCommand;
import steviecompiler.commands.PushCommand;
import steviecompiler.commands.SetCommand;
import steviecompiler.node.Block;
import steviecompiler.node.Node;
import steviecompiler.node.Param;
import steviecompiler.node.expression.Expression;
import steviecompiler.symbol.FunctionSymbol;
import steviecompiler.symbol.SymbolTable;

public class FunctionCall extends Expression {
	public String name;
	public Param param;

	public FunctionCall() {
		int beginIndex = Node.index;

		if (Node.currentToken().getType() == TokenType.WORD) {
			name = Node.currentToken().getContent();
			Node.index++;
		} else {
			return;
		}

		param = new Param();
		if (param.isValid()) {
			isValid = true;
			return;
		} else {
			Node.index = beginIndex;
		}
	}

	public void checkSymbols(SymbolTable scope) {
		param.checkSymbols(scope);

		FunctionSymbol s = scope.getFunction(name, param.getParamTypes());
		if (s == null) {
			throw new Error("Line " + getLine() + ": symbol " + name + " does not exist");
		} else {
			evaluatedType = s.dataType;
		}

	}
	
	public int getReqMemory() {
		int big = 0;
		int result = 0;
		int temp;
		for (Expression exp : param.expressions) {
			result += exp.evaluatedType.getReqMemory();
			temp = exp.getReqMemory();
			if (temp > big) {
				big = temp;
			}
		}
		return result + big;
	}

	
	//needs to set up the stack up till the local variables.
	public ArrayList<Command> makeCommands(Block block) {
		ArrayList<Command> c = new ArrayList<Command>();

		FunctionSymbol symbol = block.symbols.getFunction(name, param.getParamTypes());
		int currentSize = 0;
		if (this.evaluatedType != null) {
			c.add(new PushCommand(evaluatedType.getReqMemory())); //return value
			currentSize += evaluatedType.getReqMemory();
		}
		LoadCommand loadReturnAddress = new LoadCommand(-4, 0);
		c.add(new PushCommand(4));
		c.add(loadReturnAddress);
		
		c.add(new PushCommand(4));
		c.add(new SetCommand(-4, 0, 4)); //setup previous frame pointer

		currentSize += 8;

		c.add(new NormalizeCommand(0, -currentSize)); //update the new frame pointer
		
		int i = 1;
		while (symbol.block.symbols.getParents() > i) { //set up the rest of the frame pointers
			c.add(new PushCommand(4));
			if (block.symbols.getParents() > i) {
				c.add(new PushCommand(4)); //temp value to calculate correct address for frame pointer
				c.add(new LoadCommand(-4, i * 4));
				c.add(new AddCommand(-4, 0, -4));
				Command set = new SetCommand(-4, 0, 4);
				c.add(new MorphCommand(set, 5, -4));
				c.add(new PopCommand(4));
				c.add(set);
			} else {
				c.add(new SetCommand(-4, 0, 4));
			}
			i++;
		}

		for (Expression expression : param.expressions) { //add info for params
			c.addAll(expression.makeCommands(block));
		}

		Command goCommand = new GoCommand(symbol.block); //goto
		c.add(goCommand);

		loadReturnAddress.addGotoCommand(goCommand); //set the return address.

		return c;
		
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Function Call: " + name + "\n";
		Node.indent++;
		result += Node.indentStr() + "Params: \n";
		result += param;
		Node.indent--;
		return result;
	}
}
