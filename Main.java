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
        int line = 1;
        while(reader.hasNextLine()) {
          Token.tokenize(reader.nextLine(), line);
          new Token(Token.TokenType.ENDLN, line);
          line++;
        }
        reader.close();
      }
      catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }
}

