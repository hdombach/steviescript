package steviecompiler.node;

public class Statement extends Node {

	public static Statement expect() {
		Statement temp;

		temp = new Set();
		if (temp.isValid) {
			return temp;
		}
		temp = new While();
		if (temp.isValid) {
			return temp;
		}

		return temp;
	}
}