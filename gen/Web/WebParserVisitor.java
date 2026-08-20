// Generated from D:/Downloads/Telegram Desktop/Compiler2_Repo (2)/Compiler2_Repo/src/Web/WebParser.g4 by ANTLR 4.13.2
package Web;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link WebParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface WebParserVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link WebParser#htmlDocument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlDocument(WebParser.HtmlDocumentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#htmlContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlContent(WebParser.HtmlContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#htmlNode}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitHtmlNode(WebParser.HtmlNodeContext ctx);
	/**
	 * Visit a parse tree produced by the {@code normalHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNormalHtmlElement(WebParser.NormalHtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code selfClosingHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitSelfClosingHtmlElement(WebParser.SelfClosingHtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code voidHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVoidHtmlElement(WebParser.VoidHtmlElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#styleElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyleElement(WebParser.StyleElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#scriptElement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptElement(WebParser.ScriptElementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#attribute}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttribute(WebParser.AttributeContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#attributeValue}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeValue(WebParser.AttributeValueContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#attributeValueContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAttributeValueContent(WebParser.AttributeValueContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#styleContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStyleContent(WebParser.StyleContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#scriptContent}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitScriptContent(WebParser.ScriptContentContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaExpression(WebParser.JinjaExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaSetStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaSetStatement(WebParser.JinjaSetStatementContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaIfBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaIfBlock(WebParser.JinjaIfBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaElifClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaElifClause(WebParser.JinjaElifClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaElseClause}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaElseClause(WebParser.JinjaElseClauseContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaForBlock}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaForBlock(WebParser.JinjaForBlockContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#jinjaStatement}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaStatement(WebParser.JinjaStatementContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaIf(WebParser.JinjaIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaElif}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaElif(WebParser.JinjaElifContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaElse}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaElse(WebParser.JinjaElseContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaEndIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaEndIf(WebParser.JinjaEndIfContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaFor(WebParser.JinjaForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaEndFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaEndFor(WebParser.JinjaEndForContext ctx);
	/**
	 * Visit a parse tree produced by the {@code jinjaSet}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitJinjaSet(WebParser.JinjaSetContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link WebParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalOrExpr(WebParser.LogicalOrExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link WebParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitLogicalAndExpr(WebParser.LogicalAndExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link WebParser#comparisonExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitComparisonExpr(WebParser.ComparisonExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link WebParser#simpleExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAddSubExpr(WebParser.AddSubExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link WebParser#term}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMulDivExpr(WebParser.MulDivExprContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#factor}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFactor(WebParser.FactorContext ctx);
	/**
	 * Visit a parse tree produced by the {@code variableExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitVariableExpr(WebParser.VariableExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitStringLiteral(WebParser.StringLiteralContext ctx);
	/**
	 * Visit a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFilterExpr(WebParser.FilterExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitParenExpr(WebParser.ParenExprContext ctx);
	/**
	 * Visit a parse tree produced by the {@code numberLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumberLiteral(WebParser.NumberLiteralContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#callArguments}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallArguments(WebParser.CallArgumentsContext ctx);
	/**
	 * Visit a parse tree produced by {@link WebParser#callArgument}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitCallArgument(WebParser.CallArgumentContext ctx);
}