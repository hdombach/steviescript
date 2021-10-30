package steviecompiler.symbol;

import java.util.HashMap;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;

public class SymbolTable {
    public static HashMap<String, Flag> globalTable = new HashMap<String, Flag>();

    public static HashMap<String, Flag> table;

    public SymbolTable() {
        table = new HashMap<String, Flag>();
    }

    public void symbolize(CreateVar make) {
        Flag symbol = new Flag(make);
        table.put(make.name, symbol);
        globalTable.put(make.name, symbol);
    }


    public Flag get(String name) {
        return table.get(name);
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
        if(exists(name) && table.containsKey(name)) {
            return true;
        }
        return false;
    }
}