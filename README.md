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
PRIVATE 'private' [L3:76]
DOUBLE 'double' [L3:84]
IDENTIFIER 'small' [L3:91]
EQUAL '=' [L3:97]
MINUS '-' [L3:99]
SCIENTIFIC_CONSTANT '4.70e-9' [L3:100]
SEMICOLON ';' [L3:107]
PRIVATE 'private' [L5:142]
IDENTIFIER 'String' [L5:150]
IDENTIFIER 'message' [L5:157]
EQUAL '=' [L5:165]
STRING_LITERAL '"Foo >> "' [L5:167]
SEMICOLON ';' [L5:176]
PRIVATE 'private' [L6:182]
IDENTIFIER 'char' [L6:190]
IDENTIFIER 'newline' [L6:195]
EQUAL '=' [L6:203]
CHAR_LITERAL ''\n'' [L6:205]
SEMICOLON ';' [L6:209]
PUBLIC 'public' [L12:283]
VOID 'void' [L12:290]
IDENTIFIER 'main' [L12:295]
OPENING_BRACE '(' [L12:299]
IDENTIFIER 'String' [L12:300]
OPENING_SQUARE_BRACE '[' [L12:306]
CLOSING_SQUARE_BRACE ']' [L12:307]
IDENTIFIER 'args' [L12:309]
CLOSING_BRACE ')' [L12:313]
OPENING_CURLY_BRACE '{' [L12:315]
INT 'int' [L13:325]
IDENTIFIER 'size' [L13:329]
EQUAL '=' [L13:334]
INT_CONSTANT '3' [L13:336]
SEMICOLON ';' [L13:337]
INT 'int' [L14:347]
OPENING_SQUARE_BRACE '[' [L14:351]
IDENTIFIER 'size' [L14:352]
CLOSING_SQUARE_BRACE ']' [L14:356]
IDENTIFIER 'array' [L14:358]
EQUAL '=' [L14:364]
OPENING_CURLY_BRACE '{' [L14:366]
INT_CONSTANT '1' [L14:368]
COMMA ',' [L14:369]
INT_CONSTANT '2' [L14:371]
COMMA ',' [L14:372]
INT_CONSTANT '3' [L14:374]
CLOSING_CURLY_BRACE '}' [L14:376]
SEMICOLON ';' [L14:377]
INT 'int' [L15:387]
IDENTIFIER 'index' [L15:391]
EQUAL '=' [L15:397]
INT_CONSTANT '0' [L15:399]
SEMICOLON ';' [L15:400]
FLOAT 'float' [L16:410]
IDENTIFIER 'mesh' [L16:416]
EQUAL '=' [L16:421]
DOUBLE_CONSTANT '2.73' [L16:423]
SEMICOLON ';' [L16:427]
WHILE 'while' [L17:437]
OPENING_BRACE '(' [L17:443]
IDENTIFIER 'index' [L17:444]
NOT_EQUAL '!=' [L17:450]
INT_CONSTANT '0' [L17:453]
CLOSING_BRACE ')' [L17:454]
OPENING_CURLY_BRACE '{' [L17:456]
IDENTIFIER 'index' [L18:470]
EQUAL '=' [L18:476]
IDENTIFIER 'index' [L18:478]
MINUS '-' [L18:484]
INT_CONSTANT '1' [L18:486]
SEMICOLON ';' [L18:487]
IDENTIFIER 'System' [L19:501]
POINT '.' [L19:507]
IDENTIFIER 'out' [L19:508]
POINT '.' [L19:511]
IDENTIFIER 'println' [L19:512]
OPENING_BRACE '(' [L19:519]
IDENTIFIER 'message' [L19:520]
COMMA ',' [L19:527]
IDENTIFIER 'array' [L19:529]
OPENING_SQUARE_BRACE '[' [L19:534]
IDENTIFIER 'index' [L19:535]
CLOSING_SQUARE_BRACE ']' [L19:540]
MULTIPLY '*' [L19:542]
IDENTIFIER 'big' [L19:544]
MULTIPLY '*' [L19:548]
IDENTIFIER 'small' [L19:550]
COMMA ',' [L19:555]
IDENTIFIER 'newline' [L19:557]
CLOSING_BRACE ')' [L19:564]
SEMICOLON ';' [L19:565]
CLOSING_CURLY_BRACE '}' [L20:575]
FOR 'for' [L21:585]
OPENING_BRACE '(' [L21:589]
IDENTIFIER 'var' [L21:590]
IDENTIFIER 'num' [L21:594]
COLON ':' [L21:598]
IDENTIFIER 'array' [L21:600]
CLOSING_BRACE ')' [L21:605]
OPENING_CURLY_BRACE '{' [L21:607]
IDENTIFIER 'System' [L22:621]
POINT '.' [L22:627]
IDENTIFIER 'out' [L22:628]
POINT '.' [L22:631]
IDENTIFIER 'println' [L22:632]
OPENING_BRACE '(' [L22:639]
IDENTIFIER 'message' [L22:640]
COMMA ',' [L22:647]
IDENTIFIER 'num' [L22:649]
MULTIPLY '*' [L22:653]
IDENTIFIER 'big' [L22:655]
MULTIPLY '*' [L22:659]
IDENTIFIER 'small' [L22:661]
COMMA ',' [L22:666]
IDENTIFIER 'newline' [L22:668]
CLOSING_BRACE ')' [L22:675]
SEMICOLON ';' [L22:676]
CLOSING_CURLY_BRACE '}' [L23:686]
CLOSING_CURLY_BRACE '}' [L24:692]
CLOSING_CURLY_BRACE '}' [L24:694]
```
