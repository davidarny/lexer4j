package lexer;

/**
 * The {@code Token} class represents token (lexeme). A token is a string of
 * characters, categorized according to the rules as a symbol. For example: <i>
 * Identifier, Comma, DoubleConstant</i>.
 */
public class Token {
    /**
     * The beginning index of this token in the input
     */
    private int from;

    /**
     * The ending index of token in the input
     */
    private int to;

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
     * @param from    the beginning index of this token in the input, inclusive
     * @param to      the ending index of token in the input, exclusive
     * @param literal string of characters
     * @param type    type of token
     */
    public Token(int from, int to, String literal, TokenType type) {
        this.from = from;
        this.to = to;
        this.type = type;
        this.literal = literal;
    }

    /**
     * Returns the ending index
     *
     * @return the ending index of token in the input, exclusive
     */
    public int getEnd() {
        return to;
    }

    public boolean isNotAuxiliary() {
        return !type.isAuxiliary();
    }

    @Override
    public String toString() {
        if (isNotAuxiliary())
            return type + "  '" + literal + "' [" + from + ";" + to + "] ";
        else
            return type + "   [" + from + ";" + to + "] ";
    }
}
