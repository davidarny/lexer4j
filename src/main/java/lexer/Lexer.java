package lexer;

import lombok.Cleanup;
import lombok.SneakyThrows;
import lombok.val;

import javax.annotation.Nullable;
import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * The {@code Lexer} class represents lexical analyzer for subset of Java
 * language
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
        fillTokenMap();
        result = new ArrayList<>();
    }

    @SneakyThrows
    public static void main(String[] args) {
        val lexer = new Lexer();
        val input = new File(args[0]);
        @Cleanup val stream = new FileInputStream(input);
        val text = new String(stream.readAllBytes());
        val tokens = lexer.tokenize(text);
        for (var token : tokens) {
            System.out.println(token);
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
                position = token.getEndPosition();
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
        return result
            .stream()
            .filter(Token::isNotAuxiliary)
            .collect(Collectors.toList());
    }

    /**
     * Scans the source from the specific index and returns the first separated
     * token
     *
     * @param source source code to be scanned
     * @param from   the index from which to start the scanning
     * @return first separated token or {@code null} if no token was found
     */
    @Nullable
    private Token getNextToken(String source, int from) {
        if (from < 0 || from >= source.length()) {
            throw new IllegalArgumentException("Illegal index in the input stream");
        }
        for (val type : TokenType.values()) {
            val p = Pattern.compile(".{" + from + "}" + regex.get(type), Pattern.DOTALL);
            val m = p.matcher(source);
            if (m.matches()) {
                val literal = m.group(1);
                val line = getLineNumber(source, from);
                val position = getPositionInLine(source, line, from);
                return new Token(from + literal.length(), literal, type, line, position);
            }
        }
        return null;
    }

    /**
     * Splits the substring of source ranged from {@code 0} to {@code from}
     * and returns number of {@code \n} in that part
     *
     * @param source source code to be scanned
     * @param from   the index from which to start the scanning
     * @return number of {@code \n} matched
     */
    private int getLineNumber(String source, int from) {
        return source.substring(0, from).split("\n").length;
    }

    private int getPositionInLine(String source, int line, int from) {
        @Cleanup val scanner = new Scanner(source);
        var index = 0;
        var pos = from;
        while (scanner.hasNextLine() && index != line - 1) {
            val nextLine = scanner.nextLine();
            pos -= nextLine.length();
            index++;
        }
        return pos;
    }

    /**
     * Creates map from token types to its regular expressions
     */
    private void fillTokenMap() {
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
        regex.put(TokenType.SCIENTIFIC_LITERAL, "\\b([+\\-]?(?:0|[1-9]\\d*)(?:\\.\\d*)?(?:[eE][+\\-]?\\d+))\\b.*");
        regex.put(TokenType.OCTAL_LITERAL, "\\b(0[0-7]+)\\b.*");
        regex.put(TokenType.FLOAT_LITERAL, "\\b(\\d{1,9}\\.\\d{1,16})\\b.*");
        regex.put(TokenType.DOUBLE_LITERAL, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regex.put(TokenType.INT_LITERAL, "\\b(\\d{1,9})\\b.*");
        regex.put(TokenType.HEX_LITERAL, "\\b(0[xX][0-9a-fA-F]+)\\b.*");
        regex.put(TokenType.BINARY_LITERAL, "\\b(0[bB][01]+)\\b.*");
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
        regex.put(TokenType.ADDITION, "(\\+{1}).*");
        regex.put(TokenType.SUBTRACTION, "(\\-{1}).*");
        regex.put(TokenType.MULTIPLICATION, "(\\*).*");
        regex.put(TokenType.DIVISION, "(/).*");
        regex.put(TokenType.EQUALS, "(==).*");
        regex.put(TokenType.XOR, "(\\^).*");
        regex.put(TokenType.ASSIGNMENT, "(=).*");
        regex.put(TokenType.NOT_EQUALS, "(\\!=).*");
        regex.put(TokenType.MORE, "(>).*");
        regex.put(TokenType.LESS, "(<).*");
        regex.put(TokenType.IDENTIFIER, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
        regex.put(TokenType.OPENING_SQUARE_BRACE, "(\\[).*");
        regex.put(TokenType.CLOSING_SQUARE_BRACE, "(\\]).*");
        regex.put(TokenType.VAR, "\\b(var)\\b.*");
        regex.put(TokenType.FINAL, "\\b(final)\\b.*");
        regex.put(TokenType.STRING_LITERAL, "(\\\"([^\\\\\\\"]|\\\\.)*\\\").*");
        regex.put(TokenType.MULTILINE_STRING_LITERAL, "(\\\"\\\"\\\"([^\\\\\\\"]|\\\\.)*\\\"\\\"\\\").*");
        regex.put(TokenType.CHAR_LITERAL, "('(.{1}|\\\\n|\\\\t)').*");
    }
}
