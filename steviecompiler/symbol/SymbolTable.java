package steviecompiler.symbol;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import javax.xml.crypto.Data;

import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.DefParam;
import steviecompiler.node.Param;
import steviecompiler.node.expression.FunctionCall;
import steviecompiler.symbol.Symbol.SymbolType;

public class SymbolTable {
   // public static HashMap<String, Symbol> globalTable = new HashMap<String, Symbol>();

    public HashMap<String, ArrayList<Symbol>> table;

    public HashMap<String, ArrayList<Symbol>> sharedTable;

    //can be null
    public SymbolTable parent;

	public int requiredTempMemory;

    public SymbolTable(SymbolTable parent) {
        this(parent, null);
    }

    public SymbolTable(SymbolTable parent, HashMap<String, ArrayList<Symbol>> shared) {
        this.sharedTable = shared;
        table = new HashMap<String, ArrayList<Symbol>>();
        this.parent = parent;
        //if root node the add default data types
        if (parent == null && sharedTable.size() == 0) {
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

            symbolize(">", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("<", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize(">=", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("!=", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("<=", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("&&", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("||", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("^^", new DataType("int"), new DataType("int"), new DataType("boolean"));
            symbolize("==", new DataType("int"), new DataType("int"), new DataType("boolean"));
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

        //this shared list allows files to know about data types in other files.
        if (sharedTable != null) {
            ArrayList<Symbol> sharedList;
            if (sharedTable.containsKey(name)) {
                sharedList = table.get(name);
            } else {
                sharedList = new ArrayList<Symbol>();
                sharedTable.put(name, sharedList);
            }

            sharedList.add(s);
        }
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

    public void symbolize(ArrayList<DefParam> params) {
        for (DefParam param : params) {
            if (getValue(param.name) != null) {
                throw new Error("Symbol " + param.name + "already exists in current scope. ");
            }
            addSymbol(new Symbol(param), param.name);
        }
    }

    //symobls in current close will be at beggining.
    //as a result, don't have to worry about not include same names from parent SymbolTable
    private ArrayList<Symbol> getList(String name) {
        ArrayList<Symbol> result = new ArrayList<Symbol>();

        if (sharedTable == null) {
            if (table.containsKey(name)) {
                result.addAll(table.get(name));
            }
        } else {
            if (sharedTable.containsKey(name)) {
                result.addAll(sharedTable.get(name));
            }
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

    //TODO: need to test this still later on (can prob test when stuff is set up for code generation)
    public int getValueAddress(String name) {
        //dedicated variables appear beneath the temp memory in the stack
        
        Object[] localKeys = table.keySet().toArray();
        Arrays.sort(localKeys);

        int currentAddress = 0 - requiredTempMemory;

        for (Object key : localKeys) {
            ArrayList<Symbol> symbols = table.get(key);
            for (Symbol symbol : symbols) {
                if (symbol.type == SymbolType.VALUE) {
                    currentAddress -= symbol.dataType.getReqMemory();
                }
                if ((String) key == name) {
                    return currentAddress;
                }
            }
        }

        if (parent != null) {
            return currentAddress - parent.getValueAddress(name);
        }

        //theoretically this should neve happen since all the names would be tested in checkSymbols.
        //In other words, if this happens, something is terribly wrong.
        throw new Error("Value " + name + " could not be found when looking for an address");
    }

    public FunctionSymbol getFunction(String name, ArrayList<DataType> params) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.FUNCTION) {
                FunctionSymbol fSymbol = (FunctionSymbol) symbol;
                if (Param.compare(params, fSymbol.params)) { //TODO make a function to compare lists of params
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
                if (oSymbol.left.compare(l) && oSymbol.right.compare(r)) {
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