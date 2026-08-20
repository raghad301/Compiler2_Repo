// Generated from D:/Downloads/Telegram Desktop/Compiler2_Repo (2)/Compiler2_Repo/src/parser_pkg/pythonParser.g4 by ANTLR 4.13.2
package parser_pkg;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link pythonParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface pythonParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link pythonParser#program}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitProgram(pythonParser.ProgramContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#statement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStatement(pythonParser.StatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code importSimple}
	 * labeled alternative in {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportSimple(pythonParser.ImportSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code assignmentSimple}
	 * labeled alternative in {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignmentSimple(pythonParser.AssignmentSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code returnSimple}
	 * labeled alternative in {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnSimple(pythonParser.ReturnSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code expressionSimple}
	 * labeled alternative in {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionSimple(pythonParser.ExpressionSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicExpressionSimple}
	 * labeled alternative in {@link pythonParser#simple_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicExpressionSimple(pythonParser.LogicExpressionSimpleContext ctx);
	/**
	 * Visit a parse tree produced by the {@code functionCompound}
	 * labeled alternative in {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionCompound(pythonParser.FunctionCompoundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code ifCompound}
	 * labeled alternative in {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfCompound(pythonParser.IfCompoundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code whileCompound}
	 * labeled alternative in {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileCompound(pythonParser.WhileCompoundContext ctx);
	/**
	 * Visit a parse tree produced by the {@code forCompound}
	 * labeled alternative in {@link pythonParser#compound_stmt}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForCompound(pythonParser.ForCompoundContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#importStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitImportStatement(pythonParser.ImportStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#functionDef}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunctionDef(pythonParser.FunctionDefContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#decorator}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDecorator(pythonParser.DecoratorContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#parameters}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParameters(pythonParser.ParametersContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#block}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitBlock(pythonParser.BlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#assignment}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAssignment(pythonParser.AssignmentContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#target}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTarget(pythonParser.TargetContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#expressionStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpressionStatement(pythonParser.ExpressionStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#returnStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitReturnStatement(pythonParser.ReturnStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#testList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestList(pythonParser.TestListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#ifStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitIfStatement(pythonParser.IfStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#elifPart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElifPart(pythonParser.ElifPartContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#elsePart}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitElsePart(pythonParser.ElsePartContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#whileStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitWhileStatement(pythonParser.WhileStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#forStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitForStatement(pythonParser.ForStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(pythonParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#logic_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogic_expr(pythonParser.Logic_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#comparison}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparison(pythonParser.ComparisonContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#arith_expr}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArith_expr(pythonParser.Arith_exprContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTerm(pythonParser.TermContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(pythonParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#trailer}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTrailer(pythonParser.TrailerContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#argList}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgList(pythonParser.ArgListContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#testList_comp}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitTestList_comp(pythonParser.TestList_compContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#argument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitArgument(pythonParser.ArgumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(pythonParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#listLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitListLiteral(pythonParser.ListLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#dictLiteral}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictLiteral(pythonParser.DictLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#dictEntry}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitDictEntry(pythonParser.DictEntryContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#comp_for}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_for(pythonParser.Comp_forContext ctx);
	/**
	 * Visit a parse tree produced by {@link pythonParser#comp_iter}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComp_iter(pythonParser.Comp_iterContext ctx);
}