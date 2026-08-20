lexer grammar pythonLexer;

// Python indentation tokens are emitted manually by nextToken().
// Declaring them here avoids empty lexer rules that can match without input.
tokens { INDENT, DEDENT }

@members {

    private java.util.Queue<Token> tokens = new java.util.LinkedList<>();
    private java.util.Stack<Integer> indents = new java.util.Stack<>();
    private boolean reachedEof = false;
    private int bracketDepth = 0;
    private int lastTokenType = Token.INVALID_TYPE;

    private void init() {
        if (indents.isEmpty()) {
            indents.push(0);
        }
    }

    @Override
    public Token nextToken() {
        init();

        if (!tokens.isEmpty()) {
            Token queued = tokens.poll();
            lastTokenType = queued.getType();
            return queued;
        }

        Token token = super.nextToken();

         if (token.getType() == LP || token.getType() == LC || token.getType() == LB) {
                    bracketDepth++;
                } else if (token.getType() == RP || token.getType() == RC || token.getType() == RB) {
                    if (bracketDepth > 0) bracketDepth--;
                }

        if (token.getType() == EOF) {
            if (!reachedEof && lastTokenType != NEWLINE && lastTokenType != DEDENT) {
                tokens.offer(createToken(NEWLINE, "\n"));
            }
            while (indents.size() > 1) {
                indents.pop();
                tokens.offer(createToken(DEDENT, ""));
            }

            tokens.offer(token);
            reachedEof = true;
            Token queued = tokens.poll();
            lastTokenType = queued.getType();
            return queued;
        }

        if (token.getType() == NEWLINE) {

            if (bracketDepth > 0) {
                        return nextToken();
                    }

            int nextChar = _input.LA(1);
                if (nextChar == '\n' || nextChar == '\r') {
                    return nextToken();
                }

            String text = token.getText();

            int indent = 0;
            for (int i = text.length() - 1; i >= 0; i--) {
                char c = text.charAt(i);
                if (c == ' ') {
                    indent++;
                } else if (c == '\t') {
                    indent += 4;
                } else {
                    break;
                }
            }

            tokens.offer(token);

            int prevIndent = indents.peek();

            if (indent > prevIndent) {
                indents.push(indent);
                tokens.offer(createToken(INDENT, ""));
            } else if (indent < prevIndent) {
                while (indents.size() > 1 && indent < indents.peek()) {
                    indents.pop();
                    tokens.offer(createToken(DEDENT, ""));
                }
            }

            Token queued = tokens.poll();
            lastTokenType = queued.getType();
            return queued;
        }

        lastTokenType = token.getType();
        return token;
    }

    private Token createToken(int type, String text) {
        CommonToken token = new CommonToken(
                this._tokenFactorySourcePair,
                type,
                DEFAULT_TOKEN_CHANNEL,
                _tokenStartCharIndex,
                _tokenStartCharIndex
        );
        token.setText(text);
        return token;
    }
}

DEF: 'def';
IF: 'if';
ELSE: 'else';
ELIF: 'elif';
WHILE: 'while';
FOR: 'for';
IN: 'in';
PRINT: 'print';
RETURN: 'return';
AND: 'and';
OR: 'or';
NOT: 'not';
TRUE: 'True';
FALSE: 'False';
NONE: 'None';
IMPORT: 'import';
FROM: 'from';
AS: 'as';
IS: 'is';

PLUS: '+';
MINUS: '-';
MUL: '*';
AT: '@';
DIV: '/';
EQ: '=';
EQEQ: '==';
LT: '<';
GT: '>';
LE: '<=';
GE: '>=';
NOT_EQ : '!=' ;

LP: '(';
RP: ')';
LB: '{';
RB: '}';
LC: '[';
RC: ']';
COMMA: ',';
COLON: ':';
DOT: '.';

ID: [a-zA-Z_][a-zA-Z_0-9]*;

NUMBER: [0-9]+ (DOT [0-9]+)?;

STRING
    : '"'  ( ~["\\\r\n] | '\\' . )* '"'
    | '\'' ( ~['\\\r\n] | '\\' . )* '\''
    ;

WS
    : [ \t]+ -> skip
    ;

COMMENT: '#' ~[\r\n]* -> skip;

NEWLINE : '\r'? '\n' [ \t]* ;
