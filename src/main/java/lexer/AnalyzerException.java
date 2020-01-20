package lexer;

/**
 * The {@code AnalyzerException} class represents exceptions which may be caused
 * by lexical or syntax errors
 */
public class AnalyzerException extends Exception {
    /**
     * Creates {@code AnalyzerException} object with specified error position
     * and message
     *
     * @param message  detailed message
     * @param position position of the error
     */
    public AnalyzerException(String message, int position) {
        super(String.format("%d: %s", position, message));
    }
}
