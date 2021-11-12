package steviecompiler;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Scanner;
import java.util.HashMap;
import java.util.ArrayList;

import steviecompiler.node.Node;
import steviecompiler.symbol.SymbolTable;
import steviecompiler.commands.Command;
import steviecompiler.error.ErrorHandler;
 

public class Main{   
    public static String filePath;
    public static HashMap<String, ArrayList<String>> codeText = new HashMap<String, ArrayList<String>>();
    public static ArrayList<Command> commands;

    public static void main(String[] args){

      filePath = "test.txt";
      if (args.length > 0) {
        filePath = args[0];
      }
      readFile(filePath);
      System.out.println(Token.getTokenList());
      Node.parse(Token.getTokenList());
      Node.checkScope();
      Node.getAllReqMemory();
      //commands = Command.generate();
      System.out.println(Node.getCode());
      if(ErrorHandler.errorCount() == 0) {
          //write(args[args.length - 1]);
      }
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

    public static void write(String path) {
        try {
            File out = new File(path);
            out.createNewFile();
            FileWriter writer = new FileWriter(path);
            for(Command c : commands) {
                writer.write(c.toString()); //TODO: toString for Command that prints out a command
                writer.close();
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getLine(int line) {
      return codeText.get(filePath).get(line - 1);
    }
}

