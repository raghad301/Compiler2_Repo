// Generated from D:/CompilerProjectRepo/CompilerProject1_IT4-main/src/parser_pkg/pythonParser.g4 by ANTLR 4.13.2
package parser_pkg;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link pythonParser}.
 */
public interface pythonParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link pythonParser#program}.
	 * @param ctx the parse tree
	 */
	void enterProgram(pythonParser.ProgramContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#program}.
	 * @param ctx the parse tree
	 */
	void exitProgram(pythonParser.ProgramContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(pythonParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(pythonParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void enterSimple_stmt(pythonParser.Simple_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 */
	void exitSimple_stmt(pythonParser.Simple_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void enterCompound_stmt(pythonParser.Compound_stmtContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 */
	void exitCompound_stmt(pythonParser.Compound_stmtContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void enterImportStatement(pythonParser.ImportStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#importStatement}.
	 * @param ctx the parse tree
	 */
	void exitImportStatement(pythonParser.ImportStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDef(pythonParser.FunctionDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#functionDef}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDef(pythonParser.FunctionDefContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void enterDecorator(pythonParser.DecoratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#decorator}.
	 * @param ctx the parse tree
	 */
	void exitDecorator(pythonParser.DecoratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void enterParameters(pythonParser.ParametersContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#parameters}.
	 * @param ctx the parse tree
	 */
	void exitParameters(pythonParser.ParametersContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(pythonParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(pythonParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void enterAssignment(pythonParser.AssignmentContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#assignment}.
	 * @param ctx the parse tree
	 */
	void exitAssignment(pythonParser.AssignmentContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#target}.
	 * @param ctx the parse tree
	 */
	void enterTarget(pythonParser.TargetContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#target}.
	 * @param ctx the parse tree
	 */
	void exitTarget(pythonParser.TargetContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(pythonParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#expressionStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(pythonParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(pythonParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#returnStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(pythonParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#testList}.
	 * @param ctx the parse tree
	 */
	void enterTestList(pythonParser.TestListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#testList}.
	 * @param ctx the parse tree
	 */
	void exitTestList(pythonParser.TestListContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#elifPart}.
	 * @param ctx the parse tree
	 */
	void enterElifPart(pythonParser.ElifPartContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#elifPart}.
	 * @param ctx the parse tree
	 */
	void exitElifPart(pythonParser.ElifPartContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void enterElsePart(pythonParser.ElsePartContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#elsePart}.
	 * @param ctx the parse tree
	 */
	void exitElsePart(pythonParser.ElsePartContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(pythonParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#whileStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(pythonParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(pythonParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#forStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(pythonParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterExpression(pythonParser.ExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitExpression(pythonParser.ExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#logic_expr}.
	 * @param ctx the parse tree
	 */
	void enterLogic_expr(pythonParser.Logic_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#logic_expr}.
	 * @param ctx the parse tree
	 */
	void exitLogic_expr(pythonParser.Logic_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#comparison}.
	 * @param ctx the parse tree
	 */
	void enterComparison(pythonParser.ComparisonContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#comparison}.
	 * @param ctx the parse tree
	 */
	void exitComparison(pythonParser.ComparisonContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void enterArith_expr(pythonParser.Arith_exprContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#arith_expr}.
	 * @param ctx the parse tree
	 */
	void exitArith_expr(pythonParser.Arith_exprContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#term}.
	 * @param ctx the parse tree
	 */
	void enterTerm(pythonParser.TermContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#term}.
	 * @param ctx the parse tree
	 */
	void exitTerm(pythonParser.TermContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(pythonParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(pythonParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void enterTrailer(pythonParser.TrailerContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#trailer}.
	 * @param ctx the parse tree
	 */
	void exitTrailer(pythonParser.TrailerContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void enterArgList(pythonParser.ArgListContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#argList}.
	 * @param ctx the parse tree
	 */
	void exitArgList(pythonParser.ArgListContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#testList_comp}.
	 * @param ctx the parse tree
	 */
	void enterTestList_comp(pythonParser.TestList_compContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#testList_comp}.
	 * @param ctx the parse tree
	 */
	void exitTestList_comp(pythonParser.TestList_compContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void enterArgument(pythonParser.ArgumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#argument}.
	 * @param ctx the parse tree
	 */
	void exitArgument(pythonParser.ArgumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void enterAtom(pythonParser.AtomContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#atom}.
	 * @param ctx the parse tree
	 */
	void exitAtom(pythonParser.AtomContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void enterListLiteral(pythonParser.ListLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#listLiteral}.
	 * @param ctx the parse tree
	 */
	void exitListLiteral(pythonParser.ListLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void enterDictLiteral(pythonParser.DictLiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 */
	void exitDictLiteral(pythonParser.DictLiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void enterDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 */
	void exitDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void enterComp_for(pythonParser.Comp_forContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#comp_for}.
	 * @param ctx the parse tree
	 */
	void exitComp_for(pythonParser.Comp_forContext ctx);
	/**
	 * Enter a parse tree produced by {@link pythonParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void enterComp_iter(pythonParser.Comp_iterContext ctx);
	/**
	 * Exit a parse tree produced by {@link pythonParser#comp_iter}.
	 * @param ctx the parse tree
	 */
	void exitComp_iter(pythonParser.Comp_iterContext ctx);
}