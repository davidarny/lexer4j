package lexer;

/**
 * The {@code TokeType} enumeration represents types of tokens in subset of Java
 * language
 */
public enum TokenType {
    BLOCK_COMMENT,
    LINE_COMMENT,
    WHITE_SPACE,
    TAB,
    NEW_LINE,
    CLOSING_BRACE,
    OPENING_BRACE,
    OPENING_CURLY_BRACE,
    CLOSING_CURLY_BRACE,
    SCIENTIFIC_LITERAL,
    OCTAL_LITERAL,
    DOUBLE_LITERAL,
    FLOAT_LITERAL,
    INT_LITERAL,
    HEX_LITERAL,
    BINARY_LITERAL,
    ADDITION,
    SUBTRACTION,
    MULTIPLICATION,
    DIVISION,
    XOR,
    POINT,
    EQUALS,
    NOT_EQUALS,
    ASSIGNMENT,
    MORE,
    LESS,
    STATIC,
    PUBLIC,
    PRIVATE,
    INT,
    DOUBLE,
    FLOAT,
    VOID,
    FALSE,
    TRUE,
    NULL,
    RETURN,
    NEW,
    CLASS,
    IF,
    WHILE,
    FOR,
    ELSE,
    SEMICOLON,
    COLON,
    COMMA,
    IDENTIFIER,
    OPENING_SQUARE_BRACE,
    CLOSING_SQUARE_BRACE,
    VAR,
    FINAL,
    MULTILINE_STRING_LITERAL,
    STRING_LITERAL,
    CHAR_LITERAL;

    /**
     * Determines if this token is auxiliary
     *
     * @return {@code true} if token is auxiliary, {@code false} otherwise
     */
    public boolean isAuxiliary() {
        return this == BLOCK_COMMENT ||
            this == LINE_COMMENT ||
            this == NEW_LINE ||
            this == TAB ||
            this == WHITE_SPACE;
    }
}
