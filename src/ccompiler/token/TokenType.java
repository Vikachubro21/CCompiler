package ccompiler.token;
public enum TokenType {
    // Operator
    OPERATOR,
    // Keyword
    KEYWORD,
    // Identifier
    IDENTIFIER,
    // Literals
    INTEGER_LITERAL, STRING_LITERAL, FLOATING_LITERAL,
    // Unknown
    UNKNOWN, INVALID
}