package steviecompiler.symbol;

import java.util.HashMap;

import steviecompiler.node.CreateVar;

public class SymbolTable {
    public static HashMap<String, Symbol> globalTable = new HashMap<String, Symbol>();

    public static HashMap<String, Symbol> table;

    public SymbolTable() {
        table = new HashMap<String, Symbol>();
    }

    public void symbolize(CreateVar create) {
        Symbol temp = new Symbol(create);
        table.put(create.name, temp);
        globalTable.put(create.name, temp);
    }

    public Symbol get(String name) {
        return table.get(name);
    }

    public static Symbol getGlobal(String name) {
        return globalTable.get(name);
    }

    public boolean exists(String name) {
        if(globalTable.containsKey(name))  {
            return true;
        }
        return false;
    }

    public boolean inScope(String name) {
        if(exists(name) && table.containsKey(name)) {
            return true;
        }
        return false;
    }
}