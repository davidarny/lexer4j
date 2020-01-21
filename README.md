# Lexer4J

![GitHub top language](https://img.shields.io/github/languages/top/DavidArutiunian/lexer4j.svg)
![GitHub code size in bytes](https://img.shields.io/github/languages/code-size/DavidArutiunian/lexer4j.svg)
![GitHub last commit](https://img.shields.io/github/last-commit/DavidArutiunian/lexer4j.svg)
[![TLOC](https://tokei.rs/b1/github/DavidArutiunian/lexer4j)](https://github.com/DavidArutiunian/lexer4j)


## Getting Started

### 📃 Create an input file with some code

You can find code example in `test/test.txt`

```bash
$ cat test/test.txt > input.txt
```

Or you can link the test file

Linux:
```bash
$ ln -s test/test.txt input.txt
```

Windows:
```cmd
$ mklink input.txt test/test.txt
```

### 🚀 Run lexer

Pass the file with code to lexer

```bash
$ java -jar lexer.jar input.txt
```

You should get something like this

```
CLASS 'class' [L1:0]
IDENTIFIER 'Foo' [L1:6]
OPENING_CURLY_BRACE '{' [L1:10]
PRIVATE 'private' [L2:16]
DOUBLE 'double' [L2:24]
IDENTIFIER 'big' [L2:31]
EQUAL '=' [L2:35]
SCIENTIFIC_CONSTANT '3.2e+23' [L2:37]
SEMICOLON ';' [L2:44]
LINE_COMMENT '// Some sort of big value' [L2:46]
PRIVATE 'private' [L3:76]
DOUBLE 'double' [L3:84]
IDENTIFIER 'small' [L3:91]
EQUAL '=' [L3:97]
MINUS '-' [L3:99]
SCIENTIFIC_CONSTANT '4.70e-9' [L3:100]
SEMICOLON ';' [L3:107]
LINE_COMMENT '// Some sort of small value' [L3:109]
BLOCK_COMMENT '/**
     * Multiline comment
     * Starts the program
     */' [L5:142]
PUBLIC 'public' [L9:209]
VOID 'void' [L9:216]
IDENTIFIER 'main' [L9:221]
OPENING_BRACE '(' [L9:225]
IDENTIFIER 'String' [L9:226]
OPENING_SQUARE_BRACE '[' [L9:232]
CLOSING_SQUARE_BRACE ']' [L9:233]
IDENTIFIER 'args' [L9:235]
CLOSING_BRACE ')' [L9:239]
OPENING_CURLY_BRACE '{' [L9:241]
INT 'int' [L10:251]
IDENTIFIER 'size' [L10:255]
EQUAL '=' [L10:260]
INT_CONSTANT '3' [L10:262]
SEMICOLON ';' [L10:263]
INT 'int' [L11:273]
OPENING_SQUARE_BRACE '[' [L11:277]
IDENTIFIER 'size' [L11:278]
CLOSING_SQUARE_BRACE ']' [L11:282]
IDENTIFIER 'array' [L11:284]
EQUAL '=' [L11:290]
OPENING_CURLY_BRACE '{' [L11:292]
INT_CONSTANT '1' [L11:294]
COMMA ',' [L11:295]
INT_CONSTANT '2' [L11:297]
COMMA ',' [L11:298]
INT_CONSTANT '3' [L11:300]
CLOSING_CURLY_BRACE '}' [L11:302]
SEMICOLON ';' [L11:303]
INT 'int' [L12:313]
IDENTIFIER 'index' [L12:317]
EQUAL '=' [L12:323]
INT_CONSTANT '0' [L12:325]
SEMICOLON ';' [L12:326]
FLOAT 'float' [L13:336]
IDENTIFIER 'mesh' [L13:342]
EQUAL '=' [L13:347]
DOUBLE_CONSTANT '2.73' [L13:349]
SEMICOLON ';' [L13:353]
WHILE 'while' [L14:363]
OPENING_BRACE '(' [L14:369]
IDENTIFIER 'index' [L14:370]
NOT_EQUAL '!=' [L14:376]
INT_CONSTANT '0' [L14:379]
CLOSING_BRACE ')' [L14:380]
OPENING_CURLY_BRACE '{' [L14:382]
IDENTIFIER 'index' [L15:396]
EQUAL '=' [L15:402]
IDENTIFIER 'index' [L15:404]
MINUS '-' [L15:410]
INT_CONSTANT '1' [L15:412]
SEMICOLON ';' [L15:413]
IDENTIFIER 'System' [L16:427]
POINT '.' [L16:433]
IDENTIFIER 'out' [L16:434]
POINT '.' [L16:437]
IDENTIFIER 'println' [L16:438]
OPENING_BRACE '(' [L16:445]
IDENTIFIER 'array' [L16:446]
OPENING_SQUARE_BRACE '[' [L16:451]
IDENTIFIER 'index' [L16:452]
CLOSING_SQUARE_BRACE ']' [L16:457]
MULTIPLY '*' [L16:459]
IDENTIFIER 'big' [L16:461]
MULTIPLY '*' [L16:465]
IDENTIFIER 'small' [L16:467]
CLOSING_BRACE ')' [L16:472]
SEMICOLON ';' [L16:473]
CLOSING_CURLY_BRACE '}' [L17:483]
FOR 'for' [L18:493]
OPENING_BRACE '(' [L18:497]
IDENTIFIER 'var' [L18:498]
IDENTIFIER 'num' [L18:502]
COLON ':' [L18:506]
IDENTIFIER 'array' [L18:508]
CLOSING_BRACE ')' [L18:513]
OPENING_CURLY_BRACE '{' [L18:515]
IDENTIFIER 'System' [L19:529]
POINT '.' [L19:535]
IDENTIFIER 'out' [L19:536]
POINT '.' [L19:539]
IDENTIFIER 'println' [L19:540]
OPENING_BRACE '(' [L19:547]
IDENTIFIER 'num' [L19:548]
MULTIPLY '*' [L19:552]
IDENTIFIER 'big' [L19:554]
MULTIPLY '*' [L19:558]
IDENTIFIER 'small' [L19:560]
CLOSING_BRACE ')' [L19:565]
SEMICOLON ';' [L19:566]
CLOSING_CURLY_BRACE '}' [L20:576]
CLOSING_CURLY_BRACE '}' [L21:582]
CLOSING_CURLY_BRACE '}' [L21:584]
```
