parser grammar WebParser;

options { tokenVocab=WebLexer; }


htmlDocument
    : htmlContent EOF
    ;


htmlContent
    : htmlNode*
    ;

htmlNode
    : htmlElement
    | styleElement
    | scriptElement
    | jinjaIfBlock
    | jinjaForBlock
    | jinjaSetStatement
    | jinjaExpression
    | HTML_TEXT
    | HTML_COMMENT
    | DOCTYPE
    ;

htmlElement
    : TAG_OPEN TAG_NAME attribute* TAG_CLOSE htmlContent TAG_OPEN TAG_SLASH TAG_NAME TAG_CLOSE  #normalHtmlElement
    | TAG_OPEN (TAG_NAME | VOID_TAG_NAME) attribute* TAG_SLASH_CLOSE                            #selfClosingHtmlElement
    | TAG_OPEN VOID_TAG_NAME attribute* TAG_CLOSE                                                #voidHtmlElement
    ;


styleElement
    : STYLE_OPEN styleContent* STYLE_CLOSE
    ;


scriptElement
    : SCRIPT_OPEN scriptContent* SCRIPT_CLOSE
    ;

attribute
    : (TAG_NAME | JINJA_NAME) ( TAG_EQUALS attributeValue )?
    ;


attributeValue
    : attributeValueContent*
    ;


attributeValueContent
    : jinjaExpression
    | jinjaStatement
    | ATTVALUE_TEXT
    ;

styleContent
    : jinjaExpression
    | jinjaStatement
    | CSS_IDENT
    | CSS_NUMBER
    | CSS_STRING
    | CSS_LBRACE
    | CSS_RBRACE
    | CSS_COLON
    | CSS_SEMI
    | CSS_DOT
    | CSS_HASH
    | CSS_LPAREN
    | CSS_RPAREN
    | CSS_COMMA
    | CSS_AT
    | CSS_TILDE
    | PLUS
    | MINUS
    | STAR
    | SLASH
    | GT
    ;


scriptContent
    : jinjaExpression
    | jinjaStatement
    | SCRIPT_LBRACE
    | SCRIPT_RBRACE
    | SCRIPT_LT
    | SCRIPT_OTHER
    ;


jinjaExpression
    : JINJA_EXPR_START expression JINJA_EXPR_END
    ;

jinjaSetStatement
    : JINJA_STMT_START
      SETKW JINJA_NAME ASSIGN expression
      JINJA_STMT_END
    ;

jinjaIfBlock
    : JINJA_STMT_START
      IFKW condition=expression
      JINJA_STMT_END

      thenBody=htmlContent

      jinjaElifClause*
      jinjaElseClause?

      JINJA_STMT_START
      ENDIFKW
      JINJA_STMT_END
    ;

jinjaElifClause
    : JINJA_STMT_START
      ELIFKW condition=expression
      JINJA_STMT_END

      body=htmlContent
    ;

jinjaElseClause
    : JINJA_STMT_START
      ELSEKW
      JINJA_STMT_END

      body=htmlContent
    ;

jinjaForBlock
    : JINJA_STMT_START
      FORKW variable=JINJA_NAME IN iterable=expression
      JINJA_STMT_END

      body=htmlContent

      JINJA_STMT_START
      ENDFORKW
      JINJA_STMT_END
    ;

jinjaStatement
    : JINJA_STMT_START jinjaStatementBody JINJA_STMT_END
    ;


jinjaStatementBody
    : IFKW expression                    #jinjaIf
    | ELIFKW expression                  #jinjaElif
    | ELSEKW                             #jinjaElse
    | ENDIFKW                            #jinjaEndIf
    | FORKW JINJA_NAME IN expression     #jinjaFor
    | ENDFORKW                           #jinjaEndFor
    | SETKW JINJA_NAME ASSIGN expression #jinjaSet
    ;


expression
    : logicalAndExpression (ORKW logicalAndExpression)*  #logicalOrExpr
    ;


logicalAndExpression
    : comparisonExpression (ANDKW comparisonExpression)*  #logicalAndExpr
    ;


comparisonExpression
    : simpleExpression ( (EQ | NEQ | GT | LT | GTE | LTE | IN) simpleExpression )?  #comparisonExpr
    ;

simpleExpression
    : term ( (PLUS | MINUS) term )* #addSubExpr
    ;

term
    : factor ( (STAR | SLASH | DIV | MOD) factor )* #mulDivExpr
    ;

factor
    : (NOTKW | PLUS | MINUS)* primary
    ;

primary
    : JINJA_NUMBER                            #numberLiteral
    | JINJA_STRING                            #stringLiteral
    | JINJA_NAME (DOT JINJA_NAME | LBRACKET expression RBRACKET)*
      (LPAREN callArguments? RPAREN)?          #variableExpr
    | LPAREN expression RPAREN                #parenExpr
    | primary PIPE JINJA_NAME                 #filterExpr
    ;

callArguments
    : callArgument (COMMA callArgument)* COMMA?
    ;

callArgument
    : (JINJA_NAME ASSIGN)? expression
    ;
