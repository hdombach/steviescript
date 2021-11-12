package steviecompiler.node;

import steviecompiler.node.expression.*;
import steviecompiler.symbol.SymbolTable;

public class CreateVar extends Statement {
	public String name;
	public DataType type;
	public Expression expression;
	
	public CreateVar() {
		isValid = true;
		int beginIndex = Node.index;
		DataType dataType = new DataType();
		if (!dataType.isValid) {
			isValid = false;
			return;
		}
		type = dataType;
		Node.index++;

		VariableName name = new VariableName();
		if(!name.isValid()) {
			isValid = false;
			Node.index = beginIndex;
			return;
		}
		this.name = name.content();	
	}

	public String toString() {
		String result = "";
		result += Node.indentStr() + "Statement " + name + ": \n";
		Node.indent++;
		result += Node.indentStr() + "type: " + type.getType() + "\n";
		Node.indent--;
		return result;
	}

	public void checkSymbols(SymbolTable scope) {
		if (expression == null) {
			return;
		} else {
			expression.checkSymbols(scope);
		}
		if (!expression.evaluatedType.compare(type)) {
			throw new Error("Cannot set a " + type.getType() + " to a " + expression.evaluatedType.getType() + ".");
		}
	}

	public int getReqMemory() {
		if (expression == null) {
			return 0;
		}
		return expression.getReqMemory() + expression.evaluatedType.getReqMemory();
	}
}