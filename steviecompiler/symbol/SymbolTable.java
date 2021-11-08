package steviecompiler.symbol;

import java.util.HashMap;

import javax.xml.crypto.Data;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.expression.FunctionCall;

public class SymbolTable {
    public static HashMap<String, Symbol> globalTable = new HashMap<String, Symbol>();

    public HashMap<String, Symbol> table;

    //can be null
    public SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        table = new HashMap<String, Symbol>();
        this.parent = parent;
    }

    public void symbolize(CreateVar make) {
        if (table.containsKey(make.name)) {
            //Doesn't test global tree or parent trees because it's fine if symbols have same name in different scopes.
            throw new Error("Sumbol " + make.name + " already exists in current scope. ");
        }
        Symbol symbol = new Symbol(make);
        table.put(make.name, symbol);
        globalTable.put(make.name, symbol);
    }

    public void symbolize(DefFunction make) {
        if (table.containsKey(make.functionName)) {
            throw new Error("Symbol " + make.functionName + "already exists in current scope.");
        }
        Symbol symbol = new Symbol(make);
        table.put(make.functionName, symbol);
        globalTable.put(make.functionName, symbol);
    }


    public Symbol get(String name) {
        if (table.containsKey(name)) {
            return table.get(name);
        } else if (parent != null) {
            return parent.get(name);
        }
        return null;
    }

    public Integer getAddress(String name) {
        return get(name).address;
    }

    public DataType getType(String name, DataType t) {
        return get(name).datatype;
    }

    public boolean exists(String name) {
        if(globalTable.containsKey(name) && globalTable.get(name).address == this.getAddress(name))  {
            return true;
        }
        return false;
    }

    public boolean isDataType(String name, DataType d) {
        return get(name).datatype == d;
    }

    public boolean inScope(String name) {
        return get(name) != null;
    }
}