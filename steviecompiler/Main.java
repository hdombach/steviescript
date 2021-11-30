package steviecompiler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;
import java.util.ArrayList;

import steviecompiler.node.Node;
import steviecompiler.commands.Command;
import steviecompiler.error.ErrorHandler;
 
public class Main{   
    public static String filePath;
    public static ArrayList<String> files = new ArrayList<String>();
    public static String outputPath = "";
    public static ArrayList<String> codeText = new ArrayList<String>();
    public static ArrayList<Command> commands;

    public static void main(String[] args) {         

        parseArgs(args);
        if(args.length == 0) {
            parseHeader("test.sh"); //default
        }
        System.out.println("Files: " + files + "\n\n\n");
        for(String f : files) {
            filePath = f;
            readFile(f);
            Token.tokenize(codeText);
            System.out.println(Token.getTokenList() + "\n\n\n");
            Node.parse(Token.getTokenList());
            System.out.println(Node.getCode(f));
            Token.clear();
            codeText.clear();
        } 
        Node.checkScope();
        Node.getAllReqMemory();
        commands = Node.makeAllCommands();
        setCommandPositions();
        if(ErrorHandler.errorCount() == 0) {
            write("./test.s");
            writeRaw("./raw.txt");
        }
        ErrorHandler.throwErrors();
    }

    public static void parseArgs(String[] args) {
        if(args.length == 0) {
            return;
        }
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
        String temp;
        while (scan.hasNextLine()) {
                temp = scan.nextLine();
                if(temp.length() != 0) {
                    files.add(temp);
                }
            }
        }
        catch (FileNotFoundException e) {
            ErrorHandler.generate(004);
        }
    }

    public static void readFile(String path) {
        try {
            Scanner reader = new Scanner(new File(path));
            while(reader.hasNextLine()) {
                String thisLine = reader.nextLine();
                codeText.add(thisLine);
            }
            reader.close();
        }
        catch (Exception e) {
            ErrorHandler.generate(005);
        }
    }

    public static void write(String path) {
        try {
            File out = new File(path);
            out.createNewFile();
            FileWriter writer = new FileWriter(path);
            writer.write(getAssembly());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static void writeRaw(String path) {
        try {
            File out = new File(path);
            out.createNewFile();
            FileWriter writer = new FileWriter(path);
            writer.write(getCommandDescriptions());
            writer.close();
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();
        }
    }

    public static String getLine(int line) {
      return codeText.get(line - 1);
    }

    static private void setCommandPositions() {
        //variables like current frame pointer are stored at address 0
        int c = 8;
        for (Command command : commands) {
            command.setLocation(c);
            c += command.getLength();
        }
    }
    static private String getAssembly() {
        String result = "";
        String temp;
        for (Command command : commands){
            temp = command.toAssembly();
            if (temp.lines().count() != command.getLength()) {
                throw new Error("Command " + command + " said it would be " + command.getLength() + " bytes long instead of " + temp.lines().count());
            }
            result += command.toAssembly();
        }
        return result;
    }

    static private String getCommandDescriptions() {
        String r = "";
        for (Command command : commands) {
            r += command.getLocation() + ": " + command + "\n";
        }
        return r;
    }
}

