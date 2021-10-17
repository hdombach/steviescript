import java.util.ArrayList;
import java.io.File;
import java.util.Scanner;

public class Main{   
    public static void main(String[] args){
      String codeString = readFile("test.txt");
      Token.tokenize(codeString);
      System.out.println(Token.getTokenList());
    }

    public static String readFile(String path) {
      String result = "";
      try {
        Scanner reader = new Scanner(new File(path));
        while(reader.hasNextLine()) {
          result = result + reader.nextLine();
        }
        reader.close();
      }
      catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
      return result;
    }
}

