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
SCIENTIFIC_CONSTANT '3.2e23' [L2:37]
SEMICOLON ';' [L2:43]
PRIVATE 'private' [L3:67]
DOUBLE 'double' [L3:75]
IDENTIFIER 'small' [L3:82]
EQUAL '=' [L3:88]
MINUS '-' [L3:90]
SCIENTIFIC_CONSTANT '4.70e+9' [L3:91]
SEMICOLON ';' [L3:98]
PUBLIC 'public' [L9:172]
VOID 'void' [L9:179]
IDENTIFIER 'main' [L9:184]
OPENING_BRACE '(' [L9:188]
IDENTIFIER 'String' [L9:189]
OPENING_SQUARE_BRACE '[' [L9:195]
CLOSING_SQUARE_BRACE ']' [L9:196]
IDENTIFIER 'args' [L9:198]
CLOSING_BRACE ')' [L9:202]
OPENING_CURLY_BRACE '{' [L9:204]
INT 'int' [L10:214]
IDENTIFIER 'size' [L10:218]
EQUAL '=' [L10:223]
INT_CONSTANT '3' [L10:225]
SEMICOLON ';' [L10:226]
INT 'int' [L11:236]
OPENING_SQUARE_BRACE '[' [L11:240]
IDENTIFIER 'size' [L11:241]
CLOSING_SQUARE_BRACE ']' [L11:245]
IDENTIFIER 'array' [L11:247]
EQUAL '=' [L11:253]
OPENING_CURLY_BRACE '{' [L11:255]
INT_CONSTANT '1' [L11:257]
COMMA ',' [L11:258]
INT_CONSTANT '2' [L11:260]
COMMA ',' [L11:261]
INT_CONSTANT '3' [L11:263]
CLOSING_CURLY_BRACE '}' [L11:265]
SEMICOLON ';' [L11:266]
INT 'int' [L12:276]
IDENTIFIER 'index' [L12:280]
EQUAL '=' [L12:286]
SCIENTIFIC_CONSTANT '0' [L12:288]
SEMICOLON ';' [L12:289]
WHILE 'while' [L13:299]
OPENING_BRACE '(' [L13:305]
IDENTIFIER 'index' [L13:306]
NOT_EQUAL '!=' [L13:312]
SCIENTIFIC_CONSTANT '0' [L13:315]
CLOSING_BRACE ')' [L13:316]
OPENING_CURLY_BRACE '{' [L13:318]
IDENTIFIER 'index' [L14:332]
EQUAL '=' [L14:338]
IDENTIFIER 'index' [L14:340]
MINUS '-' [L14:346]
INT_CONSTANT '1' [L14:348]
SEMICOLON ';' [L14:349]
IDENTIFIER 'System' [L15:363]
POINT '.' [L15:369]
IDENTIFIER 'out' [L15:370]
POINT '.' [L15:373]
IDENTIFIER 'println' [L15:374]
OPENING_BRACE '(' [L15:381]
IDENTIFIER 'array' [L15:382]
OPENING_SQUARE_BRACE '[' [L15:387]
IDENTIFIER 'index' [L15:388]
CLOSING_SQUARE_BRACE ']' [L15:393]
MULTIPLY '*' [L15:395]
IDENTIFIER 'big' [L15:397]
MULTIPLY '*' [L15:401]
IDENTIFIER 'small' [L15:403]
CLOSING_BRACE ')' [L15:408]
SEMICOLON ';' [L15:409]
CLOSING_CURLY_BRACE '}' [L16:419]
FOR 'for' [L17:429]
OPENING_BRACE '(' [L17:433]
IDENTIFIER 'var' [L17:434]
IDENTIFIER 'num' [L17:438]
COLON ':' [L17:442]
IDENTIFIER 'array' [L17:444]
CLOSING_BRACE ')' [L17:449]
OPENING_CURLY_BRACE '{' [L17:451]
IDENTIFIER 'System' [L18:465]
POINT '.' [L18:471]
IDENTIFIER 'out' [L18:472]
POINT '.' [L18:475]
IDENTIFIER 'println' [L18:476]
OPENING_BRACE '(' [L18:483]
IDENTIFIER 'num' [L18:484]
MULTIPLY '*' [L18:488]
IDENTIFIER 'big' [L18:490]
MULTIPLY '*' [L18:494]
IDENTIFIER 'small' [L18:496]
CLOSING_BRACE ')' [L18:501]
SEMICOLON ';' [L18:502]
CLOSING_CURLY_BRACE '}' [L19:512]
CLOSING_CURLY_BRACE '}' [L20:518]
CLOSING_CURLY_BRACE '}' [L20:520]
```
