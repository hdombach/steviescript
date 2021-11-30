package steviecompiler.commands;

public class NormalizeCommand extends Command {
   int result;
   int a;
   
   public NormalizeCommand(int result, int address) {
       this.result = result;
       this.a = address;
   }

   public String toAssembly() {
       return getAssembly(result, a);
   }

   public static String getAssembly(int result, int address) {
       String r = "19\n";
       r += parseInt(result);
       r += parseInt(address);
       return r;
   }

   public int getLength() {
       return 9;
   }

   public String toString() {
       return "Normalize " + result + " " + a;
   }
}
