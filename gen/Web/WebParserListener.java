// Generated from D:/CompilerProjectRepo/CompilerProject1_IT4-main/src/Web/WebParser.g4 by ANTLR 4.13.2
package Web;
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link WebParser}.
 */
public interface WebParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link WebParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void enterHtmlDocument(WebParser.HtmlDocumentContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#htmlDocument}.
	 * @param ctx the parse tree
	 */
	void exitHtmlDocument(WebParser.HtmlDocumentContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void enterHtmlContent(WebParser.HtmlContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#htmlContent}.
	 * @param ctx the parse tree
	 */
	void exitHtmlContent(WebParser.HtmlContentContext ctx);
	/**
	 * Enter a parse tree produced by the {@code normalHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterNormalHtmlElement(WebParser.NormalHtmlElementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code normalHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitNormalHtmlElement(WebParser.NormalHtmlElementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code selfClosingHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void enterSelfClosingHtmlElement(WebParser.SelfClosingHtmlElementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code selfClosingHtmlElement}
	 * labeled alternative in {@link WebParser#htmlElement}.
	 * @param ctx the parse tree
	 */
	void exitSelfClosingHtmlElement(WebParser.SelfClosingHtmlElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#styleElement}.
	 * @param ctx the parse tree
	 */
	void enterStyleElement(WebParser.StyleElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#styleElement}.
	 * @param ctx the parse tree
	 */
	void exitStyleElement(WebParser.StyleElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#scriptElement}.
	 * @param ctx the parse tree
	 */
	void enterScriptElement(WebParser.ScriptElementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#scriptElement}.
	 * @param ctx the parse tree
	 */
	void exitScriptElement(WebParser.ScriptElementContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttribute(WebParser.AttributeContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttribute(WebParser.AttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#attributeValue}.
	 * @param ctx the parse tree
	 */
	void enterAttributeValue(WebParser.AttributeValueContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#attributeValue}.
	 * @param ctx the parse tree
	 */
	void exitAttributeValue(WebParser.AttributeValueContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#attributeValueContent}.
	 * @param ctx the parse tree
	 */
	void enterAttributeValueContent(WebParser.AttributeValueContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#attributeValueContent}.
	 * @param ctx the parse tree
	 */
	void exitAttributeValueContent(WebParser.AttributeValueContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#styleContent}.
	 * @param ctx the parse tree
	 */
	void enterStyleContent(WebParser.StyleContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#styleContent}.
	 * @param ctx the parse tree
	 */
	void exitStyleContent(WebParser.StyleContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#scriptContent}.
	 * @param ctx the parse tree
	 */
	void enterScriptContent(WebParser.ScriptContentContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#scriptContent}.
	 * @param ctx the parse tree
	 */
	void exitScriptContent(WebParser.ScriptContentContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void enterJinjaExpression(WebParser.JinjaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#jinjaExpression}.
	 * @param ctx the parse tree
	 */
	void exitJinjaExpression(WebParser.JinjaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#jinjaStatement}.
	 * @param ctx the parse tree
	 */
	void enterJinjaStatement(WebParser.JinjaStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#jinjaStatement}.
	 * @param ctx the parse tree
	 */
	void exitJinjaStatement(WebParser.JinjaStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaIf(WebParser.JinjaIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaIf(WebParser.JinjaIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaElif}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaElif(WebParser.JinjaElifContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaElif}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaElif(WebParser.JinjaElifContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaElse}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaElse(WebParser.JinjaElseContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaElse}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaElse(WebParser.JinjaElseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaEndIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaEndIf(WebParser.JinjaEndIfContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaEndIf}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaEndIf(WebParser.JinjaEndIfContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaFor(WebParser.JinjaForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaFor(WebParser.JinjaForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaEndFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaEndFor(WebParser.JinjaEndForContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaEndFor}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaEndFor(WebParser.JinjaEndForContext ctx);
	/**
	 * Enter a parse tree produced by the {@code jinjaSet}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void enterJinjaSet(WebParser.JinjaSetContext ctx);
	/**
	 * Exit a parse tree produced by the {@code jinjaSet}
	 * labeled alternative in {@link WebParser#jinjaStatementBody}.
	 * @param ctx the parse tree
	 */
	void exitJinjaSet(WebParser.JinjaSetContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link WebParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalOrExpr(WebParser.LogicalOrExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalOrExpr}
	 * labeled alternative in {@link WebParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalOrExpr(WebParser.LogicalOrExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link WebParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void enterLogicalAndExpr(WebParser.LogicalAndExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code logicalAndExpr}
	 * labeled alternative in {@link WebParser#logicalAndExpression}.
	 * @param ctx the parse tree
	 */
	void exitLogicalAndExpr(WebParser.LogicalAndExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link WebParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void enterComparisonExpr(WebParser.ComparisonExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code comparisonExpr}
	 * labeled alternative in {@link WebParser#comparisonExpression}.
	 * @param ctx the parse tree
	 */
	void exitComparisonExpr(WebParser.ComparisonExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link WebParser#simpleExpression}.
	 * @param ctx the parse tree
	 */
	void enterAddSubExpr(WebParser.AddSubExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code addSubExpr}
	 * labeled alternative in {@link WebParser#simpleExpression}.
	 * @param ctx the parse tree
	 */
	void exitAddSubExpr(WebParser.AddSubExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link WebParser#term}.
	 * @param ctx the parse tree
	 */
	void enterMulDivExpr(WebParser.MulDivExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code mulDivExpr}
	 * labeled alternative in {@link WebParser#term}.
	 * @param ctx the parse tree
	 */
	void exitMulDivExpr(WebParser.MulDivExprContext ctx);
	/**
	 * Enter a parse tree produced by {@link WebParser#factor}.
	 * @param ctx the parse tree
	 */
	void enterFactor(WebParser.FactorContext ctx);
	/**
	 * Exit a parse tree produced by {@link WebParser#factor}.
	 * @param ctx the parse tree
	 */
	void exitFactor(WebParser.FactorContext ctx);
	/**
	 * Enter a parse tree produced by the {@code variableExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterVariableExpr(WebParser.VariableExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code variableExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitVariableExpr(WebParser.VariableExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterStringLiteral(WebParser.StringLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code stringLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitStringLiteral(WebParser.StringLiteralContext ctx);
	/**
	 * Enter a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterFilterExpr(WebParser.FilterExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code filterExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitFilterExpr(WebParser.FilterExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterParenExpr(WebParser.ParenExprContext ctx);
	/**
	 * Exit a parse tree produced by the {@code parenExpr}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitParenExpr(WebParser.ParenExprContext ctx);
	/**
	 * Enter a parse tree produced by the {@code numberLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void enterNumberLiteral(WebParser.NumberLiteralContext ctx);
	/**
	 * Exit a parse tree produced by the {@code numberLiteral}
	 * labeled alternative in {@link WebParser#primary}.
	 * @param ctx the parse tree
	 */
	void exitNumberLiteral(WebParser.NumberLiteralContext ctx);
}