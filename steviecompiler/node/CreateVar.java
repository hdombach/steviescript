package steviecompiler.node;

import steviecompiler.node.expression.*;

public class CreateVar extends Statement {
	public String name;
	public DataType type;
	public Expression expression;
	
	public CreateVar() {

		DataType dataType = new DataType();
		if (!dataType.isValid) {
			isValid = false;
		} else {
			type = dataType;
		}
		VariableName name = new VariableName();
		
	}
}