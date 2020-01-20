package lexer;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.regex.Matcher;
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
            throw new AnalyzerException("Lexical error at position # " + position, position);
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
            throw new IllegalArgumentException("Illegal index in the input stream!");
        }
        for (TokenType tokenType : TokenType.values()) {
            Pattern p = Pattern.compile(".{" + from + "}" + regex.get(tokenType), Pattern.DOTALL);
            Matcher m = p.matcher(source);
            if (m.matches()) {
                String lexeme = m.group(1);
                return new Token(from, from + lexeme.length(), lexeme, tokenType);
            }
        }
        return null;
    }

    /**
     * Creates map from token types to its regular expressions
     */
    private void initTokenMap() {
        regex.put(TokenType.BlockComment, "(/\\*.*?\\*/).*");
        regex.put(TokenType.LineComment, "(//(.*?)[\r$]?\n).*");
        regex.put(TokenType.WhiteSpace, "( ).*");
        regex.put(TokenType.OpenBrace, "(\\().*");
        regex.put(TokenType.CloseBrace, "(\\)).*");
        regex.put(TokenType.Semicolon, "(;).*");
        regex.put(TokenType.Comma, "(,).*");
        regex.put(TokenType.OpeningCurlyBrace, "(\\{).*");
        regex.put(TokenType.ClosingCurlyBrace, "(\\}).*");
        regex.put(TokenType.DoubleConstant, "\\b(\\d{1,9}\\.\\d{1,32})\\b.*");
        regex.put(TokenType.IntConstant, "\\b(\\d{1,9})\\b.*");
        regex.put(TokenType.Void, "\\b(void)\\b.*");
        regex.put(TokenType.Int, "\\b(int)\\b.*");
        regex.put(TokenType.Double, "\\b(int|double)\\b.*");
        regex.put(TokenType.Tab, "(\\t).*");
        regex.put(TokenType.NewLine, "(\\n).*");
        regex.put(TokenType.Public, "\\b(public)\\b.*");
        regex.put(TokenType.Private, "\\b(private)\\b.*");
        regex.put(TokenType.False, "\\b(false)\\b.*");
        regex.put(TokenType.True, "\\b(true)\\b.*");
        regex.put(TokenType.Null, "\\b(null)\\b.*");
        regex.put(TokenType.Return, "\\b(return)\\b.*");
        regex.put(TokenType.New, "\\b(new)\\b.*");
        regex.put(TokenType.Class, "\\b(class)\\b.*");
        regex.put(TokenType.If, "\\b(if)\\b.*");
        regex.put(TokenType.Else, "\\b(else)\\b.*");
        regex.put(TokenType.While, "\\b(while)\\b.*");
        regex.put(TokenType.Static, "\\b(static)\\b.*");
        regex.put(TokenType.Point, "(\\.).*");
        regex.put(TokenType.Plus, "(\\+{1}).*");
        regex.put(TokenType.Minus, "(\\-{1}).*");
        regex.put(TokenType.Multiply, "(\\*).*");
        regex.put(TokenType.Divide, "(/).*");
        regex.put(TokenType.EqualEqual, "(==).*");
        regex.put(TokenType.Equal, "(=).*");
        regex.put(TokenType.NotEqual, "(\\!=).*");
        regex.put(TokenType.Greater, "(>).*");
        regex.put(TokenType.Less, "(<).*");
        regex.put(TokenType.Identifier, "\\b([a-zA-Z]{1}[0-9a-zA-Z_]{0,31})\\b.*");
    }
}
