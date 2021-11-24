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
        result += parseInt(resultAddress) + "\n";
        result += parseInt(4) + "\n";
        result += parseInt(value) + "\n";
        return result;
    } 
    public static String getAssembly(int resultAddress, String value) {
        byte[] data = value.getBytes();
        String result = "12\n";
        result += parseInt(resultAddress) + "\n";
        result += parseInt(data.length) + "\n";
        result += parseBytes(data) + "\n";
        return result;
    }

    public int getLength() {
        if (svalue == null) {
            return 16;
        } else {
            byte[] data = svalue.getBytes();
            return 12 + data.length;
        }
    }
}
