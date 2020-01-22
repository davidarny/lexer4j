package lexer;

/**
 * The {@code Token} class represents token (lexeme). A token is a string of
 * characters, categorized according to the rules as a symbol. For example: <i>
 * Identifier, Comma, DoubleConstant</i>.
 */
public class Token {
    /**
     * The ending index of token in the input
     */
    private int to;

    /**
     * Position of token on the line
     */
    private int position;

    /**
     * Line the token belongs to
     */
    private int line;

    /**
     * Type(category) of token
     */
    private TokenType type;

    /**
     * String of characters for this token
     */
    private String literal;

    /**
     * Constructs new {@code Token} object with specified parameters.
     *
     * @param line    line the token belongs to
     * @param to      the ending index of token in the input, exclusive
     * @param literal string of characters
     * @param type    type of token
     */
    public Token(int to, String literal, TokenType type, int line, int position) {
        this.to = to;
        this.type = type;
        this.literal = literal;
        this.line = line;
        this.position = position;
    }

    /**
     * Returns the ending index
     *
     * @return the ending index of token in the input, exclusive
     */
    public int getEndPosition() {
        return to;
    }

    public boolean isNotAuxiliary() {
        return !type.isAuxiliary();
    }

    @Override
    public String toString() {
        if (isNotAuxiliary()) {
            return type + " '" + literal.trim() + "' [L" + line + ":" + position + "]";
        } else {
            return type + " [L" + line + ":" + position + "]";
        }
    }
}
