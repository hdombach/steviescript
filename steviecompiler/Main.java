package steviecompiler;

import java.io.File;
import java.util.Scanner;

import steviecompiler.node.Node;


public class Main{   
    public static String filePath;
    public static void main(String[] args){
      filePath = args[0]; 
      readFile(filePath);
      Node.parse(Token.getTokenList());
      System.out.println(Node.getCode());
    }

    public static void readFile(String path) {
      try {
        Scanner reader = new Scanner(new File(path));
        int line = 1;
        while(reader.hasNextLine()) {
          Token.tokenize(reader.nextLine(), line);
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

