package steviecompiler;

import java.io.File;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import steviecompiler.node.Node;
import steviecompiler.symbol.SymbolTable;
import steviecompiler.error.ErrorHandler;
 

public class Main{   
    public static String filePath;
    public static HashMap<String, ArrayList<String>> codeText = new HashMap<String, ArrayList<String>>();
    public static void main(String[] args){
      filePath = "test.txt";
      if (args.length > 0) {
        filePath = args[0];
      }
      readFile(filePath);
      System.out.println(Token.getTokenList());
      Node.parse(Token.getTokenList());
      System.out.println(Node.getCode());
      ErrorHandler.throwErrors();
    }

    public static void readFile(String path) {
      try {
        Scanner reader = new Scanner(new File(path));
        codeText.put(path, new ArrayList<String>());
        int line = 1;
        while(reader.hasNextLine()) {
          String thisLine = reader.nextLine();
          codeText.get(path).add(thisLine);
          Token.tokenize(thisLine, line);
          line++;
        }
        reader.close();
      }
      catch (Exception e) {
        e.printStackTrace();
        System.exit(1);
      }
    }

    public static String getLine(int line) {
      return codeText.get(filePath).get(line - 1);
    }
}

