package steviecompiler.commands;

public class LoadCommand extends Command {
    int a;
    int ivalue;
    String svalue;

    public LoadCommand(int address, int value) {
        this.a = address
        this.ivalue = value;
    }

    public String toAssembly() {
        if (svalue != null) {
            return getAssembly(a, svalue);
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
}
