package steviecompiler.node;

import steviecompiler.node.expression.*;

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

	//Needs toString()
}