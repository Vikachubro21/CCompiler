package ccompiler;

import ccompiler.token.Token;
import ccompiler.token.TokenType;

import java.io.FileReader;
import java.io.IOException;

public class Scanner {
    final int BUFFER_SIZE = 1024;
    String filename;
    FileReader fileReader;
    // buffer to read file
    char[] buffer;
    // current position in buffer
    int bufferPointer;
    // position of character after last valid read
    int bufferValid;
    public Scanner(String fileName) throws IOException {
        filename = fileName;
        fileReader = new FileReader(fileName);
        buffer = new char[BUFFER_SIZE];
        bufferPointer = 0;
        bufferValid = 0;
    }

    public Token getNextToken() throws IOException {
        StringBuilder tokenValue = new StringBuilder();
        TokenType tokenType = TokenType.UNKNOWN;
        while(tokenType != TokenType.INVALID) {
            if(bufferPointer == BUFFER_SIZE) {
                bufferValid = fileReader.read(buffer);
                bufferPointer = 0;
            }
            if(bufferValid <= bufferPointer)
                break;
            if(" /t/n".indexOf(buffer[bufferPointer]) != -1) {
                switch(tokenType) {
                    case UNKNOWN:
                        if(buffer[bufferPointer] == '\"')
                            tokenType = TokenType.STRING_LITERAL;
                        if(Character.isDigit(buffer[bufferPointer]))
                            tokenType = TokenType.INTEGER_LITERAL;
                        if(Character.isLetter(buffer[bufferPointer]))
                            tokenType = TokenType.IDENTIFIER;
                        else
                            tokenType = TokenType.OPERATOR;
                        break;
                    case STRING_LITERAL:
                        break;
                    case INVALID:
                        break;
                }
                tokenValue.append(buffer[bufferPointer]);
            }
            bufferPointer++;
        }
        return new Token(tokenValue.toString(), tokenType);
    }
}
