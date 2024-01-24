import ccompiler.*;
import ccompiler.token.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Test {
    public static void main(String[] args) throws IOException {
        System.out.println("Enter a filename to parse.");
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
        String fileName = br.readLine();
        Scanner scanner = new Scanner(fileName);
        Token t = scanner.getNextToken();
        while(t.getType() != TokenType.UNKNOWN) {
            System.out.println(t.getToken() + " " + t.getType().toString());
            t = scanner.getNextToken();
        }
    }
}
