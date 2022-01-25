package steviecompiler.commands;

public class LoadCommand extends Command {
    int a;
    int ivalue;
    String svalue;
    Command gotoCommand;

    public LoadCommand(int address, int value) {
        this.a = address;
        this.ivalue = value;
    }

    public LoadCommand(int address, Command gotoCommand) {
        this.a = address;
        this.gotoCommand = gotoCommand;
    }

    public String toAssembly() {
        if (svalue != null) {
            return getAssembly(a, svalue);
        } else if (gotoCommand != null) {
            return getAssembly(a, gotoCommand.location + gotoCommand.getLength());
        } else {
            return getAssembly(a, ivalue);
        }
    }

    public static String getAssembly(int resultAddress, int value) {
        String result = "12\n";
        result += parseInt(resultAddress);
        result += parseInt(4);
        result += parseInt(value);
        return result;
    } 
    public static String getAssembly(int resultAddress, String value) {
        byte[] data = value.getBytes();
        String result = "12\n";
        result += parseInt(resultAddress);
        result += parseInt(data.length);
        result += parseBytes(data);
        return result;
    }

    public int getLength() {
        if (svalue == null) {
            return 13;
        } else {
            byte[] data = svalue.getBytes();
            return 9 + data.length;
        }
    }

    public String toString() {
        if (svalue != null) {
            return "Load Command " + a + " value: " + svalue;
        } else if (gotoCommand != null) {
            return "Load Command " + a + " value: " + (gotoCommand.location + gotoCommand.getLength());
        } else {
            return "Load Command " + a + " value: " + ivalue;
        }
    }

    /**
     * Adds the last command involved when calling a functtion
     * @param c
     */
    public void addGotoCommand(Command c) {
        gotoCommand = c;
    }
}
