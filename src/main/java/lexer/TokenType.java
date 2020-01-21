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
    SCIENTIFIC_CONSTANT,
    DOUBLE_CONSTANT,
    FLOAT_CONSTANT,
    INT_CONSTANT,
    PLUS,
    MINUS,
    MULTIPLY,
    DIVIDE,
    POINT,
    EQUAL_EQUAL,
    EQUAL,
    NOT_EQUAL,
    GREATER,
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
    VAR;

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
