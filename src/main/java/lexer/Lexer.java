package lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Pattern;

/**
 * The {@code Lexer} class represents lexical analyzer for subset of Java
 * language.
 */
public class Lexer {
    /**
     * Mapping from type of token to its regular expression
     */
    private Map<TokenType, String> regex;
    /**
     * List of tokens as they appear in the input source
     */
    private List<Token> result;

    /**
     * Initializes a newly created {@code Lexer} object
     */
    public Lexer() {
        regex = new TreeMap<>();
        initTokenMap();
        result = new ArrayList<>();
    }

    public static void main(String[] args) {
        var lexer = new Lexer();
        var input = new File("input.txt");
        try (var stream = new FileInputStream(input)) {
            var text = new String(stream.readAllBytes());
            var tokens = lexer.tokenize(text);
            for (var token : tokens) {
                System.out.println(token.toString());
            }
        } catch (AnalyzerException | IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * Performs the tokenization of the input source code.
     *
     * @param source string to be analyzed
     * @throws AnalyzerException if lexical error exists in the source
     */
    public List<Token> tokenize(String source) throws AnalyzerException {
        int position = 0;
        Token token;
        do {
            token = getNextToken(source, position);
            if (token != null) {
                position = token.getEnd();
                result.add(token);
            }
        } while (token != null && position != source.length());
        if (position != source.length()) {
            throw new AnalyzerException(source, position);
        }
        return getFilteredTokens();
    }



    /**
     * Returns a sequence of tokens without types {@code BlockComment},
     * {@code LineComment} , {@code NewLine}, {@code Tab}, {@code WhiteSpace}
     *
     * @return list of tokens
     */
    private List<Token> getFilteredTokens() {
        List<Token> filtered = new ArrayList<>();
        for (Token token : result) {
            if (token.isNotAuxiliary()) {
                filtered.add(token);
            }
        }
        return filtered;
    }

    /**
     * Scans the source from the specific index and returns the first separated
     * token
     *
     * @param source source code to be scanned
     * @param from   the index from which to start the scanning
     * @return first separated token or {@code null} if no token was found
     */
    private Token getNextToken(String source, int from) {
        if (from < 0 || from >= source.length()) {
            throw new IllegalArgumentException("Illegal index in the input stream");
        }
        for (var type : TokenType.values()) {
            var p = Pattern.compile(".{" + from + "}" + regex.get(type), Pattern.DOTALL);
            var m = p.matcher(source);
            if (m.matches()) {
                var lexeme = m.group(1);
                return new Token(from, from + lexeme.length(), lexeme, type);
            }
        }
        return null;
    }

    /**
     * Creates map from token types to its regular expressions
     */
    private void initTokenMap() {
        regex.put(TokenType.BLOCK_COMMENT, "(/\\*.*?\\*/).*");
        regex.put(TokenType.LINE_COMMENT, "(//(.*?)[\r$]?\n).*");
        regex.put(TokenType.WHITE_SPACE, "( ).*");
        regex.put(TokenType.OPENING_BRACE, "(\\().*");
        regex.put(TokenType.CLOSING_BRACE, "(\\)).*");
        regex.put(TokenType.SEMICOLON, "(;).*");
        regex.put(TokenType.COLON, "(:).*");
        regex.put(TokenType.COMMA, "(,).*");
        regex.put(TokenType.OPENING_CURLY_BRACE, "(\\{).*");
        regex.put(TokenType.CLOSING_CURLY_BRACE, "(\\}).*");
        regex.put(TokenType.SCIENTIFIC_CONSTANT, "\\b([+\\-]?0|[1-9]\\d*\\.\\d*?[eE][+\\-]?\\d+?)\\b.*");
        regex.put(TokenType.FLOAT_CONSTANT, "\\b(\\d{1,9}\\.\\d{1,16})\\b.*");
        regex.put(TokenType.DOUBLE_CONSTANT, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regex.put(TokenType.INT_CONSTANT, "\\b(\\d{1,9})\\b.*");
        regex.put(TokenType.VOID, "\\b(void)\\b.*");
        regex.put(TokenType.INT, "\\b(int)\\b.*");
        regex.put(TokenType.DOUBLE, "\\b(double)\\b.*");
        regex.put(TokenType.FLOAT, "\\b(float)\\b.*");
        regex.put(TokenType.TAB, "(\\t).*");
        regex.put(TokenType.NEW_LINE, "(\\n).*");
        regex.put(TokenType.PUBLIC, "\\b(public)\\b.*");
        regex.put(TokenType.PRIVATE, "\\b(private)\\b.*");
        regex.put(TokenType.FALSE, "\\b(false)\\b.*");
        regex.put(TokenType.TRUE, "\\b(true)\\b.*");
        regex.put(TokenType.NULL, "\\b(null)\\b.*");
        regex.put(TokenType.RETURN, "\\b(return)\\b.*");
        regex.put(TokenType.NEW, "\\b(new)\\b.*");
        regex.put(TokenType.CLASS, "\\b(class)\\b.*");
        regex.put(TokenType.IF, "\\b(if)\\b.*");
        regex.put(TokenType.ELSE, "\\b(else)\\b.*");
        regex.put(TokenType.WHILE, "\\b(while)\\b.*");
        regex.put(TokenType.FOR, "\\b(for)\\b.*");
        regex.put(TokenType.STATIC, "\\b(static)\\b.*");
        regex.put(TokenType.POINT, "(\\.).*");
        regex.put(TokenType.PLUS, "(\\+{1}).*");
        regex.put(TokenType.MINUS, "(\\-{1}).*");
        regex.put(TokenType.MULTIPLY, "(\\*).*");
        regex.put(TokenType.DIVIDE, "(/).*");
        regex.put(TokenType.EQUAL_EQUAL, "(==).*");
        regex.put(TokenType.EQUAL, "(=).*");
        regex.put(TokenType.NOT_EQUAL, "(\\!=).*");
        regex.put(TokenType.GREATER, "(>).*");
        regex.put(TokenType.LESS, "(<).*");
        regex.put(TokenType.IDENTIFIER, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
        regex.put(TokenType.OPENING_SQUARE_BRACE, "(\\[).*");
        regex.put(TokenType.CLOSING_SQUARE_BRACE, "(\\]).*");
        regex.put(TokenType.VAR, "\\b(var)\\b.*");
    }
}
