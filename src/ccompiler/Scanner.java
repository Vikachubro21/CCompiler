package ccompiler;

import ccompiler.token.Token;
import ccompiler.token.TokenType;

import javax.print.DocFlavor;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.stream.Collectors;

public class Scanner {
    final int BUFFER_SIZE = 1024;
    final HashSet<String> KEYWORDS = new HashSet<>(Arrays.stream(new String[] {
            "auto", "break", "case", "char", "const", "continue", "default", "do",
            "double", "else", "enum", "extern", "float", "for", "goto", "if", "int",
            "long", "register", "return", "short", "signed", "sizeof", "static",
            "struct", "switch", "typedef", "union", "unsigned", "void", "volatile",
            "while"
        }).collect(Collectors.toSet()));
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
        bufferPointer = BUFFER_SIZE;
        bufferValid = 0;
    }

    public Token getNextToken() throws IOException {
        StringBuilder tokenValue = new StringBuilder();
        TokenType tokenType = TokenType.UNKNOWN;
        bufferPointer--;
        while(tokenType != TokenType.INVALID) {
            bufferPointer++;
            if(bufferPointer == BUFFER_SIZE) {
                bufferValid = fileReader.read(buffer);
                bufferPointer = 0;
            }
            if(bufferValid <= bufferPointer)
                break;
            if(!Character.isWhitespace(buffer[bufferPointer])) {
                switch(tokenType) {
                    case UNKNOWN:
                        if(buffer[bufferPointer] == '\"')
                            tokenType = TokenType.STRING_LITERAL;
                        else if(Character.isDigit(buffer[bufferPointer]))
                            tokenType = TokenType.INTEGER_LITERAL;
                        else if(Character.isLetter(buffer[bufferPointer]))
                            tokenType = TokenType.IDENTIFIER;
                        else
                            tokenType = TokenType.OPERATOR;
                        break;
                    case STRING_LITERAL:
                        break;
                    case INTEGER_LITERAL:
                        if(buffer[bufferPointer] == '.')
                            tokenType = TokenType.FLOATING_LITERAL;
                        else if(!Character.isDigit(buffer[bufferPointer]))
                            tokenType = TokenType.INVALID;
                        break;
                    case FLOATING_LITERAL:
                        if(!Character.isDigit(buffer[bufferPointer]))
                            tokenType = TokenType.INVALID;
                        break;
                    case IDENTIFIER:
                        break;
                    case OPERATOR:
                        break;
                    default:
                        return new Token(tokenValue.toString(), TokenType.INVALID);
                }
                tokenValue.append(buffer[bufferPointer]);
            }
            else if(tokenType != TokenType.UNKNOWN) {
                break;
            }
        }
        if(tokenType == TokenType.IDENTIFIER && KEYWORDS.contains(tokenValue.toString()))
            tokenType = TokenType.KEYWORD;
        return new Token(tokenValue.toString(), tokenType);
    }

}
