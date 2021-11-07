package steviecompiler.symbol;

import java.util.HashMap;

import javax.xml.crypto.Data;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;

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
        Symbol symbol = new Symbol(make);
        table.put(make.name, symbol);
        globalTable.put(make.name, symbol);
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