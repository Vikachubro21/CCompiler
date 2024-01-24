package ccompiler.token;


public class Token {
    TokenType type;
    String word;
    public Token(String token, TokenType tokenType) {
        word = token;
        type = tokenType;
    }
}