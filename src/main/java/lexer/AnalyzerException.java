package lexer;

import lombok.SneakyThrows;
import lombok.val;

import java.io.Closeable;

/**
 * The {@code AnalyzerException} class represents exceptions which may be caused
 * by lexical or syntax errors
 */
public class AnalyzerException extends Exception {
    private final String message;

    /**
     * Creates {@code AnalyzerException} object with specified error position
     * and message
     */
    public AnalyzerException(String source, int position) {
        val substring = source.substring(
            position - getStartOffsetIndex(source, position),
            position + getEndOffsetIndex(source, position)
        ).trim();
        message = String.format("You have an error in your syntax near '%s'", substring);
    }

    /**
     * Returns position of the start offset of the error message
     *
     * @param source   string to be analyzed
     * @param position the index from which to start the scanning
     * @return index of the start offset of the error message
     */
    private int getStartOffsetIndex(String source, int position) {
        var index = 0;
        var state = WordState.INITIAL;
        while ((position - index) != 0 && state.whitespaces != WordState.WORD_LIMIT && state != WordState.NEWLINE) {
            index++;
            val c = source.charAt(position - index);
            state = state.next(c);
        }
        WordState.reset();
        return index;
    }

    /**
     * Returns the position of the end offset of the error message
     *
     * @param source   string to be analyzed
     * @param position the index from which to start the scanning
     * @return index of the end offset of the error message
     */
    private int getEndOffsetIndex(String source, int position) {
        var index = 0;
        var state = WordState.INITIAL;
        while ((position + index) != source.length() - 1 && state.whitespaces != WordState.WORD_LIMIT && state != WordState.NEWLINE) {
            index++;
            val c = source.charAt(position + index);
            state = state.next(c);
        }
        WordState.reset();
        return index;
    }

    @Override
    public String getMessage() {
        return message;
    }

    /**
     * Simple enum based FSM
     */
    private enum WordState implements Closeable {
        INITIAL {
            @Override
            public void close() {
                whitespaces = 0;
            }

            @Override
            public WordState next(char c) {
                return c == '\n' ? NEWLINE : WORD_BOUND;
            }
        },

        /**
         * Represents the state when {@code c} is {@code ' '}
         * which means that word boundary is reached
         */
        WORD_BOUND {
            @Override
            public void close() {
                whitespaces = 0;
            }

            @Override
            public WordState next(char c) {
                val next = c != ' ' ? NO_BOUND : WORD_BOUND;
                return c == '\n' ? NEWLINE : next;
            }
        },

        /**
         * Represents the state when {@code c} is not {@code ' '}
         * which means that a character after {@code WORD_BOUND} is reached
         */
        NO_BOUND {
            @Override
            public void close() {
                whitespaces = 0;
            }

            @Override
            public WordState next(char c) {
                if (c == '\n') {
                    return NEWLINE;
                }
                if (c == ' ') {
                    whitespaces++;
                    return WORD_BOUND;
                }
                return NO_BOUND;
            }
        },

        /**
         * Represents the state when {@code c} is {@code '\n'}
         * which means newline character is reached
         */
        NEWLINE {
            @Override
            public void close() {
                whitespaces = 0;
            }

            @Override
            public WordState next(char c) {
                return null;
            }
        };

        public static final int WORD_LIMIT = 5;

        protected int whitespaces = 0;

        /**
         * Sets {@code whitespaces} to {@code 0} for all states
         */
        @SneakyThrows
        public static void reset() {
            for (var state : WordState.values()) {
                state.close();
            }
        }

        /**
         * Returns next state based on {@code c}
         *
         * @param c next character
         * @return next {@code WordState}
         */
        public abstract WordState next(char c);
    }
}
