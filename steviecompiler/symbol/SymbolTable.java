package steviecompiler.symbol;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

import steviecompiler.node.Block;
import steviecompiler.node.CreateVar;
import steviecompiler.node.DataType;
import steviecompiler.node.DefFunction;
import steviecompiler.node.DefParam;
import steviecompiler.node.Param;
import steviecompiler.symbol.Symbol.SymbolType;

public class SymbolTable {
   // public static HashMap<String, Symbol> globalTable = new HashMap<String, Symbol>();

   
    private ArrayList<String> paramOrder;

    public HashMap<String, ArrayList<Symbol>> table;

    public HashMap<String, ArrayList<Symbol>> sharedTable;

    //can be null
    public SymbolTable parent;

    private Block block;

    private int framePointersSize;

    public SymbolTable(SymbolTable parent, Block block) {
        this(parent, block, null);
    }

    public SymbolTable(SymbolTable parent, Block block, HashMap<String, ArrayList<Symbol>> shared) {
        this.sharedTable = shared;
        table = new HashMap<String, ArrayList<Symbol>>();
        paramOrder = new ArrayList<String>();
        this.parent = parent;
        this.block = block;
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
            symbolize("%", new DataType("int"), new DataType("int"), new DataType("int"));

            symbolize("+", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("-", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("*", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("/", new DataType("char"), new DataType("char"), new DataType("char"));
            symbolize("%", new DataType("char"), new DataType("char"), new DataType("char"));

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

        framePointersSize = (getParents() - 1) * new DataType("pointer").getReqMemory();
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
                sharedList = sharedTable.get(name);
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
            this.paramOrder.add(param.name);
            addSymbol(new Symbol(param), param.name);
        }
    }

    public int getParents() {
        if (parent == null) {
            return 1;
        } else {
            return parent.getParents() + 1;
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

    /*public ArrayList<Symbol> getLocalVariables() {
        ArrayList<Symbol> result = new ArrayList<Symbol>();
        for (ArrayList<Symbol> list : table.values()) {
            for (Symbol s : list) {
                if (s.type == SymbolType.VALUE) {
                    result.add(s);
                }
            }
        }
        return result;
    }*/

    //TODO: need to test this still later on (can prob test when stuff is set up for code generation)
    //Need to figure how this works with global variabls and shared modules.
    public LocalAddress getValueAddress(String name) {

        int currentOffset = paramOffset();

        if (paramOrder.contains(name)) {
            for (String param : paramOrder) {
                if (param.equals(name)) {
                    return new LocalAddress(0, currentOffset);
                }
                currentOffset += getValue(name).getMemSize();
            }
        }

        //dedicated variables appear beneath the temp memory in the stack
        
        //make sure order of variables is always the same
        Set<String> localSet = new HashSet<String>();
        if (sharedTable == null) {
            localSet.addAll(table.keySet());
            for (String param : paramOrder) {
                localSet.remove(param);
            }
        } else {
            localSet.addAll(sharedTable.keySet());
        }
        String[] localKeys = localSet.toArray(new String[0]);
        Arrays.sort(localKeys);

        for (Object key : localKeys) {
            ArrayList<Symbol> symbols = table.get(key);
            for (Symbol symbol : symbols) {
                if (symbol.type == SymbolType.VALUE) {
                    if (key.equals(name)) {
                        return new LocalAddress(0, currentOffset);
                    }
                    currentOffset += symbol.dataType.getReqMemory();
                }
            }
        }

        if (parent != null) {
            return parent.getValueAddress(name).increased();
        }

        //theoretically this should neve happen since all the names would be tested in checkSymbols.
        //In other words, if this happens, something is terribly wrong.
        throw new Error("Value " + name + " could not be found when looking for an address");
    }

    public int getStackSize() {
        int sum = localOffset();
        for (ArrayList<Symbol> symbols : table.values()) {
            for (Symbol symbol : symbols) {
                if (symbol.type == SymbolType.VALUE) {
                    sum += symbol.getMemSize();
                }
            }
        }
        sum -= getParamSize(); //params are stored in local variables.
        return sum;
    }

    public int getParamSize() {
        int sum = 0;
        for (String param : paramOrder) {
            sum += getValue(param).getMemSize();
        }
        return sum;
    }

    public int localOffset() {
        return getParamSize() + paramOffset();
    }
    public int paramOffset() {
        //the current frame pointer is stored elsewhere so it doesn't need to be in the frame pointer. Instead, store the previous frame pointer
        return framePointerOffset() + (getParents()) * new DataType("pointer").getReqMemory();
    }
    public int framePointerOffset() {
        return gotoOffset() + new DataType("pointer").getReqMemory();
    }
    public int returnOffset() {
        return 0;
    }
    public int gotoOffset() {
        if (block.returnType == null) {
            return 0;
        }
        return block.returnType.getReqMemory();
    }

    public FunctionSymbol getFunction(String name, ArrayList<DataType> params) {
        ArrayList<Symbol> symbols = getList(name);
        for (Symbol symbol : symbols) {
            if (symbol.type == SymbolType.FUNCTION) {
                FunctionSymbol fSymbol = (FunctionSymbol) symbol;
                if (Param.compare(params, fSymbol.params)) {
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