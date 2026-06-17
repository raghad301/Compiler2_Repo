// Generated from D:/CompilerProjectRepo/CompilerProject1_IT4-main/src/Web/WebParser.g4 by ANTLR 4.13.2
package Web;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.misc.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;
import java.util.Iterator;
import java.util.ArrayList;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class WebParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		PLUS=1, MINUS=2, STAR=3, SLASH=4, DIV=5, MOD=6, LPAREN=7, RPAREN=8, DOT=9, 
		COMMA=10, EQ=11, NEQ=12, GT=13, LT=14, GTE=15, LTE=16, IFKW=17, ELIFKW=18, 
		ELSEKW=19, ENDIFKW=20, FORKW=21, ENDFORKW=22, SETKW=23, IN=24, ANDKW=25, 
		ORKW=26, ASSIGN=27, PIPE=28, TAG_EQUALS=29, ATTVALUE_TEXT=30, JINJA_EXPR_START=31, 
		JINJA_STMT_START=32, JINJA_EXPR_END=33, JINJA_STMT_END=34, JINJA_NAME=35, 
		JINJA_NUMBER=36, JINJA_STRING=37, HTML_COMMENT=38, STYLE_OPEN=39, SCRIPT_OPEN=40, 
		JINJA_COMMENT_START=41, TAG_OPEN=42, HTML_TEXT=43, TAG_CLOSE=44, TAG_SLASH_CLOSE=45, 
		TAG_SLASH=46, TAG_NAME=47, TAG_WHITESPACE=48, ATTR_DQ_COMMENT_START=49, 
		ATTR_DQ_CLOSE=50, ATTR_SQ_COMMENT_START=51, ATTR_SQ_CLOSE=52, STYLE_CLOSE=53, 
		STYLE_WS=54, STYLE_JINJA_COMMENT_START=55, CSS_COMMENT=56, CSS_LBRACE=57, 
		CSS_RBRACE=58, CSS_COLON=59, CSS_SEMI=60, CSS_DOT=61, CSS_HASH=62, CSS_LPAREN=63, 
		CSS_RPAREN=64, CSS_COMMA=65, CSS_AT=66, CSS_TILDE=67, CSS_IDENT=68, CSS_NUMBER=69, 
		CSS_STRING=70, SCRIPT_JINJA_COMMENT_START=71, SCRIPT_CLOSE=72, SCRIPT_COMMENT=73, 
		SCRIPT_LINE_COMMENT=74, SCRIPT_LBRACE=75, SCRIPT_RBRACE=76, SCRIPT_LT=77, 
		SCRIPT_OTHER=78, JEXPR_WS=79, JSTMT_WS=80, JCOMMENT_END=81, JCOMMENT_TEXT=82, 
		STYLE_PLUS=83, STYLE_MINUS=84, STYLE_STAR=85, JEXPR_END=86, JSTMT_END=87, 
		JSTMT_IF=88, JSTMT_ELIF=89, JSTMT_ELSE=90, JSTMT_ENDIF=91, JSTMT_FOR=92, 
		JSTMT_ENDFOR=93, JSTMT_SET=94, JSTMT_IN=95, JSTMT_AND=96, JSTMT_OR=97, 
		JSTMT_ASSIGN=98;
	public static final int
		RULE_htmlDocument = 0, RULE_htmlContent = 1, RULE_htmlElement = 2, RULE_styleElement = 3, 
		RULE_scriptElement = 4, RULE_attribute = 5, RULE_attributeValue = 6, RULE_attributeValueContent = 7, 
		RULE_styleContent = 8, RULE_scriptContent = 9, RULE_jinjaExpression = 10, 
		RULE_jinjaStatement = 11, RULE_jinjaStatementBody = 12, RULE_expression = 13, 
		RULE_logicalAndExpression = 14, RULE_comparisonExpression = 15, RULE_simpleExpression = 16, 
		RULE_term = 17, RULE_factor = 18, RULE_primary = 19;
	private static String[] makeRuleNames() {
		return new String[] {
			"htmlDocument", "htmlContent", "htmlElement", "styleElement", "scriptElement", 
			"attribute", "attributeValue", "attributeValueContent", "styleContent", 
			"scriptContent", "jinjaExpression", "jinjaStatement", "jinjaStatementBody", 
			"expression", "logicalAndExpression", "comparisonExpression", "simpleExpression", 
			"term", "factor", "primary"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, null, null, null, null, null, 
			null, null, null, null, null, null, null, "'{{'", "'{%'", null, null, 
			null, null, null, "' '", null, null, null, null, null, null, "'/>'", 
			null, null, null, null, "'\"'", null, "'''", "'</style>'", null, null, 
			null, null, null, "':'", "';'", null, "'#'", null, null, null, "'@'", 
			"'~'", null, null, null, null, "'</script>'", null, null, null, null, 
			null, null, null, null, "'#}'", null, null, null, null, "'}}'", "'%}'", 
			"'if'", "'elif'", "'else'", "'endif'", "'for'", "'endfor'", "'set'", 
			"'in'", "'and'", "'or'", "'='"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "PLUS", "MINUS", "STAR", "SLASH", "DIV", "MOD", "LPAREN", "RPAREN", 
			"DOT", "COMMA", "EQ", "NEQ", "GT", "LT", "GTE", "LTE", "IFKW", "ELIFKW", 
			"ELSEKW", "ENDIFKW", "FORKW", "ENDFORKW", "SETKW", "IN", "ANDKW", "ORKW", 
			"ASSIGN", "PIPE", "TAG_EQUALS", "ATTVALUE_TEXT", "JINJA_EXPR_START", 
			"JINJA_STMT_START", "JINJA_EXPR_END", "JINJA_STMT_END", "JINJA_NAME", 
			"JINJA_NUMBER", "JINJA_STRING", "HTML_COMMENT", "STYLE_OPEN", "SCRIPT_OPEN", 
			"JINJA_COMMENT_START", "TAG_OPEN", "HTML_TEXT", "TAG_CLOSE", "TAG_SLASH_CLOSE", 
			"TAG_SLASH", "TAG_NAME", "TAG_WHITESPACE", "ATTR_DQ_COMMENT_START", "ATTR_DQ_CLOSE", 
			"ATTR_SQ_COMMENT_START", "ATTR_SQ_CLOSE", "STYLE_CLOSE", "STYLE_WS", 
			"STYLE_JINJA_COMMENT_START", "CSS_COMMENT", "CSS_LBRACE", "CSS_RBRACE", 
			"CSS_COLON", "CSS_SEMI", "CSS_DOT", "CSS_HASH", "CSS_LPAREN", "CSS_RPAREN", 
			"CSS_COMMA", "CSS_AT", "CSS_TILDE", "CSS_IDENT", "CSS_NUMBER", "CSS_STRING", 
			"SCRIPT_JINJA_COMMENT_START", "SCRIPT_CLOSE", "SCRIPT_COMMENT", "SCRIPT_LINE_COMMENT", 
			"SCRIPT_LBRACE", "SCRIPT_RBRACE", "SCRIPT_LT", "SCRIPT_OTHER", "JEXPR_WS", 
			"JSTMT_WS", "JCOMMENT_END", "JCOMMENT_TEXT", "STYLE_PLUS", "STYLE_MINUS", 
			"STYLE_STAR", "JEXPR_END", "JSTMT_END", "JSTMT_IF", "JSTMT_ELIF", "JSTMT_ELSE", 
			"JSTMT_ENDIF", "JSTMT_FOR", "JSTMT_ENDFOR", "JSTMT_SET", "JSTMT_IN", 
			"JSTMT_AND", "JSTMT_OR", "JSTMT_ASSIGN"
		};
	}
	private static final String[] _SYMBOLIC_NAMES = makeSymbolicNames();
	public static final Vocabulary VOCABULARY = new VocabularyImpl(_LITERAL_NAMES, _SYMBOLIC_NAMES);

	/**
	 * @deprecated Use {@link #VOCABULARY} instead.
	 */
	@Deprecated
	public static final String[] tokenNames;
	static {
		tokenNames = new String[_SYMBOLIC_NAMES.length];
		for (int i = 0; i < tokenNames.length; i++) {
			tokenNames[i] = VOCABULARY.getLiteralName(i);
			if (tokenNames[i] == null) {
				tokenNames[i] = VOCABULARY.getSymbolicName(i);
			}

			if (tokenNames[i] == null) {
				tokenNames[i] = "<INVALID>";
			}
		}
	}

	@Override
	@Deprecated
	public String[] getTokenNames() {
		return tokenNames;
	}

	@Override

	public Vocabulary getVocabulary() {
		return VOCABULARY;
	}

	@Override
	public String getGrammarFileName() { return "WebParser.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public WebParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlDocumentContext extends ParserRuleContext {
		public HtmlContentContext htmlContent() {
			return getRuleContext(HtmlContentContext.class,0);
		}
		public TerminalNode EOF() { return getToken(WebParser.EOF, 0); }
		public HtmlDocumentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlDocument; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterHtmlDocument(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitHtmlDocument(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitHtmlDocument(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlDocumentContext htmlDocument() throws RecognitionException {
		HtmlDocumentContext _localctx = new HtmlDocumentContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_htmlDocument);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(40);
			htmlContent();
			setState(41);
			match(EOF);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlContentContext extends ParserRuleContext {
		public List<HtmlElementContext> htmlElement() {
			return getRuleContexts(HtmlElementContext.class);
		}
		public HtmlElementContext htmlElement(int i) {
			return getRuleContext(HtmlElementContext.class,i);
		}
		public List<StyleElementContext> styleElement() {
			return getRuleContexts(StyleElementContext.class);
		}
		public StyleElementContext styleElement(int i) {
			return getRuleContext(StyleElementContext.class,i);
		}
		public List<ScriptElementContext> scriptElement() {
			return getRuleContexts(ScriptElementContext.class);
		}
		public ScriptElementContext scriptElement(int i) {
			return getRuleContext(ScriptElementContext.class,i);
		}
		public List<JinjaExpressionContext> jinjaExpression() {
			return getRuleContexts(JinjaExpressionContext.class);
		}
		public JinjaExpressionContext jinjaExpression(int i) {
			return getRuleContext(JinjaExpressionContext.class,i);
		}
		public List<JinjaStatementContext> jinjaStatement() {
			return getRuleContexts(JinjaStatementContext.class);
		}
		public JinjaStatementContext jinjaStatement(int i) {
			return getRuleContext(JinjaStatementContext.class,i);
		}
		public List<TerminalNode> HTML_TEXT() { return getTokens(WebParser.HTML_TEXT); }
		public TerminalNode HTML_TEXT(int i) {
			return getToken(WebParser.HTML_TEXT, i);
		}
		public HtmlContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterHtmlContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitHtmlContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitHtmlContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlContentContext htmlContent() throws RecognitionException {
		HtmlContentContext _localctx = new HtmlContentContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_htmlContent);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(51);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					{
					setState(49);
					_errHandler.sync(this);
					switch (_input.LA(1)) {
					case TAG_OPEN:
						{
						setState(43);
						htmlElement();
						}
						break;
					case STYLE_OPEN:
						{
						setState(44);
						styleElement();
						}
						break;
					case SCRIPT_OPEN:
						{
						setState(45);
						scriptElement();
						}
						break;
					case JINJA_EXPR_START:
						{
						setState(46);
						jinjaExpression();
						}
						break;
					case JINJA_STMT_START:
						{
						setState(47);
						jinjaStatement();
						}
						break;
					case HTML_TEXT:
						{
						setState(48);
						match(HTML_TEXT);
						}
						break;
					default:
						throw new NoViableAltException(this);
					}
					} 
				}
				setState(53);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,1,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class HtmlElementContext extends ParserRuleContext {
		public HtmlElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_htmlElement; }
	 
		public HtmlElementContext() { }
		public void copyFrom(HtmlElementContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NormalHtmlElementContext extends HtmlElementContext {
		public List<TerminalNode> TAG_OPEN() { return getTokens(WebParser.TAG_OPEN); }
		public TerminalNode TAG_OPEN(int i) {
			return getToken(WebParser.TAG_OPEN, i);
		}
		public List<TerminalNode> TAG_NAME() { return getTokens(WebParser.TAG_NAME); }
		public TerminalNode TAG_NAME(int i) {
			return getToken(WebParser.TAG_NAME, i);
		}
		public List<TerminalNode> TAG_CLOSE() { return getTokens(WebParser.TAG_CLOSE); }
		public TerminalNode TAG_CLOSE(int i) {
			return getToken(WebParser.TAG_CLOSE, i);
		}
		public HtmlContentContext htmlContent() {
			return getRuleContext(HtmlContentContext.class,0);
		}
		public TerminalNode TAG_SLASH() { return getToken(WebParser.TAG_SLASH, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public NormalHtmlElementContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterNormalHtmlElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitNormalHtmlElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitNormalHtmlElement(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class SelfClosingHtmlElementContext extends HtmlElementContext {
		public TerminalNode TAG_OPEN() { return getToken(WebParser.TAG_OPEN, 0); }
		public TerminalNode TAG_NAME() { return getToken(WebParser.TAG_NAME, 0); }
		public TerminalNode TAG_SLASH_CLOSE() { return getToken(WebParser.TAG_SLASH_CLOSE, 0); }
		public List<AttributeContext> attribute() {
			return getRuleContexts(AttributeContext.class);
		}
		public AttributeContext attribute(int i) {
			return getRuleContext(AttributeContext.class,i);
		}
		public SelfClosingHtmlElementContext(HtmlElementContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterSelfClosingHtmlElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitSelfClosingHtmlElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitSelfClosingHtmlElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HtmlElementContext htmlElement() throws RecognitionException {
		HtmlElementContext _localctx = new HtmlElementContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_htmlElement);
		int _la;
		try {
			setState(78);
			_errHandler.sync(this);
			switch ( getInterpreter().adaptivePredict(_input,4,_ctx) ) {
			case 1:
				_localctx = new NormalHtmlElementContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(54);
				match(TAG_OPEN);
				setState(55);
				match(TAG_NAME);
				setState(59);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==TAG_NAME) {
					{
					{
					setState(56);
					attribute();
					}
					}
					setState(61);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(62);
				match(TAG_CLOSE);
				setState(63);
				htmlContent();
				setState(64);
				match(TAG_OPEN);
				setState(65);
				match(TAG_SLASH);
				setState(66);
				match(TAG_NAME);
				setState(67);
				match(TAG_CLOSE);
				}
				break;
			case 2:
				_localctx = new SelfClosingHtmlElementContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(69);
				match(TAG_OPEN);
				setState(70);
				match(TAG_NAME);
				setState(74);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==TAG_NAME) {
					{
					{
					setState(71);
					attribute();
					}
					}
					setState(76);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				setState(77);
				match(TAG_SLASH_CLOSE);
				}
				break;
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StyleElementContext extends ParserRuleContext {
		public TerminalNode STYLE_OPEN() { return getToken(WebParser.STYLE_OPEN, 0); }
		public TerminalNode STYLE_CLOSE() { return getToken(WebParser.STYLE_CLOSE, 0); }
		public List<StyleContentContext> styleContent() {
			return getRuleContexts(StyleContentContext.class);
		}
		public StyleContentContext styleContent(int i) {
			return getRuleContext(StyleContentContext.class,i);
		}
		public StyleElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_styleElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterStyleElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitStyleElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitStyleElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StyleElementContext styleElement() throws RecognitionException {
		StyleElementContext _localctx = new StyleElementContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_styleElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(80);
			match(STYLE_OPEN);
			setState(84);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & -144115181633396706L) != 0) || ((((_la - 64)) & ~0x3f) == 0 && ((1L << (_la - 64)) & 127L) != 0)) {
				{
				{
				setState(81);
				styleContent();
				}
				}
				setState(86);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(87);
			match(STYLE_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ScriptElementContext extends ParserRuleContext {
		public TerminalNode SCRIPT_OPEN() { return getToken(WebParser.SCRIPT_OPEN, 0); }
		public TerminalNode SCRIPT_CLOSE() { return getToken(WebParser.SCRIPT_CLOSE, 0); }
		public List<ScriptContentContext> scriptContent() {
			return getRuleContexts(ScriptContentContext.class);
		}
		public ScriptContentContext scriptContent(int i) {
			return getRuleContext(ScriptContentContext.class,i);
		}
		public ScriptElementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptElement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterScriptElement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitScriptElement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitScriptElement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptElementContext scriptElement() throws RecognitionException {
		ScriptElementContext _localctx = new ScriptElementContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_scriptElement);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(89);
			match(SCRIPT_OPEN);
			setState(93);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (((((_la - 31)) & ~0x3f) == 0 && ((1L << (_la - 31)) & 263882790666243L) != 0)) {
				{
				{
				setState(90);
				scriptContent();
				}
				}
				setState(95);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(96);
			match(SCRIPT_CLOSE);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeContext extends ParserRuleContext {
		public TerminalNode TAG_NAME() { return getToken(WebParser.TAG_NAME, 0); }
		public TerminalNode TAG_EQUALS() { return getToken(WebParser.TAG_EQUALS, 0); }
		public AttributeValueContext attributeValue() {
			return getRuleContext(AttributeValueContext.class,0);
		}
		public AttributeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attribute; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterAttribute(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitAttribute(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitAttribute(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeContext attribute() throws RecognitionException {
		AttributeContext _localctx = new AttributeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_attribute);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(98);
			match(TAG_NAME);
			setState(101);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==TAG_EQUALS) {
				{
				setState(99);
				match(TAG_EQUALS);
				setState(100);
				attributeValue();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeValueContext extends ParserRuleContext {
		public List<AttributeValueContentContext> attributeValueContent() {
			return getRuleContexts(AttributeValueContentContext.class);
		}
		public AttributeValueContentContext attributeValueContent(int i) {
			return getRuleContext(AttributeValueContentContext.class,i);
		}
		public AttributeValueContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeValue; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterAttributeValue(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitAttributeValue(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitAttributeValue(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeValueContext attributeValue() throws RecognitionException {
		AttributeValueContext _localctx = new AttributeValueContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_attributeValue);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(106);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 7516192768L) != 0)) {
				{
				{
				setState(103);
				attributeValueContent();
				}
				}
				setState(108);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class AttributeValueContentContext extends ParserRuleContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaStatementContext jinjaStatement() {
			return getRuleContext(JinjaStatementContext.class,0);
		}
		public TerminalNode ATTVALUE_TEXT() { return getToken(WebParser.ATTVALUE_TEXT, 0); }
		public AttributeValueContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_attributeValueContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterAttributeValueContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitAttributeValueContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitAttributeValueContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final AttributeValueContentContext attributeValueContent() throws RecognitionException {
		AttributeValueContentContext _localctx = new AttributeValueContentContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_attributeValueContent);
		try {
			setState(112);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_EXPR_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(109);
				jinjaExpression();
				}
				break;
			case JINJA_STMT_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(110);
				jinjaStatement();
				}
				break;
			case ATTVALUE_TEXT:
				enterOuterAlt(_localctx, 3);
				{
				setState(111);
				match(ATTVALUE_TEXT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class StyleContentContext extends ParserRuleContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaStatementContext jinjaStatement() {
			return getRuleContext(JinjaStatementContext.class,0);
		}
		public TerminalNode CSS_IDENT() { return getToken(WebParser.CSS_IDENT, 0); }
		public TerminalNode CSS_NUMBER() { return getToken(WebParser.CSS_NUMBER, 0); }
		public TerminalNode CSS_STRING() { return getToken(WebParser.CSS_STRING, 0); }
		public TerminalNode CSS_LBRACE() { return getToken(WebParser.CSS_LBRACE, 0); }
		public TerminalNode CSS_RBRACE() { return getToken(WebParser.CSS_RBRACE, 0); }
		public TerminalNode CSS_COLON() { return getToken(WebParser.CSS_COLON, 0); }
		public TerminalNode CSS_SEMI() { return getToken(WebParser.CSS_SEMI, 0); }
		public TerminalNode CSS_DOT() { return getToken(WebParser.CSS_DOT, 0); }
		public TerminalNode CSS_HASH() { return getToken(WebParser.CSS_HASH, 0); }
		public TerminalNode CSS_LPAREN() { return getToken(WebParser.CSS_LPAREN, 0); }
		public TerminalNode CSS_RPAREN() { return getToken(WebParser.CSS_RPAREN, 0); }
		public TerminalNode CSS_COMMA() { return getToken(WebParser.CSS_COMMA, 0); }
		public TerminalNode CSS_AT() { return getToken(WebParser.CSS_AT, 0); }
		public TerminalNode CSS_TILDE() { return getToken(WebParser.CSS_TILDE, 0); }
		public TerminalNode PLUS() { return getToken(WebParser.PLUS, 0); }
		public TerminalNode MINUS() { return getToken(WebParser.MINUS, 0); }
		public TerminalNode STAR() { return getToken(WebParser.STAR, 0); }
		public TerminalNode SLASH() { return getToken(WebParser.SLASH, 0); }
		public TerminalNode GT() { return getToken(WebParser.GT, 0); }
		public StyleContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_styleContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterStyleContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitStyleContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitStyleContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StyleContentContext styleContent() throws RecognitionException {
		StyleContentContext _localctx = new StyleContentContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_styleContent);
		try {
			setState(135);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_EXPR_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(114);
				jinjaExpression();
				}
				break;
			case JINJA_STMT_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(115);
				jinjaStatement();
				}
				break;
			case CSS_IDENT:
				enterOuterAlt(_localctx, 3);
				{
				setState(116);
				match(CSS_IDENT);
				}
				break;
			case CSS_NUMBER:
				enterOuterAlt(_localctx, 4);
				{
				setState(117);
				match(CSS_NUMBER);
				}
				break;
			case CSS_STRING:
				enterOuterAlt(_localctx, 5);
				{
				setState(118);
				match(CSS_STRING);
				}
				break;
			case CSS_LBRACE:
				enterOuterAlt(_localctx, 6);
				{
				setState(119);
				match(CSS_LBRACE);
				}
				break;
			case CSS_RBRACE:
				enterOuterAlt(_localctx, 7);
				{
				setState(120);
				match(CSS_RBRACE);
				}
				break;
			case CSS_COLON:
				enterOuterAlt(_localctx, 8);
				{
				setState(121);
				match(CSS_COLON);
				}
				break;
			case CSS_SEMI:
				enterOuterAlt(_localctx, 9);
				{
				setState(122);
				match(CSS_SEMI);
				}
				break;
			case CSS_DOT:
				enterOuterAlt(_localctx, 10);
				{
				setState(123);
				match(CSS_DOT);
				}
				break;
			case CSS_HASH:
				enterOuterAlt(_localctx, 11);
				{
				setState(124);
				match(CSS_HASH);
				}
				break;
			case CSS_LPAREN:
				enterOuterAlt(_localctx, 12);
				{
				setState(125);
				match(CSS_LPAREN);
				}
				break;
			case CSS_RPAREN:
				enterOuterAlt(_localctx, 13);
				{
				setState(126);
				match(CSS_RPAREN);
				}
				break;
			case CSS_COMMA:
				enterOuterAlt(_localctx, 14);
				{
				setState(127);
				match(CSS_COMMA);
				}
				break;
			case CSS_AT:
				enterOuterAlt(_localctx, 15);
				{
				setState(128);
				match(CSS_AT);
				}
				break;
			case CSS_TILDE:
				enterOuterAlt(_localctx, 16);
				{
				setState(129);
				match(CSS_TILDE);
				}
				break;
			case PLUS:
				enterOuterAlt(_localctx, 17);
				{
				setState(130);
				match(PLUS);
				}
				break;
			case MINUS:
				enterOuterAlt(_localctx, 18);
				{
				setState(131);
				match(MINUS);
				}
				break;
			case STAR:
				enterOuterAlt(_localctx, 19);
				{
				setState(132);
				match(STAR);
				}
				break;
			case SLASH:
				enterOuterAlt(_localctx, 20);
				{
				setState(133);
				match(SLASH);
				}
				break;
			case GT:
				enterOuterAlt(_localctx, 21);
				{
				setState(134);
				match(GT);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ScriptContentContext extends ParserRuleContext {
		public JinjaExpressionContext jinjaExpression() {
			return getRuleContext(JinjaExpressionContext.class,0);
		}
		public JinjaStatementContext jinjaStatement() {
			return getRuleContext(JinjaStatementContext.class,0);
		}
		public TerminalNode SCRIPT_LBRACE() { return getToken(WebParser.SCRIPT_LBRACE, 0); }
		public TerminalNode SCRIPT_RBRACE() { return getToken(WebParser.SCRIPT_RBRACE, 0); }
		public TerminalNode SCRIPT_LT() { return getToken(WebParser.SCRIPT_LT, 0); }
		public TerminalNode SCRIPT_OTHER() { return getToken(WebParser.SCRIPT_OTHER, 0); }
		public ScriptContentContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_scriptContent; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterScriptContent(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitScriptContent(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitScriptContent(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ScriptContentContext scriptContent() throws RecognitionException {
		ScriptContentContext _localctx = new ScriptContentContext(_ctx, getState());
		enterRule(_localctx, 18, RULE_scriptContent);
		try {
			setState(143);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_EXPR_START:
				enterOuterAlt(_localctx, 1);
				{
				setState(137);
				jinjaExpression();
				}
				break;
			case JINJA_STMT_START:
				enterOuterAlt(_localctx, 2);
				{
				setState(138);
				jinjaStatement();
				}
				break;
			case SCRIPT_LBRACE:
				enterOuterAlt(_localctx, 3);
				{
				setState(139);
				match(SCRIPT_LBRACE);
				}
				break;
			case SCRIPT_RBRACE:
				enterOuterAlt(_localctx, 4);
				{
				setState(140);
				match(SCRIPT_RBRACE);
				}
				break;
			case SCRIPT_LT:
				enterOuterAlt(_localctx, 5);
				{
				setState(141);
				match(SCRIPT_LT);
				}
				break;
			case SCRIPT_OTHER:
				enterOuterAlt(_localctx, 6);
				{
				setState(142);
				match(SCRIPT_OTHER);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaExpressionContext extends ParserRuleContext {
		public TerminalNode JINJA_EXPR_START() { return getToken(WebParser.JINJA_EXPR_START, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode JINJA_EXPR_END() { return getToken(WebParser.JINJA_EXPR_END, 0); }
		public JinjaExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaExpression; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaExpression(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaExpression(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaExpression(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaExpressionContext jinjaExpression() throws RecognitionException {
		JinjaExpressionContext _localctx = new JinjaExpressionContext(_ctx, getState());
		enterRule(_localctx, 20, RULE_jinjaExpression);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(145);
			match(JINJA_EXPR_START);
			setState(146);
			expression();
			setState(147);
			match(JINJA_EXPR_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaStatementContext extends ParserRuleContext {
		public TerminalNode JINJA_STMT_START() { return getToken(WebParser.JINJA_STMT_START, 0); }
		public JinjaStatementBodyContext jinjaStatementBody() {
			return getRuleContext(JinjaStatementBodyContext.class,0);
		}
		public TerminalNode JINJA_STMT_END() { return getToken(WebParser.JINJA_STMT_END, 0); }
		public JinjaStatementContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaStatement; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaStatement(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaStatement(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaStatement(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaStatementContext jinjaStatement() throws RecognitionException {
		JinjaStatementContext _localctx = new JinjaStatementContext(_ctx, getState());
		enterRule(_localctx, 22, RULE_jinjaStatement);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(149);
			match(JINJA_STMT_START);
			setState(150);
			jinjaStatementBody();
			setState(151);
			match(JINJA_STMT_END);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class JinjaStatementBodyContext extends ParserRuleContext {
		public JinjaStatementBodyContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_jinjaStatementBody; }
	 
		public JinjaStatementBodyContext() { }
		public void copyFrom(JinjaStatementBodyContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaForContext extends JinjaStatementBodyContext {
		public TerminalNode FORKW() { return getToken(WebParser.FORKW, 0); }
		public TerminalNode JINJA_NAME() { return getToken(WebParser.JINJA_NAME, 0); }
		public TerminalNode IN() { return getToken(WebParser.IN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JinjaForContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaIfContext extends JinjaStatementBodyContext {
		public TerminalNode IFKW() { return getToken(WebParser.IFKW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JinjaIfContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaElifContext extends JinjaStatementBodyContext {
		public TerminalNode ELIFKW() { return getToken(WebParser.ELIFKW, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JinjaElifContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaElif(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaElif(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaElif(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaEndIfContext extends JinjaStatementBodyContext {
		public TerminalNode ENDIFKW() { return getToken(WebParser.ENDIFKW, 0); }
		public JinjaEndIfContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaEndIf(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaEndIf(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaEndIf(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaElseContext extends JinjaStatementBodyContext {
		public TerminalNode ELSEKW() { return getToken(WebParser.ELSEKW, 0); }
		public JinjaElseContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaElse(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaElse(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaElse(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaEndForContext extends JinjaStatementBodyContext {
		public TerminalNode ENDFORKW() { return getToken(WebParser.ENDFORKW, 0); }
		public JinjaEndForContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaEndFor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaEndFor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaEndFor(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class JinjaSetContext extends JinjaStatementBodyContext {
		public TerminalNode SETKW() { return getToken(WebParser.SETKW, 0); }
		public TerminalNode JINJA_NAME() { return getToken(WebParser.JINJA_NAME, 0); }
		public TerminalNode ASSIGN() { return getToken(WebParser.ASSIGN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public JinjaSetContext(JinjaStatementBodyContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterJinjaSet(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitJinjaSet(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitJinjaSet(this);
			else return visitor.visitChildren(this);
		}
	}

	public final JinjaStatementBodyContext jinjaStatementBody() throws RecognitionException {
		JinjaStatementBodyContext _localctx = new JinjaStatementBodyContext(_ctx, getState());
		enterRule(_localctx, 24, RULE_jinjaStatementBody);
		try {
			setState(168);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case IFKW:
				_localctx = new JinjaIfContext(_localctx);
				enterOuterAlt(_localctx, 1);
				{
				setState(153);
				match(IFKW);
				setState(154);
				expression();
				}
				break;
			case ELIFKW:
				_localctx = new JinjaElifContext(_localctx);
				enterOuterAlt(_localctx, 2);
				{
				setState(155);
				match(ELIFKW);
				setState(156);
				expression();
				}
				break;
			case ELSEKW:
				_localctx = new JinjaElseContext(_localctx);
				enterOuterAlt(_localctx, 3);
				{
				setState(157);
				match(ELSEKW);
				}
				break;
			case ENDIFKW:
				_localctx = new JinjaEndIfContext(_localctx);
				enterOuterAlt(_localctx, 4);
				{
				setState(158);
				match(ENDIFKW);
				}
				break;
			case FORKW:
				_localctx = new JinjaForContext(_localctx);
				enterOuterAlt(_localctx, 5);
				{
				setState(159);
				match(FORKW);
				setState(160);
				match(JINJA_NAME);
				setState(161);
				match(IN);
				setState(162);
				expression();
				}
				break;
			case ENDFORKW:
				_localctx = new JinjaEndForContext(_localctx);
				enterOuterAlt(_localctx, 6);
				{
				setState(163);
				match(ENDFORKW);
				}
				break;
			case SETKW:
				_localctx = new JinjaSetContext(_localctx);
				enterOuterAlt(_localctx, 7);
				{
				setState(164);
				match(SETKW);
				setState(165);
				match(JINJA_NAME);
				setState(166);
				match(ASSIGN);
				setState(167);
				expression();
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ExpressionContext extends ParserRuleContext {
		public ExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_expression; }
	 
		public ExpressionContext() { }
		public void copyFrom(ExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalOrExprContext extends ExpressionContext {
		public List<LogicalAndExpressionContext> logicalAndExpression() {
			return getRuleContexts(LogicalAndExpressionContext.class);
		}
		public LogicalAndExpressionContext logicalAndExpression(int i) {
			return getRuleContext(LogicalAndExpressionContext.class,i);
		}
		public List<TerminalNode> ORKW() { return getTokens(WebParser.ORKW); }
		public TerminalNode ORKW(int i) {
			return getToken(WebParser.ORKW, i);
		}
		public LogicalOrExprContext(ExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterLogicalOrExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitLogicalOrExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitLogicalOrExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ExpressionContext expression() throws RecognitionException {
		ExpressionContext _localctx = new ExpressionContext(_ctx, getState());
		enterRule(_localctx, 26, RULE_expression);
		int _la;
		try {
			_localctx = new LogicalOrExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(170);
			logicalAndExpression();
			setState(175);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ORKW) {
				{
				{
				setState(171);
				match(ORKW);
				setState(172);
				logicalAndExpression();
				}
				}
				setState(177);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndExpressionContext extends ParserRuleContext {
		public LogicalAndExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_logicalAndExpression; }
	 
		public LogicalAndExpressionContext() { }
		public void copyFrom(LogicalAndExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class LogicalAndExprContext extends LogicalAndExpressionContext {
		public List<ComparisonExpressionContext> comparisonExpression() {
			return getRuleContexts(ComparisonExpressionContext.class);
		}
		public ComparisonExpressionContext comparisonExpression(int i) {
			return getRuleContext(ComparisonExpressionContext.class,i);
		}
		public List<TerminalNode> ANDKW() { return getTokens(WebParser.ANDKW); }
		public TerminalNode ANDKW(int i) {
			return getToken(WebParser.ANDKW, i);
		}
		public LogicalAndExprContext(LogicalAndExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterLogicalAndExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitLogicalAndExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitLogicalAndExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final LogicalAndExpressionContext logicalAndExpression() throws RecognitionException {
		LogicalAndExpressionContext _localctx = new LogicalAndExpressionContext(_ctx, getState());
		enterRule(_localctx, 28, RULE_logicalAndExpression);
		int _la;
		try {
			_localctx = new LogicalAndExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(178);
			comparisonExpression();
			setState(183);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==ANDKW) {
				{
				{
				setState(179);
				match(ANDKW);
				setState(180);
				comparisonExpression();
				}
				}
				setState(185);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExpressionContext extends ParserRuleContext {
		public ComparisonExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_comparisonExpression; }
	 
		public ComparisonExpressionContext() { }
		public void copyFrom(ComparisonExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ComparisonExprContext extends ComparisonExpressionContext {
		public List<SimpleExpressionContext> simpleExpression() {
			return getRuleContexts(SimpleExpressionContext.class);
		}
		public SimpleExpressionContext simpleExpression(int i) {
			return getRuleContext(SimpleExpressionContext.class,i);
		}
		public TerminalNode EQ() { return getToken(WebParser.EQ, 0); }
		public TerminalNode NEQ() { return getToken(WebParser.NEQ, 0); }
		public TerminalNode GT() { return getToken(WebParser.GT, 0); }
		public TerminalNode LT() { return getToken(WebParser.LT, 0); }
		public TerminalNode GTE() { return getToken(WebParser.GTE, 0); }
		public TerminalNode LTE() { return getToken(WebParser.LTE, 0); }
		public ComparisonExprContext(ComparisonExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterComparisonExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitComparisonExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitComparisonExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ComparisonExpressionContext comparisonExpression() throws RecognitionException {
		ComparisonExpressionContext _localctx = new ComparisonExpressionContext(_ctx, getState());
		enterRule(_localctx, 30, RULE_comparisonExpression);
		int _la;
		try {
			_localctx = new ComparisonExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(186);
			simpleExpression();
			setState(189);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if ((((_la) & ~0x3f) == 0 && ((1L << _la) & 129024L) != 0)) {
				{
				setState(187);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 129024L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(188);
				simpleExpression();
				}
			}

			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class SimpleExpressionContext extends ParserRuleContext {
		public SimpleExpressionContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_simpleExpression; }
	 
		public SimpleExpressionContext() { }
		public void copyFrom(SimpleExpressionContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class AddSubExprContext extends SimpleExpressionContext {
		public List<TermContext> term() {
			return getRuleContexts(TermContext.class);
		}
		public TermContext term(int i) {
			return getRuleContext(TermContext.class,i);
		}
		public List<TerminalNode> PLUS() { return getTokens(WebParser.PLUS); }
		public TerminalNode PLUS(int i) {
			return getToken(WebParser.PLUS, i);
		}
		public List<TerminalNode> MINUS() { return getTokens(WebParser.MINUS); }
		public TerminalNode MINUS(int i) {
			return getToken(WebParser.MINUS, i);
		}
		public AddSubExprContext(SimpleExpressionContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterAddSubExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitAddSubExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitAddSubExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final SimpleExpressionContext simpleExpression() throws RecognitionException {
		SimpleExpressionContext _localctx = new SimpleExpressionContext(_ctx, getState());
		enterRule(_localctx, 32, RULE_simpleExpression);
		int _la;
		try {
			_localctx = new AddSubExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(191);
			term();
			setState(196);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==PLUS || _la==MINUS) {
				{
				{
				setState(192);
				_la = _input.LA(1);
				if ( !(_la==PLUS || _la==MINUS) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(193);
				term();
				}
				}
				setState(198);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class TermContext extends ParserRuleContext {
		public TermContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_term; }
	 
		public TermContext() { }
		public void copyFrom(TermContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class MulDivExprContext extends TermContext {
		public List<FactorContext> factor() {
			return getRuleContexts(FactorContext.class);
		}
		public FactorContext factor(int i) {
			return getRuleContext(FactorContext.class,i);
		}
		public List<TerminalNode> STAR() { return getTokens(WebParser.STAR); }
		public TerminalNode STAR(int i) {
			return getToken(WebParser.STAR, i);
		}
		public List<TerminalNode> SLASH() { return getTokens(WebParser.SLASH); }
		public TerminalNode SLASH(int i) {
			return getToken(WebParser.SLASH, i);
		}
		public List<TerminalNode> DIV() { return getTokens(WebParser.DIV); }
		public TerminalNode DIV(int i) {
			return getToken(WebParser.DIV, i);
		}
		public List<TerminalNode> MOD() { return getTokens(WebParser.MOD); }
		public TerminalNode MOD(int i) {
			return getToken(WebParser.MOD, i);
		}
		public MulDivExprContext(TermContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterMulDivExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitMulDivExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitMulDivExpr(this);
			else return visitor.visitChildren(this);
		}
	}

	public final TermContext term() throws RecognitionException {
		TermContext _localctx = new TermContext(_ctx, getState());
		enterRule(_localctx, 34, RULE_term);
		int _la;
		try {
			_localctx = new MulDivExprContext(_localctx);
			enterOuterAlt(_localctx, 1);
			{
			setState(199);
			factor();
			setState(204);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while ((((_la) & ~0x3f) == 0 && ((1L << _la) & 120L) != 0)) {
				{
				{
				setState(200);
				_la = _input.LA(1);
				if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & 120L) != 0)) ) {
				_errHandler.recoverInline(this);
				}
				else {
					if ( _input.LA(1)==Token.EOF ) matchedEOF = true;
					_errHandler.reportMatch(this);
					consume();
				}
				setState(201);
				factor();
				}
				}
				setState(206);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class FactorContext extends ParserRuleContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public FactorContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_factor; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterFactor(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitFactor(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitFactor(this);
			else return visitor.visitChildren(this);
		}
	}

	public final FactorContext factor() throws RecognitionException {
		FactorContext _localctx = new FactorContext(_ctx, getState());
		enterRule(_localctx, 36, RULE_factor);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(207);
			primary(0);
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	@SuppressWarnings("CheckReturnValue")
	public static class PrimaryContext extends ParserRuleContext {
		public PrimaryContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_primary; }
	 
		public PrimaryContext() { }
		public void copyFrom(PrimaryContext ctx) {
			super.copyFrom(ctx);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class VariableExprContext extends PrimaryContext {
		public List<TerminalNode> JINJA_NAME() { return getTokens(WebParser.JINJA_NAME); }
		public TerminalNode JINJA_NAME(int i) {
			return getToken(WebParser.JINJA_NAME, i);
		}
		public List<TerminalNode> DOT() { return getTokens(WebParser.DOT); }
		public TerminalNode DOT(int i) {
			return getToken(WebParser.DOT, i);
		}
		public VariableExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterVariableExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitVariableExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitVariableExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class StringLiteralContext extends PrimaryContext {
		public TerminalNode JINJA_STRING() { return getToken(WebParser.JINJA_STRING, 0); }
		public StringLiteralContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterStringLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitStringLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitStringLiteral(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class FilterExprContext extends PrimaryContext {
		public PrimaryContext primary() {
			return getRuleContext(PrimaryContext.class,0);
		}
		public TerminalNode PIPE() { return getToken(WebParser.PIPE, 0); }
		public TerminalNode JINJA_NAME() { return getToken(WebParser.JINJA_NAME, 0); }
		public FilterExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterFilterExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitFilterExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitFilterExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class ParenExprContext extends PrimaryContext {
		public TerminalNode LPAREN() { return getToken(WebParser.LPAREN, 0); }
		public ExpressionContext expression() {
			return getRuleContext(ExpressionContext.class,0);
		}
		public TerminalNode RPAREN() { return getToken(WebParser.RPAREN, 0); }
		public ParenExprContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterParenExpr(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitParenExpr(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitParenExpr(this);
			else return visitor.visitChildren(this);
		}
	}
	@SuppressWarnings("CheckReturnValue")
	public static class NumberLiteralContext extends PrimaryContext {
		public TerminalNode JINJA_NUMBER() { return getToken(WebParser.JINJA_NUMBER, 0); }
		public NumberLiteralContext(PrimaryContext ctx) { copyFrom(ctx); }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).enterNumberLiteral(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof WebParserListener ) ((WebParserListener)listener).exitNumberLiteral(this);
		}
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof WebParserVisitor ) return ((WebParserVisitor<? extends T>)visitor).visitNumberLiteral(this);
			else return visitor.visitChildren(this);
		}
	}

	public final PrimaryContext primary() throws RecognitionException {
		return primary(0);
	}

	private PrimaryContext primary(int _p) throws RecognitionException {
		ParserRuleContext _parentctx = _ctx;
		int _parentState = getState();
		PrimaryContext _localctx = new PrimaryContext(_ctx, _parentState);
		PrimaryContext _prevctx = _localctx;
		int _startState = 38;
		enterRecursionRule(_localctx, 38, RULE_primary, _p);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(224);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case JINJA_NUMBER:
				{
				_localctx = new NumberLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;

				setState(210);
				match(JINJA_NUMBER);
				}
				break;
			case JINJA_STRING:
				{
				_localctx = new StringLiteralContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(211);
				match(JINJA_STRING);
				}
				break;
			case JINJA_NAME:
				{
				_localctx = new VariableExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(212);
				match(JINJA_NAME);
				setState(217);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
					if ( _alt==1 ) {
						{
						{
						setState(213);
						match(DOT);
						setState(214);
						match(JINJA_NAME);
						}
						} 
					}
					setState(219);
					_errHandler.sync(this);
					_alt = getInterpreter().adaptivePredict(_input,18,_ctx);
				}
				}
				break;
			case LPAREN:
				{
				_localctx = new ParenExprContext(_localctx);
				_ctx = _localctx;
				_prevctx = _localctx;
				setState(220);
				match(LPAREN);
				setState(221);
				expression();
				setState(222);
				match(RPAREN);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			_ctx.stop = _input.LT(-1);
			setState(231);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			while ( _alt!=2 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1 ) {
					if ( _parseListeners!=null ) triggerExitRuleEvent();
					_prevctx = _localctx;
					{
					{
					_localctx = new FilterExprContext(new PrimaryContext(_parentctx, _parentState));
					pushNewRecursionContext(_localctx, _startState, RULE_primary);
					setState(226);
					if (!(precpred(_ctx, 1))) throw new FailedPredicateException(this, "precpred(_ctx, 1)");
					setState(227);
					match(PIPE);
					setState(228);
					match(JINJA_NAME);
					}
					} 
				}
				setState(233);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,20,_ctx);
			}
			}
		}
		catch (RecognitionException re) {
			_localctx.exception = re;
			_errHandler.reportError(this, re);
			_errHandler.recover(this, re);
		}
		finally {
			unrollRecursionContexts(_parentctx);
		}
		return _localctx;
	}

	public boolean sempred(RuleContext _localctx, int ruleIndex, int predIndex) {
		switch (ruleIndex) {
		case 19:
			return primary_sempred((PrimaryContext)_localctx, predIndex);
		}
		return true;
	}
	private boolean primary_sempred(PrimaryContext _localctx, int predIndex) {
		switch (predIndex) {
		case 0:
			return precpred(_ctx, 1);
		}
		return true;
	}

	public static final String _serializedATN =
		"\u0004\u0001b\u00eb\u0002\u0000\u0007\u0000\u0002\u0001\u0007\u0001\u0002"+
		"\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004\u0007\u0004\u0002"+
		"\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007\u0007\u0007\u0002"+
		"\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b\u0007\u000b\u0002"+
		"\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002\u000f\u0007\u000f"+
		"\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002\u0012\u0007\u0012"+
		"\u0002\u0013\u0007\u0013\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001"+
		"\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0001\u0005\u0001"+
		"2\b\u0001\n\u0001\f\u00015\t\u0001\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0005\u0002:\b\u0002\n\u0002\f\u0002=\t\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002"+
		"\u0001\u0002\u0001\u0002\u0005\u0002I\b\u0002\n\u0002\f\u0002L\t\u0002"+
		"\u0001\u0002\u0003\u0002O\b\u0002\u0001\u0003\u0001\u0003\u0005\u0003"+
		"S\b\u0003\n\u0003\f\u0003V\t\u0003\u0001\u0003\u0001\u0003\u0001\u0004"+
		"\u0001\u0004\u0005\u0004\\\b\u0004\n\u0004\f\u0004_\t\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0005\u0001\u0005\u0001\u0005\u0003\u0005f\b\u0005"+
		"\u0001\u0006\u0005\u0006i\b\u0006\n\u0006\f\u0006l\t\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0003\u0007q\b\u0007\u0001\b\u0001\b\u0001\b"+
		"\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001"+
		"\b\u0003\b\u0088\b\b\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0001\t\u0003"+
		"\t\u0090\b\t\u0001\n\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001"+
		"\u000b\u0001\u000b\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001"+
		"\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0003"+
		"\f\u00a9\b\f\u0001\r\u0001\r\u0001\r\u0005\r\u00ae\b\r\n\r\f\r\u00b1\t"+
		"\r\u0001\u000e\u0001\u000e\u0001\u000e\u0005\u000e\u00b6\b\u000e\n\u000e"+
		"\f\u000e\u00b9\t\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0003\u000f"+
		"\u00be\b\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0005\u0010\u00c3\b"+
		"\u0010\n\u0010\f\u0010\u00c6\t\u0010\u0001\u0011\u0001\u0011\u0001\u0011"+
		"\u0005\u0011\u00cb\b\u0011\n\u0011\f\u0011\u00ce\t\u0011\u0001\u0012\u0001"+
		"\u0012\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001\u0013\u0001"+
		"\u0013\u0005\u0013\u00d8\b\u0013\n\u0013\f\u0013\u00db\t\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0001\u0013\u0003\u0013\u00e1\b\u0013\u0001\u0013"+
		"\u0001\u0013\u0001\u0013\u0005\u0013\u00e6\b\u0013\n\u0013\f\u0013\u00e9"+
		"\t\u0013\u0001\u0013\u0000\u0001&\u0014\u0000\u0002\u0004\u0006\b\n\f"+
		"\u000e\u0010\u0012\u0014\u0016\u0018\u001a\u001c\u001e \"$&\u0000\u0003"+
		"\u0001\u0000\u000b\u0010\u0001\u0000\u0001\u0002\u0001\u0000\u0003\u0006"+
		"\u010e\u0000(\u0001\u0000\u0000\u0000\u00023\u0001\u0000\u0000\u0000\u0004"+
		"N\u0001\u0000\u0000\u0000\u0006P\u0001\u0000\u0000\u0000\bY\u0001\u0000"+
		"\u0000\u0000\nb\u0001\u0000\u0000\u0000\fj\u0001\u0000\u0000\u0000\u000e"+
		"p\u0001\u0000\u0000\u0000\u0010\u0087\u0001\u0000\u0000\u0000\u0012\u008f"+
		"\u0001\u0000\u0000\u0000\u0014\u0091\u0001\u0000\u0000\u0000\u0016\u0095"+
		"\u0001\u0000\u0000\u0000\u0018\u00a8\u0001\u0000\u0000\u0000\u001a\u00aa"+
		"\u0001\u0000\u0000\u0000\u001c\u00b2\u0001\u0000\u0000\u0000\u001e\u00ba"+
		"\u0001\u0000\u0000\u0000 \u00bf\u0001\u0000\u0000\u0000\"\u00c7\u0001"+
		"\u0000\u0000\u0000$\u00cf\u0001\u0000\u0000\u0000&\u00e0\u0001\u0000\u0000"+
		"\u0000()\u0003\u0002\u0001\u0000)*\u0005\u0000\u0000\u0001*\u0001\u0001"+
		"\u0000\u0000\u0000+2\u0003\u0004\u0002\u0000,2\u0003\u0006\u0003\u0000"+
		"-2\u0003\b\u0004\u0000.2\u0003\u0014\n\u0000/2\u0003\u0016\u000b\u0000"+
		"02\u0005+\u0000\u00001+\u0001\u0000\u0000\u00001,\u0001\u0000\u0000\u0000"+
		"1-\u0001\u0000\u0000\u00001.\u0001\u0000\u0000\u00001/\u0001\u0000\u0000"+
		"\u000010\u0001\u0000\u0000\u000025\u0001\u0000\u0000\u000031\u0001\u0000"+
		"\u0000\u000034\u0001\u0000\u0000\u00004\u0003\u0001\u0000\u0000\u0000"+
		"53\u0001\u0000\u0000\u000067\u0005*\u0000\u00007;\u0005/\u0000\u00008"+
		":\u0003\n\u0005\u000098\u0001\u0000\u0000\u0000:=\u0001\u0000\u0000\u0000"+
		";9\u0001\u0000\u0000\u0000;<\u0001\u0000\u0000\u0000<>\u0001\u0000\u0000"+
		"\u0000=;\u0001\u0000\u0000\u0000>?\u0005,\u0000\u0000?@\u0003\u0002\u0001"+
		"\u0000@A\u0005*\u0000\u0000AB\u0005.\u0000\u0000BC\u0005/\u0000\u0000"+
		"CD\u0005,\u0000\u0000DO\u0001\u0000\u0000\u0000EF\u0005*\u0000\u0000F"+
		"J\u0005/\u0000\u0000GI\u0003\n\u0005\u0000HG\u0001\u0000\u0000\u0000I"+
		"L\u0001\u0000\u0000\u0000JH\u0001\u0000\u0000\u0000JK\u0001\u0000\u0000"+
		"\u0000KM\u0001\u0000\u0000\u0000LJ\u0001\u0000\u0000\u0000MO\u0005-\u0000"+
		"\u0000N6\u0001\u0000\u0000\u0000NE\u0001\u0000\u0000\u0000O\u0005\u0001"+
		"\u0000\u0000\u0000PT\u0005\'\u0000\u0000QS\u0003\u0010\b\u0000RQ\u0001"+
		"\u0000\u0000\u0000SV\u0001\u0000\u0000\u0000TR\u0001\u0000\u0000\u0000"+
		"TU\u0001\u0000\u0000\u0000UW\u0001\u0000\u0000\u0000VT\u0001\u0000\u0000"+
		"\u0000WX\u00055\u0000\u0000X\u0007\u0001\u0000\u0000\u0000Y]\u0005(\u0000"+
		"\u0000Z\\\u0003\u0012\t\u0000[Z\u0001\u0000\u0000\u0000\\_\u0001\u0000"+
		"\u0000\u0000][\u0001\u0000\u0000\u0000]^\u0001\u0000\u0000\u0000^`\u0001"+
		"\u0000\u0000\u0000_]\u0001\u0000\u0000\u0000`a\u0005H\u0000\u0000a\t\u0001"+
		"\u0000\u0000\u0000be\u0005/\u0000\u0000cd\u0005\u001d\u0000\u0000df\u0003"+
		"\f\u0006\u0000ec\u0001\u0000\u0000\u0000ef\u0001\u0000\u0000\u0000f\u000b"+
		"\u0001\u0000\u0000\u0000gi\u0003\u000e\u0007\u0000hg\u0001\u0000\u0000"+
		"\u0000il\u0001\u0000\u0000\u0000jh\u0001\u0000\u0000\u0000jk\u0001\u0000"+
		"\u0000\u0000k\r\u0001\u0000\u0000\u0000lj\u0001\u0000\u0000\u0000mq\u0003"+
		"\u0014\n\u0000nq\u0003\u0016\u000b\u0000oq\u0005\u001e\u0000\u0000pm\u0001"+
		"\u0000\u0000\u0000pn\u0001\u0000\u0000\u0000po\u0001\u0000\u0000\u0000"+
		"q\u000f\u0001\u0000\u0000\u0000r\u0088\u0003\u0014\n\u0000s\u0088\u0003"+
		"\u0016\u000b\u0000t\u0088\u0005D\u0000\u0000u\u0088\u0005E\u0000\u0000"+
		"v\u0088\u0005F\u0000\u0000w\u0088\u00059\u0000\u0000x\u0088\u0005:\u0000"+
		"\u0000y\u0088\u0005;\u0000\u0000z\u0088\u0005<\u0000\u0000{\u0088\u0005"+
		"=\u0000\u0000|\u0088\u0005>\u0000\u0000}\u0088\u0005?\u0000\u0000~\u0088"+
		"\u0005@\u0000\u0000\u007f\u0088\u0005A\u0000\u0000\u0080\u0088\u0005B"+
		"\u0000\u0000\u0081\u0088\u0005C\u0000\u0000\u0082\u0088\u0005\u0001\u0000"+
		"\u0000\u0083\u0088\u0005\u0002\u0000\u0000\u0084\u0088\u0005\u0003\u0000"+
		"\u0000\u0085\u0088\u0005\u0004\u0000\u0000\u0086\u0088\u0005\r\u0000\u0000"+
		"\u0087r\u0001\u0000\u0000\u0000\u0087s\u0001\u0000\u0000\u0000\u0087t"+
		"\u0001\u0000\u0000\u0000\u0087u\u0001\u0000\u0000\u0000\u0087v\u0001\u0000"+
		"\u0000\u0000\u0087w\u0001\u0000\u0000\u0000\u0087x\u0001\u0000\u0000\u0000"+
		"\u0087y\u0001\u0000\u0000\u0000\u0087z\u0001\u0000\u0000\u0000\u0087{"+
		"\u0001\u0000\u0000\u0000\u0087|\u0001\u0000\u0000\u0000\u0087}\u0001\u0000"+
		"\u0000\u0000\u0087~\u0001\u0000\u0000\u0000\u0087\u007f\u0001\u0000\u0000"+
		"\u0000\u0087\u0080\u0001\u0000\u0000\u0000\u0087\u0081\u0001\u0000\u0000"+
		"\u0000\u0087\u0082\u0001\u0000\u0000\u0000\u0087\u0083\u0001\u0000\u0000"+
		"\u0000\u0087\u0084\u0001\u0000\u0000\u0000\u0087\u0085\u0001\u0000\u0000"+
		"\u0000\u0087\u0086\u0001\u0000\u0000\u0000\u0088\u0011\u0001\u0000\u0000"+
		"\u0000\u0089\u0090\u0003\u0014\n\u0000\u008a\u0090\u0003\u0016\u000b\u0000"+
		"\u008b\u0090\u0005K\u0000\u0000\u008c\u0090\u0005L\u0000\u0000\u008d\u0090"+
		"\u0005M\u0000\u0000\u008e\u0090\u0005N\u0000\u0000\u008f\u0089\u0001\u0000"+
		"\u0000\u0000\u008f\u008a\u0001\u0000\u0000\u0000\u008f\u008b\u0001\u0000"+
		"\u0000\u0000\u008f\u008c\u0001\u0000\u0000\u0000\u008f\u008d\u0001\u0000"+
		"\u0000\u0000\u008f\u008e\u0001\u0000\u0000\u0000\u0090\u0013\u0001\u0000"+
		"\u0000\u0000\u0091\u0092\u0005\u001f\u0000\u0000\u0092\u0093\u0003\u001a"+
		"\r\u0000\u0093\u0094\u0005!\u0000\u0000\u0094\u0015\u0001\u0000\u0000"+
		"\u0000\u0095\u0096\u0005 \u0000\u0000\u0096\u0097\u0003\u0018\f\u0000"+
		"\u0097\u0098\u0005\"\u0000\u0000\u0098\u0017\u0001\u0000\u0000\u0000\u0099"+
		"\u009a\u0005\u0011\u0000\u0000\u009a\u00a9\u0003\u001a\r\u0000\u009b\u009c"+
		"\u0005\u0012\u0000\u0000\u009c\u00a9\u0003\u001a\r\u0000\u009d\u00a9\u0005"+
		"\u0013\u0000\u0000\u009e\u00a9\u0005\u0014\u0000\u0000\u009f\u00a0\u0005"+
		"\u0015\u0000\u0000\u00a0\u00a1\u0005#\u0000\u0000\u00a1\u00a2\u0005\u0018"+
		"\u0000\u0000\u00a2\u00a9\u0003\u001a\r\u0000\u00a3\u00a9\u0005\u0016\u0000"+
		"\u0000\u00a4\u00a5\u0005\u0017\u0000\u0000\u00a5\u00a6\u0005#\u0000\u0000"+
		"\u00a6\u00a7\u0005\u001b\u0000\u0000\u00a7\u00a9\u0003\u001a\r\u0000\u00a8"+
		"\u0099\u0001\u0000\u0000\u0000\u00a8\u009b\u0001\u0000\u0000\u0000\u00a8"+
		"\u009d\u0001\u0000\u0000\u0000\u00a8\u009e\u0001\u0000\u0000\u0000\u00a8"+
		"\u009f\u0001\u0000\u0000\u0000\u00a8\u00a3\u0001\u0000\u0000\u0000\u00a8"+
		"\u00a4\u0001\u0000\u0000\u0000\u00a9\u0019\u0001\u0000\u0000\u0000\u00aa"+
		"\u00af\u0003\u001c\u000e\u0000\u00ab\u00ac\u0005\u001a\u0000\u0000\u00ac"+
		"\u00ae\u0003\u001c\u000e\u0000\u00ad\u00ab\u0001\u0000\u0000\u0000\u00ae"+
		"\u00b1\u0001\u0000\u0000\u0000\u00af\u00ad\u0001\u0000\u0000\u0000\u00af"+
		"\u00b0\u0001\u0000\u0000\u0000\u00b0\u001b\u0001\u0000\u0000\u0000\u00b1"+
		"\u00af\u0001\u0000\u0000\u0000\u00b2\u00b7\u0003\u001e\u000f\u0000\u00b3"+
		"\u00b4\u0005\u0019\u0000\u0000\u00b4\u00b6\u0003\u001e\u000f\u0000\u00b5"+
		"\u00b3\u0001\u0000\u0000\u0000\u00b6\u00b9\u0001\u0000\u0000\u0000\u00b7"+
		"\u00b5\u0001\u0000\u0000\u0000\u00b7\u00b8\u0001\u0000\u0000\u0000\u00b8"+
		"\u001d\u0001\u0000\u0000\u0000\u00b9\u00b7\u0001\u0000\u0000\u0000\u00ba"+
		"\u00bd\u0003 \u0010\u0000\u00bb\u00bc\u0007\u0000\u0000\u0000\u00bc\u00be"+
		"\u0003 \u0010\u0000\u00bd\u00bb\u0001\u0000\u0000\u0000\u00bd\u00be\u0001"+
		"\u0000\u0000\u0000\u00be\u001f\u0001\u0000\u0000\u0000\u00bf\u00c4\u0003"+
		"\"\u0011\u0000\u00c0\u00c1\u0007\u0001\u0000\u0000\u00c1\u00c3\u0003\""+
		"\u0011\u0000\u00c2\u00c0\u0001\u0000\u0000\u0000\u00c3\u00c6\u0001\u0000"+
		"\u0000\u0000\u00c4\u00c2\u0001\u0000\u0000\u0000\u00c4\u00c5\u0001\u0000"+
		"\u0000\u0000\u00c5!\u0001\u0000\u0000\u0000\u00c6\u00c4\u0001\u0000\u0000"+
		"\u0000\u00c7\u00cc\u0003$\u0012\u0000\u00c8\u00c9\u0007\u0002\u0000\u0000"+
		"\u00c9\u00cb\u0003$\u0012\u0000\u00ca\u00c8\u0001\u0000\u0000\u0000\u00cb"+
		"\u00ce\u0001\u0000\u0000\u0000\u00cc\u00ca\u0001\u0000\u0000\u0000\u00cc"+
		"\u00cd\u0001\u0000\u0000\u0000\u00cd#\u0001\u0000\u0000\u0000\u00ce\u00cc"+
		"\u0001\u0000\u0000\u0000\u00cf\u00d0\u0003&\u0013\u0000\u00d0%\u0001\u0000"+
		"\u0000\u0000\u00d1\u00d2\u0006\u0013\uffff\uffff\u0000\u00d2\u00e1\u0005"+
		"$\u0000\u0000\u00d3\u00e1\u0005%\u0000\u0000\u00d4\u00d9\u0005#\u0000"+
		"\u0000\u00d5\u00d6\u0005\t\u0000\u0000\u00d6\u00d8\u0005#\u0000\u0000"+
		"\u00d7\u00d5\u0001\u0000\u0000\u0000\u00d8\u00db\u0001\u0000\u0000\u0000"+
		"\u00d9\u00d7\u0001\u0000\u0000\u0000\u00d9\u00da\u0001\u0000\u0000\u0000"+
		"\u00da\u00e1\u0001\u0000\u0000\u0000\u00db\u00d9\u0001\u0000\u0000\u0000"+
		"\u00dc\u00dd\u0005\u0007\u0000\u0000\u00dd\u00de\u0003\u001a\r\u0000\u00de"+
		"\u00df\u0005\b\u0000\u0000\u00df\u00e1\u0001\u0000\u0000\u0000\u00e0\u00d1"+
		"\u0001\u0000\u0000\u0000\u00e0\u00d3\u0001\u0000\u0000\u0000\u00e0\u00d4"+
		"\u0001\u0000\u0000\u0000\u00e0\u00dc\u0001\u0000\u0000\u0000\u00e1\u00e7"+
		"\u0001\u0000\u0000\u0000\u00e2\u00e3\n\u0001\u0000\u0000\u00e3\u00e4\u0005"+
		"\u001c\u0000\u0000\u00e4\u00e6\u0005#\u0000\u0000\u00e5\u00e2\u0001\u0000"+
		"\u0000\u0000\u00e6\u00e9\u0001\u0000\u0000\u0000\u00e7\u00e5\u0001\u0000"+
		"\u0000\u0000\u00e7\u00e8\u0001\u0000\u0000\u0000\u00e8\'\u0001\u0000\u0000"+
		"\u0000\u00e9\u00e7\u0001\u0000\u0000\u0000\u001513;JNT]ejp\u0087\u008f"+
		"\u00a8\u00af\u00b7\u00bd\u00c4\u00cc\u00d9\u00e0\u00e7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}