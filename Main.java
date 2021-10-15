import java.util.ArrayList;

public class Main{   
    public static void main(String[] args){
      Token.tokenize("if(bones yeet)");
      System.out.println(Token.getTokenList());
    }
}

