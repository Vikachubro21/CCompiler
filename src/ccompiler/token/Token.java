package ccompiler.token;


public class Token {
    TokenType type;
    String word;
    public Token(String token, TokenType tokenType) {
        word = token;
        type = tokenType;
    }

    public TokenType getType() {
        return type;
    }

    public String getToken() {
        return word;
    }
}