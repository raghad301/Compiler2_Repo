lexer grammar WebLexer;


tokens {
    PLUS, MINUS, STAR, SLASH, DIV, MOD,
    LPAREN, RPAREN, DOT, COMMA,
    EQ, NEQ, GT, LT, GTE, LTE,
    IFKW, ELIFKW, ELSEKW, ENDIFKW, FORKW, ENDFORKW, SETKW, IN,
    ANDKW, ORKW,
    ASSIGN,
    PIPE,
    TAG_EQUALS, ATTVALUE_TEXT,
    JINJA_EXPR_START, JINJA_STMT_START,
    JINJA_EXPR_END, JINJA_STMT_END,
    JINJA_NAME, JINJA_NUMBER, JINJA_STRING
}

HTML_COMMENT: ' ' -> skip;
STYLE_OPEN: '<style' .*? '>' -> pushMode(STYLE);
SCRIPT_OPEN: '<script' .*? '>' -> pushMode(SCRIPT);
JINJA_EXPR_START: '{{' -> pushMode(JEXPR);
JINJA_STMT_START: '{%' -> pushMode(JSTMT);
JINJA_COMMENT_START: '{#' -> pushMode(JCOMMENT), skip;
TAG_OPEN: '<' -> pushMode(TAG);
HTML_TEXT: ~[<{]+;


mode TAG;
TAG_CLOSE: '>' -> popMode;
TAG_SLASH_CLOSE: '/>' -> popMode;
TAG_SLASH: '/';
TAG_EQUALS_DQ: '=' [ \t\r\n]* '"' -> type(TAG_EQUALS), pushMode(ATTR_DQ);
TAG_EQUALS_SQ: '=' [ \t\r\n]* '\'' -> type(TAG_EQUALS), pushMode(ATTR_SQ);
TAG_NAME: [A-Za-z_:] [A-Za-z0-9_:\\-\\.]*;
TAG_WHITESPACE: [ \t\r\n]+ -> skip;


mode ATTR_DQ;
ATTR_DQ_JEXPR_START: '{{' -> pushMode(JEXPR), type(JINJA_EXPR_START);
ATTR_DQ_JSTMT_START: '{%' -> pushMode(JSTMT), type(JINJA_STMT_START);
ATTR_DQ_COMMENT_START: '{#' -> pushMode(JCOMMENT), skip;
ATTR_DQ_TEXT: ~[{\r\n"<]+ -> type(ATTVALUE_TEXT);
ATTR_DQ_CLOSE: '"' -> popMode, skip;


mode ATTR_SQ;
ATTR_SQ_JEXPR_START: '{{' -> pushMode(JEXPR), type(JINJA_EXPR_START);
ATTR_SQ_JSTMT_START: '{%' -> pushMode(JSTMT), type(JINJA_STMT_START);
ATTR_SQ_COMMENT_START: '{#' -> pushMode(JCOMMENT), skip;
ATTR_SQ_TEXT: ~[{\r\n'<]+ -> type(ATTVALUE_TEXT);
ATTR_SQ_CLOSE: '\'' -> popMode, skip;


mode STYLE;
STYLE_CLOSE: '</style>' -> popMode;
STYLE_WS: [ \t\r\n]+ -> skip;
STYLE_JINJA_EXPR_START: '{{' -> pushMode(JEXPR), type(JINJA_EXPR_START);
STYLE_JINJA_STMT_START: '{%' -> pushMode(JSTMT), type(JINJA_STMT_START);
STYLE_JINJA_COMMENT_START: '{#' -> pushMode(JCOMMENT), skip;
CSS_COMMENT: '/*' .*? '*/' -> skip;
CSS_LBRACE: '{'; CSS_RBRACE: '}'; CSS_COLON: ':'; CSS_SEMI: ';'; CSS_DOT: '.'; CSS_HASH: '#'; CSS_LPAREN: '('; CSS_RPAREN: ')'; CSS_COMMA: ','; CSS_AT: '@'; CSS_TILDE: '~';
STYLE_PLUS: '+' -> type(PLUS); STYLE_MINUS: '-' -> type(MINUS); STYLE_STAR: '*' -> type(STAR); STYLE_SLASH: '/' -> type(SLASH);
CSS_IDENT: [A-Za-z_-] [A-Za-z0-9_-]*;
CSS_NUMBER: [0-9]+ ('.' [0-9]+)?;
CSS_STRING: '"' ~["\r\n]* '"' | '\'' ~['\r\n]* '\'';


mode SCRIPT;
SCRIPT_JINJA_EXPR_START: '{{' -> pushMode(JEXPR), type(JINJA_EXPR_START);
SCRIPT_JINJA_STMT_START: '{%' -> pushMode(JSTMT), type(JINJA_STMT_START);
SCRIPT_JINJA_COMMENT_START: '{#' -> pushMode(JCOMMENT), skip;
SCRIPT_CLOSE: '</script>' -> popMode;
SCRIPT_COMMENT: '/*' .*? '*/' -> skip;
SCRIPT_LINE_COMMENT: '//' ~[\r\n]* -> skip;
SCRIPT_LBRACE: '{'; SCRIPT_RBRACE: '}'; SCRIPT_LT: '<';
SCRIPT_OTHER: ~[<{]+;


mode JEXPR;
JEXPR_END: '}}' -> popMode, type(JINJA_EXPR_END);
JEXPR_WS: [ \t\r\n]+ -> skip;

JINJA_NAME: [A-Za-z_][A-Za-z0-9_]*;
JINJA_NUMBER: [0-9]+ ('.' [0-9]+)?;
JINJA_STRING: '"' (~["\r\n])* '"' | '\'' (~['\r\n])* '\'';

EQ: '=='; NEQ: '!='; GTE: '>='; LTE: '<=';
GT: '>'; LT: '<';
DIV: '//';

LPAREN: '('; RPAREN: ')'; COMMA: ',';
PIPE: '|' ;
DOT: '.'; MOD: '%'; PLUS: '+'; MINUS: '-'; STAR: '*'; SLASH: '/';



mode JSTMT;

JSTMT_END: '%}' -> popMode, type(JINJA_STMT_END);
JSTMT_WS: [ \t\r\n]+ -> skip;


JSTMT_IF: 'if' -> type(IFKW); JSTMT_ELIF: 'elif' -> type(ELIFKW); JSTMT_ELSE: 'else' -> type(ELSEKW); JSTMT_ENDIF: 'endif' -> type(ENDIFKW); JSTMT_FOR: 'for' -> type(FORKW); JSTMT_ENDFOR: 'endfor' -> type(ENDFORKW); JSTMT_SET: 'set' -> type(SETKW); JSTMT_IN: 'in' -> type(IN);
JSTMT_AND: 'and' -> type(ANDKW);
JSTMT_OR: 'or' -> type(ORKW);


JSTMT_EQ: '==' -> type(EQ); JSTMT_NEQ: '!=' -> type(NEQ); JSTMT_GTE: '>=' -> type(GTE); JSTMT_LTE: '<=' -> type(LTE); JSTMT_GT: '>' -> type(GT); JSTMT_LT: '<' -> type(LT);

JSTMT_ASSIGN: '=' -> type(ASSIGN);


JSTMT_PLUS: '+' -> type(PLUS); JSTMT_MINUS: '-' -> type(MINUS); JSTMT_STAR: '*' -> type(STAR); JSTMT_SLASH: '/' -> type(SLASH); JSTMT_DIV: '//' -> type(DIV); JSTMT_MOD: '%' -> type(MOD);
JSTMT_LPAREN: '(' -> type(LPAREN); JSTMT_RPAREN: ')' -> type(RPAREN); JSTMT_DOT: '.' -> type(DOT); JSTMT_COMMA: ',' -> type(COMMA);
JSTMT_PIPE: '|' -> type(PIPE);


JSTMT_STRING
    : (   '"' (~["\r\n])* '"'
        | '\'' (~['\r\n])* '\''
      )
    -> type(JINJA_STRING)
    ;


JSTMT_NAME: [A-Za-z_][A-Za-z0-9_]* -> type(JINJA_NAME);
JSTMT_NUMBER: [0-9]+ ('.' [0-9]+)? -> type(JINJA_NUMBER);


mode JCOMMENT;
JCOMMENT_END: '#}' -> popMode, skip;
JCOMMENT_TEXT: . -> skip;