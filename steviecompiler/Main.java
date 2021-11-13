package steviecompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
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
    public static ArrayList<String> codeText = new ArrayList<String>();
    public static ArrayList<Command> commands;

    public static void main(String[] args) {

        filePath = "test.txt";
        readFile(filePath);

        //parseArgs(args);
        /*for(String s : files) {
            readFile(s);
        }*/
        for(String f : files) {
            Token.tokenize(codeText);
            System.out.println(Token.getTokenList());
            Node.parse(Token.getTokenList());
            Token.clear();
        }
        Node.checkScope();
        Node.getAllReqMemory();
        System.out.println(Node.getCode());
        Node.getCode().makeAllCommands();
        commands = Command.generate();
        if(ErrorHandler.errorCount() == 0) {
            //write(outputPath);
        }
        ErrorHandler.throwErrors();
    }

    public static void parseArgs(String[] args) {
        if(args[0].contains(".sh")) {
            parseHeader(args[0]);
        }
        else if(args[0].contains(".stv")) {
            readFile(args[0]);
        }
        else {
            ErrorHandler.generate(004); //invalid input error
        }

        for(int i = 1; i < args.length; i++) {
            if(args[i].equals("-o")) {
                outputPath = args[i + 1];
                i++;
            }
        }
        if(outputPath.equals("")) {
            outputPath = "stevout.t";
        }
    }

    public static void parseHeader(String path) {
        try {
        Scanner scan = new Scanner(new File(path));
        while (scan.hasNextLine()) {
                readFile(scan.nextLine());
            }
        }
        catch (FileNotFoundException e) {
            ErrorHandler.generate(004);
        }
    }

    public static void readFile(String path) {
      try {
        Scanner reader = new Scanner(new File(path));
        int line = 1;
        while(reader.hasNextLine()) {
          String thisLine = reader.nextLine();
          codeText.add(thisLine);
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
            }
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getLine(int line) {
      return codeText.get(line - 1);
    }
}

