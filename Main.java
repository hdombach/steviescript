import java.io.File;
import java.util.Scanner;

public class Main{   
    public static void main(String[] args){
      readFile("test.txt");
      System.out.println(Token.getTokenList());
    }

    public static void readFile(String path) {
      try {
        Scanner reader = new Scanner(new File(path));
        while(reader.hasNextLine()) {
          Token.tokenize(reader.nextLine());
        }
        reader.close();
      }
      catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
}

