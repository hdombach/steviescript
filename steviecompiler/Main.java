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
    public static ArrayList<String> files = new ArrayList<String>();
    public static String outputPath = "";
    public static HashMap<String, ArrayList<String>> codeText = new HashMap<String, ArrayList<String>>();
    public static ArrayList<Command> commands;

    public static void main(String[] args) {

        filePath = "test.txt";
        readFile(filePath);

        //parseArgs(args);
        /*for(String s : files) {
            readFile(s);
        }*/
        System.out.println(Token.getTokenList());
         Node.parse(Token.getTokenList());
        System.out.println(Node.getCode());
        Node.checkScope();
        //commands = Command.generate();
        if(ErrorHandler.errorCount() == 0) {
            //write(outputPath);
        }
        ErrorHandler.throwErrors();
    }

    public static void parseArgs(String[] args) {
        boolean filesComplete = false;
        for(int i = 0; i < args.length; i++) {
            if(!filesComplete) {
                if(args[i].contains("-")) {
                    filesComplete = true;
                }
                else {
                    files.add(args[i]);
                }
            }
            if(args[i].equals("-o")) {
                outputPath = args[i + 1];
                i++;
            }
        }
        if(outputPath.equals("")) {
            outputPath = "stevout.t";
        }
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

