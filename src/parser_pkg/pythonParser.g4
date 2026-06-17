parser grammar pythonParser;

options {
    tokenVocab = pythonLexer;
}

program
    : (NEWLINE | statement) * EOF
    ;

statement
    : compound_stmt
    | simple_stmt NEWLINE
    ;

simple_stmt:
      importStatement
    | assignment
    | returnStatement
    | expressionStatement
    | logic_expr
    ;

compound_stmt:
      functionDef
    | ifStatement
    | whileStatement
    | forStatement
    ;

importStatement:
      IMPORT ID (COMMA ID)* (AS ID)?
    | FROM ID (DOT ID)* IMPORT (ID (COMMA ID)* | MUL)
    ;

functionDef:
    decorator* (NEWLINE)* DEF ID LP parameters? RP COLON block
    ;


decorator:
    AT ID (DOT ID)* (LP (argList)? RP)? NEWLINE*
    ;


parameters:
      ID (COMMA ID)*
    ;

block
    : NEWLINE INDENT (statement | NEWLINE)* DEDENT
    ;

assignment:
      target EQ expression
    ;

target:
      ID (DOT ID | LC expression RC)*
    ;

expressionStatement:
      expression
    ;

returnStatement:
    RETURN (testList)?
    ;

testList:
    expression (COMMA expression)* (COMMA)?
    ;

ifStatement:
      IF expression COLON block (elifPart)* (elsePart)?
    ;

elifPart: ELIF expression COLON block;
elsePart: ELSE COLON block;

whileStatement: WHILE expression COLON block;
forStatement: FOR ID IN expression COLON block;

expression:
    logic_expr (IF logic_expr ELSE expression)?
    ;

logic_expr:
    (NOT)* comparison (( AND | OR | IS ) comparison)*
    ;

comparison:
    arith_expr ((EQEQ | NOT_EQ | LT | GT | LE | GE | IS | IN ) arith_expr)*
    ;

arith_expr:
    term ((PLUS | MINUS) term)*
    ;

term:
    factor ((MUL | DIV) factor)*
    ;

factor:
    (PLUS | MINUS | NOT)? atom trailer*
    ;

trailer:
      LP argList? RP
    | LC expression RC
    | DOT ID
    ;

argList:
    argument (COMMA (NEWLINE|INDENT|DEDENT)* argument)* (COMMA)?
    ;

testList_comp:
      expression comp_for
    | (expression ((COMMA | NEWLINE)+ expression)* (COMMA)?)
    ;

argument:
    (name=ID EQ)? value=expression
    ;

atom:
      ID
    | NUMBER
    | STRING
    | listLiteral
    | dictLiteral
    | LP testList_comp RP
    | TRUE | FALSE | NONE
    ;

listLiteral:
    LC (NEWLINE)* (testList_comp)? (NEWLINE)* RC
    ;

dictLiteral:
    LB (NEWLINE)* (dictEntry (COMMA (NEWLINE)* dictEntry)*)? (COMMA)? (NEWLINE)* RB
    ;

dictEntry:
    expression COLON expression
    ;

comp_for:
    FOR target IN expression (comp_iter)?
    ;

comp_iter:
    comp_for
    | IF expression (comp_iter)?
    ;

