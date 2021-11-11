package steviecompiler.symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.crypto.Data;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.expression.FunctionCall;
import steviecompiler.symbol.Symbol.SymbolType;

public class SymbolTable {
   // public static HashMap<String, Symbol> globalTable = new HashMap<String, Symbol>();

    public HashMap<String, ArrayList<Symbol>> table;

    //can be null
    public SymbolTable parent;

    public SymbolTable(SymbolTable parent) {
        table = new HashMap<String, ArrayList<Symbol>>();
        this.parent = parent;
        //if root node the add default data types
        if (parent == null) {
            symbolize(new DataType("int"));
            symbolize(new DataType("pointer"));
            symbolize(new DataType("char"));
            symbolize(new DataType("boolean"));
            symbolize(new DataType("byte"));
            
            //allowed operations
            symbolize("+", new DataType("int"), new DataType("int"), new DataType("int"));
            symbolize("-", new DataType("int"), new DataType("int"), new DataType("int"));
            symbolize("*", new DataType("int"), new DataType("int"), new DataType("int"));
            symbolize("/", new DataType("int"), new DataType("int"), new DataType("int"));

            symbolize("+", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("-", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("*", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("/", new DataType("char"), new DataType("char"), new DataType("char"));

            symbolize(">", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("<", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize(">=", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("!=", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize(">=", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("&&", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("||", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("^^", new DataType("boolean"), new DataType("int"), new DataType("int"));
            symbolize("==", new DataType("boolean"), new DataType("int"), new DataType("int"));
        }
    }

    private void addSymbol(Symbol s, String name) {
        ArrayList<Symbol> list;

        if (table.containsKey(name)) {
            list = table.get(name);
        } else {
            list = new ArrayList<Symbol>();
            table.put(name, list);
        }

        list.add(s);
    }

    public void symbolize(CreateVar make) {
        if (getValue(make.name) != null) {
            throw new Error("Sumbol " + make.name + " already exists in current scope. ");
        }
        addSymbol(new Symbol(make), make.name);
    }

    public void symbolize(DefFunction make) {
        if (getFunction(make.functionName, make.getParamsTypes()) != null) {
            throw new Error("Symbol " + make.functionName + "already exists in current scope.");
        }
        addSymbol(new FunctionSymbol(make), make.functionName);
    }

    public void symbolize(DataType make) {
        if (getDataType(make.getType()) != null) {
            throw new Error("Symbol " + make.getType() + " already exists in current scope.");
        }
        addSymbol(new Symbol(make), make.getType());
    }

    public void symbolize(String name, DataType l, DataType r, DataType type) {
        if (getOperator(name, l, r) != null) {
            throw new Error("Symbol " + name + " already exists in current scope.");
        }
        addSymbol(new OperatorSymbol(l, r, type), name);
    }

    //symobls in current close will be at beggining.
    //as a result, don't have to worry about not include same names from parent SymbolTable
    private ArrayList<Symbol> getList(String name) {
        ArrayList<Symbol> result = new ArrayList<Symbol>();

        if (table.containsKey(name)) {
            result.addAll(table.get(name));
        }

        if (parent != null) {
            result.addAll(parent.getList(name));
        }
        return result;
    }

    public Symbol getValue(String name) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.VALUE) {
                return symbol;
            }
        }
        return null;
    }

    public FunctionSymbol getFunction(String name, ArrayList<DataType> params) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.FUNCTION) {
                FunctionSymbol fSymbol = (FunctionSymbol) symbol;
                if (params.equals(fSymbol.params)) {
                    return fSymbol;
                } 
            }
        }
        return null;
    }

    public OperatorSymbol getOperator(String name, DataType l, DataType r) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.OPERATOR) {
                OperatorSymbol oSymbol = (OperatorSymbol) symbol;
                if (oSymbol.left == l && oSymbol.right == r) {
                    return oSymbol;
                }
            }
        }
        return null;
    }

    public Symbol getDataType(String name) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.DATATYPE) {
                return symbol;
            }
        }
        return null;
    }
}