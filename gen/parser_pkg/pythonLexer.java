// Generated from D:/CompilerProjectRepo/CompilerProject1_IT4-main/src/parser_pkg/pythonLexer.g4 by ANTLR 4.13.2
package parser_pkg;
import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.misc.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast", "CheckReturnValue", "this-escape"})
public class pythonLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.13.2", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		DEF=1, IF=2, ELSE=3, ELIF=4, WHILE=5, FOR=6, IN=7, PRINT=8, RETURN=9, 
		AND=10, OR=11, NOT=12, TRUE=13, FALSE=14, NONE=15, IMPORT=16, FROM=17, 
		AS=18, IS=19, PLUS=20, MINUS=21, MUL=22, AT=23, DIV=24, EQ=25, EQEQ=26, 
		LT=27, GT=28, LE=29, GE=30, NOT_EQ=31, LP=32, RP=33, LB=34, RB=35, LC=36, 
		RC=37, COMMA=38, COLON=39, DOT=40, ID=41, NUMBER=42, STRING=43, WS=44, 
		COMMENT=45, NEWLINE=46, INDENT=47, DEDENT=48;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	private static String[] makeRuleNames() {
		return new String[] {
			"DEF", "IF", "ELSE", "ELIF", "WHILE", "FOR", "IN", "PRINT", "RETURN", 
			"AND", "OR", "NOT", "TRUE", "FALSE", "NONE", "IMPORT", "FROM", "AS", 
			"IS", "PLUS", "MINUS", "MUL", "AT", "DIV", "EQ", "EQEQ", "LT", "GT", 
			"LE", "GE", "NOT_EQ", "LP", "RP", "LB", "RB", "LC", "RC", "COMMA", "COLON", 
			"DOT", "ID", "NUMBER", "STRING", "WS", "COMMENT", "NEWLINE", "INDENT", 
			"DEDENT"
		};
	}
	public static final String[] ruleNames = makeRuleNames();

	private static String[] makeLiteralNames() {
		return new String[] {
			null, "'def'", "'if'", "'else'", "'elif'", "'while'", "'for'", "'in'", 
			"'print'", "'return'", "'and'", "'or'", "'not'", "'True'", "'False'", 
			"'None'", "'import'", "'from'", "'as'", "'is'", "'+'", "'-'", "'*'", 
			"'@'", "'/'", "'='", "'=='", "'<'", "'>'", "'<='", "'>='", "'!='", "'('", 
			"')'", "'{'", "'}'", "'['", "']'", "','", "':'", "'.'"
		};
	}
	private static final String[] _LITERAL_NAMES = makeLiteralNames();
	private static String[] makeSymbolicNames() {
		return new String[] {
			null, "DEF", "IF", "ELSE", "ELIF", "WHILE", "FOR", "IN", "PRINT", "RETURN", 
			"AND", "OR", "NOT", "TRUE", "FALSE", "NONE", "IMPORT", "FROM", "AS", 
			"IS", "PLUS", "MINUS", "MUL", "AT", "DIV", "EQ", "EQEQ", "LT", "GT", 
			"LE", "GE", "NOT_EQ", "LP", "RP", "LB", "RB", "LC", "RC", "COMMA", "COLON", 
			"DOT", "ID", "NUMBER", "STRING", "WS", "COMMENT", "NEWLINE", "INDENT", 
			"DEDENT"
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



	    private java.util.Queue<Token> tokens = new java.util.LinkedList<>();
	    private java.util.Stack<Integer> indents = new java.util.Stack<>();
	    private boolean reachedEof = false;
	    private int bracketDepth = 0;

	    private void init() {
	        if (indents.isEmpty()) {
	            indents.push(0);
	        }
	    }

	    @Override
	    public Token nextToken() {
	        init();

	        if (!tokens.isEmpty()) {
	            return tokens.poll();
	        }

	        Token token = super.nextToken();

	         if (token.getType() == LP || token.getType() == LC || token.getType() == LB) {
	                    bracketDepth++;
	                } else if (token.getType() == RP || token.getType() == RC || token.getType() == RB) {
	                    if (bracketDepth > 0) bracketDepth--;
	                }

	        if (token.getType() == EOF) {
	            while (indents.size() > 1) {
	                indents.pop();
	                tokens.offer(createToken(DEDENT, ""));
	            }

	            tokens.offer(token);
	            reachedEof = true;
	            return tokens.poll();
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

	            return tokens.poll();
	        }

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


	public pythonLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "pythonLexer.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public String[] getChannelNames() { return channelNames; }

	@Override
	public String[] getModeNames() { return modeNames; }

	@Override
	public ATN getATN() { return _ATN; }

	public static final String _serializedATN =
		"\u0004\u00000\u012f\u0006\uffff\uffff\u0002\u0000\u0007\u0000\u0002\u0001"+
		"\u0007\u0001\u0002\u0002\u0007\u0002\u0002\u0003\u0007\u0003\u0002\u0004"+
		"\u0007\u0004\u0002\u0005\u0007\u0005\u0002\u0006\u0007\u0006\u0002\u0007"+
		"\u0007\u0007\u0002\b\u0007\b\u0002\t\u0007\t\u0002\n\u0007\n\u0002\u000b"+
		"\u0007\u000b\u0002\f\u0007\f\u0002\r\u0007\r\u0002\u000e\u0007\u000e\u0002"+
		"\u000f\u0007\u000f\u0002\u0010\u0007\u0010\u0002\u0011\u0007\u0011\u0002"+
		"\u0012\u0007\u0012\u0002\u0013\u0007\u0013\u0002\u0014\u0007\u0014\u0002"+
		"\u0015\u0007\u0015\u0002\u0016\u0007\u0016\u0002\u0017\u0007\u0017\u0002"+
		"\u0018\u0007\u0018\u0002\u0019\u0007\u0019\u0002\u001a\u0007\u001a\u0002"+
		"\u001b\u0007\u001b\u0002\u001c\u0007\u001c\u0002\u001d\u0007\u001d\u0002"+
		"\u001e\u0007\u001e\u0002\u001f\u0007\u001f\u0002 \u0007 \u0002!\u0007"+
		"!\u0002\"\u0007\"\u0002#\u0007#\u0002$\u0007$\u0002%\u0007%\u0002&\u0007"+
		"&\u0002\'\u0007\'\u0002(\u0007(\u0002)\u0007)\u0002*\u0007*\u0002+\u0007"+
		"+\u0002,\u0007,\u0002-\u0007-\u0002.\u0007.\u0002/\u0007/\u0001\u0000"+
		"\u0001\u0000\u0001\u0000\u0001\u0000\u0001\u0001\u0001\u0001\u0001\u0001"+
		"\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0002\u0001\u0003"+
		"\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0003\u0001\u0004\u0001\u0004"+
		"\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0004\u0001\u0005\u0001\u0005"+
		"\u0001\u0005\u0001\u0005\u0001\u0006\u0001\u0006\u0001\u0006\u0001\u0007"+
		"\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\u0007\u0001\b\u0001"+
		"\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\b\u0001\t\u0001\t\u0001\t\u0001"+
		"\t\u0001\n\u0001\n\u0001\n\u0001\u000b\u0001\u000b\u0001\u000b\u0001\u000b"+
		"\u0001\f\u0001\f\u0001\f\u0001\f\u0001\f\u0001\r\u0001\r\u0001\r\u0001"+
		"\r\u0001\r\u0001\r\u0001\u000e\u0001\u000e\u0001\u000e\u0001\u000e\u0001"+
		"\u000e\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001\u000f\u0001"+
		"\u000f\u0001\u000f\u0001\u0010\u0001\u0010\u0001\u0010\u0001\u0010\u0001"+
		"\u0010\u0001\u0011\u0001\u0011\u0001\u0011\u0001\u0012\u0001\u0012\u0001"+
		"\u0012\u0001\u0013\u0001\u0013\u0001\u0014\u0001\u0014\u0001\u0015\u0001"+
		"\u0015\u0001\u0016\u0001\u0016\u0001\u0017\u0001\u0017\u0001\u0018\u0001"+
		"\u0018\u0001\u0019\u0001\u0019\u0001\u0019\u0001\u001a\u0001\u001a\u0001"+
		"\u001b\u0001\u001b\u0001\u001c\u0001\u001c\u0001\u001c\u0001\u001d\u0001"+
		"\u001d\u0001\u001d\u0001\u001e\u0001\u001e\u0001\u001e\u0001\u001f\u0001"+
		"\u001f\u0001 \u0001 \u0001!\u0001!\u0001\"\u0001\"\u0001#\u0001#\u0001"+
		"$\u0001$\u0001%\u0001%\u0001&\u0001&\u0001\'\u0001\'\u0001(\u0001(\u0005"+
		"(\u00ea\b(\n(\f(\u00ed\t(\u0001)\u0004)\u00f0\b)\u000b)\f)\u00f1\u0001"+
		")\u0001)\u0004)\u00f6\b)\u000b)\f)\u00f7\u0003)\u00fa\b)\u0001*\u0001"+
		"*\u0001*\u0001*\u0005*\u0100\b*\n*\f*\u0103\t*\u0001*\u0001*\u0001*\u0001"+
		"*\u0001*\u0005*\u010a\b*\n*\f*\u010d\t*\u0001*\u0003*\u0110\b*\u0001+"+
		"\u0004+\u0113\b+\u000b+\f+\u0114\u0001+\u0001+\u0001,\u0001,\u0005,\u011b"+
		"\b,\n,\f,\u011e\t,\u0001,\u0001,\u0001-\u0003-\u0123\b-\u0001-\u0001-"+
		"\u0005-\u0127\b-\n-\f-\u012a\t-\u0001.\u0001.\u0001/\u0001/\u0000\u0000"+
		"0\u0001\u0001\u0003\u0002\u0005\u0003\u0007\u0004\t\u0005\u000b\u0006"+
		"\r\u0007\u000f\b\u0011\t\u0013\n\u0015\u000b\u0017\f\u0019\r\u001b\u000e"+
		"\u001d\u000f\u001f\u0010!\u0011#\u0012%\u0013\'\u0014)\u0015+\u0016-\u0017"+
		"/\u00181\u00193\u001a5\u001b7\u001c9\u001d;\u001e=\u001f? A!C\"E#G$I%"+
		"K&M\'O(Q)S*U+W,Y-[.]/_0\u0001\u0000\u0007\u0003\u0000AZ__az\u0004\u0000"+
		"09AZ__az\u0001\u000009\u0004\u0000\n\n\r\r\"\"\\\\\u0004\u0000\n\n\r\r"+
		"\'\'\\\\\u0002\u0000\t\t  \u0002\u0000\n\n\r\r\u013b\u0000\u0001\u0001"+
		"\u0000\u0000\u0000\u0000\u0003\u0001\u0000\u0000\u0000\u0000\u0005\u0001"+
		"\u0000\u0000\u0000\u0000\u0007\u0001\u0000\u0000\u0000\u0000\t\u0001\u0000"+
		"\u0000\u0000\u0000\u000b\u0001\u0000\u0000\u0000\u0000\r\u0001\u0000\u0000"+
		"\u0000\u0000\u000f\u0001\u0000\u0000\u0000\u0000\u0011\u0001\u0000\u0000"+
		"\u0000\u0000\u0013\u0001\u0000\u0000\u0000\u0000\u0015\u0001\u0000\u0000"+
		"\u0000\u0000\u0017\u0001\u0000\u0000\u0000\u0000\u0019\u0001\u0000\u0000"+
		"\u0000\u0000\u001b\u0001\u0000\u0000\u0000\u0000\u001d\u0001\u0000\u0000"+
		"\u0000\u0000\u001f\u0001\u0000\u0000\u0000\u0000!\u0001\u0000\u0000\u0000"+
		"\u0000#\u0001\u0000\u0000\u0000\u0000%\u0001\u0000\u0000\u0000\u0000\'"+
		"\u0001\u0000\u0000\u0000\u0000)\u0001\u0000\u0000\u0000\u0000+\u0001\u0000"+
		"\u0000\u0000\u0000-\u0001\u0000\u0000\u0000\u0000/\u0001\u0000\u0000\u0000"+
		"\u00001\u0001\u0000\u0000\u0000\u00003\u0001\u0000\u0000\u0000\u00005"+
		"\u0001\u0000\u0000\u0000\u00007\u0001\u0000\u0000\u0000\u00009\u0001\u0000"+
		"\u0000\u0000\u0000;\u0001\u0000\u0000\u0000\u0000=\u0001\u0000\u0000\u0000"+
		"\u0000?\u0001\u0000\u0000\u0000\u0000A\u0001\u0000\u0000\u0000\u0000C"+
		"\u0001\u0000\u0000\u0000\u0000E\u0001\u0000\u0000\u0000\u0000G\u0001\u0000"+
		"\u0000\u0000\u0000I\u0001\u0000\u0000\u0000\u0000K\u0001\u0000\u0000\u0000"+
		"\u0000M\u0001\u0000\u0000\u0000\u0000O\u0001\u0000\u0000\u0000\u0000Q"+
		"\u0001\u0000\u0000\u0000\u0000S\u0001\u0000\u0000\u0000\u0000U\u0001\u0000"+
		"\u0000\u0000\u0000W\u0001\u0000\u0000\u0000\u0000Y\u0001\u0000\u0000\u0000"+
		"\u0000[\u0001\u0000\u0000\u0000\u0000]\u0001\u0000\u0000\u0000\u0000_"+
		"\u0001\u0000\u0000\u0000\u0001a\u0001\u0000\u0000\u0000\u0003e\u0001\u0000"+
		"\u0000\u0000\u0005h\u0001\u0000\u0000\u0000\u0007m\u0001\u0000\u0000\u0000"+
		"\tr\u0001\u0000\u0000\u0000\u000bx\u0001\u0000\u0000\u0000\r|\u0001\u0000"+
		"\u0000\u0000\u000f\u007f\u0001\u0000\u0000\u0000\u0011\u0085\u0001\u0000"+
		"\u0000\u0000\u0013\u008c\u0001\u0000\u0000\u0000\u0015\u0090\u0001\u0000"+
		"\u0000\u0000\u0017\u0093\u0001\u0000\u0000\u0000\u0019\u0097\u0001\u0000"+
		"\u0000\u0000\u001b\u009c\u0001\u0000\u0000\u0000\u001d\u00a2\u0001\u0000"+
		"\u0000\u0000\u001f\u00a7\u0001\u0000\u0000\u0000!\u00ae\u0001\u0000\u0000"+
		"\u0000#\u00b3\u0001\u0000\u0000\u0000%\u00b6\u0001\u0000\u0000\u0000\'"+
		"\u00b9\u0001\u0000\u0000\u0000)\u00bb\u0001\u0000\u0000\u0000+\u00bd\u0001"+
		"\u0000\u0000\u0000-\u00bf\u0001\u0000\u0000\u0000/\u00c1\u0001\u0000\u0000"+
		"\u00001\u00c3\u0001\u0000\u0000\u00003\u00c5\u0001\u0000\u0000\u00005"+
		"\u00c8\u0001\u0000\u0000\u00007\u00ca\u0001\u0000\u0000\u00009\u00cc\u0001"+
		"\u0000\u0000\u0000;\u00cf\u0001\u0000\u0000\u0000=\u00d2\u0001\u0000\u0000"+
		"\u0000?\u00d5\u0001\u0000\u0000\u0000A\u00d7\u0001\u0000\u0000\u0000C"+
		"\u00d9\u0001\u0000\u0000\u0000E\u00db\u0001\u0000\u0000\u0000G\u00dd\u0001"+
		"\u0000\u0000\u0000I\u00df\u0001\u0000\u0000\u0000K\u00e1\u0001\u0000\u0000"+
		"\u0000M\u00e3\u0001\u0000\u0000\u0000O\u00e5\u0001\u0000\u0000\u0000Q"+
		"\u00e7\u0001\u0000\u0000\u0000S\u00ef\u0001\u0000\u0000\u0000U\u010f\u0001"+
		"\u0000\u0000\u0000W\u0112\u0001\u0000\u0000\u0000Y\u0118\u0001\u0000\u0000"+
		"\u0000[\u0122\u0001\u0000\u0000\u0000]\u012b\u0001\u0000\u0000\u0000_"+
		"\u012d\u0001\u0000\u0000\u0000ab\u0005d\u0000\u0000bc\u0005e\u0000\u0000"+
		"cd\u0005f\u0000\u0000d\u0002\u0001\u0000\u0000\u0000ef\u0005i\u0000\u0000"+
		"fg\u0005f\u0000\u0000g\u0004\u0001\u0000\u0000\u0000hi\u0005e\u0000\u0000"+
		"ij\u0005l\u0000\u0000jk\u0005s\u0000\u0000kl\u0005e\u0000\u0000l\u0006"+
		"\u0001\u0000\u0000\u0000mn\u0005e\u0000\u0000no\u0005l\u0000\u0000op\u0005"+
		"i\u0000\u0000pq\u0005f\u0000\u0000q\b\u0001\u0000\u0000\u0000rs\u0005"+
		"w\u0000\u0000st\u0005h\u0000\u0000tu\u0005i\u0000\u0000uv\u0005l\u0000"+
		"\u0000vw\u0005e\u0000\u0000w\n\u0001\u0000\u0000\u0000xy\u0005f\u0000"+
		"\u0000yz\u0005o\u0000\u0000z{\u0005r\u0000\u0000{\f\u0001\u0000\u0000"+
		"\u0000|}\u0005i\u0000\u0000}~\u0005n\u0000\u0000~\u000e\u0001\u0000\u0000"+
		"\u0000\u007f\u0080\u0005p\u0000\u0000\u0080\u0081\u0005r\u0000\u0000\u0081"+
		"\u0082\u0005i\u0000\u0000\u0082\u0083\u0005n\u0000\u0000\u0083\u0084\u0005"+
		"t\u0000\u0000\u0084\u0010\u0001\u0000\u0000\u0000\u0085\u0086\u0005r\u0000"+
		"\u0000\u0086\u0087\u0005e\u0000\u0000\u0087\u0088\u0005t\u0000\u0000\u0088"+
		"\u0089\u0005u\u0000\u0000\u0089\u008a\u0005r\u0000\u0000\u008a\u008b\u0005"+
		"n\u0000\u0000\u008b\u0012\u0001\u0000\u0000\u0000\u008c\u008d\u0005a\u0000"+
		"\u0000\u008d\u008e\u0005n\u0000\u0000\u008e\u008f\u0005d\u0000\u0000\u008f"+
		"\u0014\u0001\u0000\u0000\u0000\u0090\u0091\u0005o\u0000\u0000\u0091\u0092"+
		"\u0005r\u0000\u0000\u0092\u0016\u0001\u0000\u0000\u0000\u0093\u0094\u0005"+
		"n\u0000\u0000\u0094\u0095\u0005o\u0000\u0000\u0095\u0096\u0005t\u0000"+
		"\u0000\u0096\u0018\u0001\u0000\u0000\u0000\u0097\u0098\u0005T\u0000\u0000"+
		"\u0098\u0099\u0005r\u0000\u0000\u0099\u009a\u0005u\u0000\u0000\u009a\u009b"+
		"\u0005e\u0000\u0000\u009b\u001a\u0001\u0000\u0000\u0000\u009c\u009d\u0005"+
		"F\u0000\u0000\u009d\u009e\u0005a\u0000\u0000\u009e\u009f\u0005l\u0000"+
		"\u0000\u009f\u00a0\u0005s\u0000\u0000\u00a0\u00a1\u0005e\u0000\u0000\u00a1"+
		"\u001c\u0001\u0000\u0000\u0000\u00a2\u00a3\u0005N\u0000\u0000\u00a3\u00a4"+
		"\u0005o\u0000\u0000\u00a4\u00a5\u0005n\u0000\u0000\u00a5\u00a6\u0005e"+
		"\u0000\u0000\u00a6\u001e\u0001\u0000\u0000\u0000\u00a7\u00a8\u0005i\u0000"+
		"\u0000\u00a8\u00a9\u0005m\u0000\u0000\u00a9\u00aa\u0005p\u0000\u0000\u00aa"+
		"\u00ab\u0005o\u0000\u0000\u00ab\u00ac\u0005r\u0000\u0000\u00ac\u00ad\u0005"+
		"t\u0000\u0000\u00ad \u0001\u0000\u0000\u0000\u00ae\u00af\u0005f\u0000"+
		"\u0000\u00af\u00b0\u0005r\u0000\u0000\u00b0\u00b1\u0005o\u0000\u0000\u00b1"+
		"\u00b2\u0005m\u0000\u0000\u00b2\"\u0001\u0000\u0000\u0000\u00b3\u00b4"+
		"\u0005a\u0000\u0000\u00b4\u00b5\u0005s\u0000\u0000\u00b5$\u0001\u0000"+
		"\u0000\u0000\u00b6\u00b7\u0005i\u0000\u0000\u00b7\u00b8\u0005s\u0000\u0000"+
		"\u00b8&\u0001\u0000\u0000\u0000\u00b9\u00ba\u0005+\u0000\u0000\u00ba("+
		"\u0001\u0000\u0000\u0000\u00bb\u00bc\u0005-\u0000\u0000\u00bc*\u0001\u0000"+
		"\u0000\u0000\u00bd\u00be\u0005*\u0000\u0000\u00be,\u0001\u0000\u0000\u0000"+
		"\u00bf\u00c0\u0005@\u0000\u0000\u00c0.\u0001\u0000\u0000\u0000\u00c1\u00c2"+
		"\u0005/\u0000\u0000\u00c20\u0001\u0000\u0000\u0000\u00c3\u00c4\u0005="+
		"\u0000\u0000\u00c42\u0001\u0000\u0000\u0000\u00c5\u00c6\u0005=\u0000\u0000"+
		"\u00c6\u00c7\u0005=\u0000\u0000\u00c74\u0001\u0000\u0000\u0000\u00c8\u00c9"+
		"\u0005<\u0000\u0000\u00c96\u0001\u0000\u0000\u0000\u00ca\u00cb\u0005>"+
		"\u0000\u0000\u00cb8\u0001\u0000\u0000\u0000\u00cc\u00cd\u0005<\u0000\u0000"+
		"\u00cd\u00ce\u0005=\u0000\u0000\u00ce:\u0001\u0000\u0000\u0000\u00cf\u00d0"+
		"\u0005>\u0000\u0000\u00d0\u00d1\u0005=\u0000\u0000\u00d1<\u0001\u0000"+
		"\u0000\u0000\u00d2\u00d3\u0005!\u0000\u0000\u00d3\u00d4\u0005=\u0000\u0000"+
		"\u00d4>\u0001\u0000\u0000\u0000\u00d5\u00d6\u0005(\u0000\u0000\u00d6@"+
		"\u0001\u0000\u0000\u0000\u00d7\u00d8\u0005)\u0000\u0000\u00d8B\u0001\u0000"+
		"\u0000\u0000\u00d9\u00da\u0005{\u0000\u0000\u00daD\u0001\u0000\u0000\u0000"+
		"\u00db\u00dc\u0005}\u0000\u0000\u00dcF\u0001\u0000\u0000\u0000\u00dd\u00de"+
		"\u0005[\u0000\u0000\u00deH\u0001\u0000\u0000\u0000\u00df\u00e0\u0005]"+
		"\u0000\u0000\u00e0J\u0001\u0000\u0000\u0000\u00e1\u00e2\u0005,\u0000\u0000"+
		"\u00e2L\u0001\u0000\u0000\u0000\u00e3\u00e4\u0005:\u0000\u0000\u00e4N"+
		"\u0001\u0000\u0000\u0000\u00e5\u00e6\u0005.\u0000\u0000\u00e6P\u0001\u0000"+
		"\u0000\u0000\u00e7\u00eb\u0007\u0000\u0000\u0000\u00e8\u00ea\u0007\u0001"+
		"\u0000\u0000\u00e9\u00e8\u0001\u0000\u0000\u0000\u00ea\u00ed\u0001\u0000"+
		"\u0000\u0000\u00eb\u00e9\u0001\u0000\u0000\u0000\u00eb\u00ec\u0001\u0000"+
		"\u0000\u0000\u00ecR\u0001\u0000\u0000\u0000\u00ed\u00eb\u0001\u0000\u0000"+
		"\u0000\u00ee\u00f0\u0007\u0002\u0000\u0000\u00ef\u00ee\u0001\u0000\u0000"+
		"\u0000\u00f0\u00f1\u0001\u0000\u0000\u0000\u00f1\u00ef\u0001\u0000\u0000"+
		"\u0000\u00f1\u00f2\u0001\u0000\u0000\u0000\u00f2\u00f9\u0001\u0000\u0000"+
		"\u0000\u00f3\u00f5\u0003O\'\u0000\u00f4\u00f6\u0007\u0002\u0000\u0000"+
		"\u00f5\u00f4\u0001\u0000\u0000\u0000\u00f6\u00f7\u0001\u0000\u0000\u0000"+
		"\u00f7\u00f5\u0001\u0000\u0000\u0000\u00f7\u00f8\u0001\u0000\u0000\u0000"+
		"\u00f8\u00fa\u0001\u0000\u0000\u0000\u00f9\u00f3\u0001\u0000\u0000\u0000"+
		"\u00f9\u00fa\u0001\u0000\u0000\u0000\u00faT\u0001\u0000\u0000\u0000\u00fb"+
		"\u0101\u0005\"\u0000\u0000\u00fc\u0100\b\u0003\u0000\u0000\u00fd\u00fe"+
		"\u0005\\\u0000\u0000\u00fe\u0100\t\u0000\u0000\u0000\u00ff\u00fc\u0001"+
		"\u0000\u0000\u0000\u00ff\u00fd\u0001\u0000\u0000\u0000\u0100\u0103\u0001"+
		"\u0000\u0000\u0000\u0101\u00ff\u0001\u0000\u0000\u0000\u0101\u0102\u0001"+
		"\u0000\u0000\u0000\u0102\u0104\u0001\u0000\u0000\u0000\u0103\u0101\u0001"+
		"\u0000\u0000\u0000\u0104\u0110\u0005\"\u0000\u0000\u0105\u010b\u0005\'"+
		"\u0000\u0000\u0106\u010a\b\u0004\u0000\u0000\u0107\u0108\u0005\\\u0000"+
		"\u0000\u0108\u010a\t\u0000\u0000\u0000\u0109\u0106\u0001\u0000\u0000\u0000"+
		"\u0109\u0107\u0001\u0000\u0000\u0000\u010a\u010d\u0001\u0000\u0000\u0000"+
		"\u010b\u0109\u0001\u0000\u0000\u0000\u010b\u010c\u0001\u0000\u0000\u0000"+
		"\u010c\u010e\u0001\u0000\u0000\u0000\u010d\u010b\u0001\u0000\u0000\u0000"+
		"\u010e\u0110\u0005\'\u0000\u0000\u010f\u00fb\u0001\u0000\u0000\u0000\u010f"+
		"\u0105\u0001\u0000\u0000\u0000\u0110V\u0001\u0000\u0000\u0000\u0111\u0113"+
		"\u0007\u0005\u0000\u0000\u0112\u0111\u0001\u0000\u0000\u0000\u0113\u0114"+
		"\u0001\u0000\u0000\u0000\u0114\u0112\u0001\u0000\u0000\u0000\u0114\u0115"+
		"\u0001\u0000\u0000\u0000\u0115\u0116\u0001\u0000\u0000\u0000\u0116\u0117"+
		"\u0006+\u0000\u0000\u0117X\u0001\u0000\u0000\u0000\u0118\u011c\u0005#"+
		"\u0000\u0000\u0119\u011b\b\u0006\u0000\u0000\u011a\u0119\u0001\u0000\u0000"+
		"\u0000\u011b\u011e\u0001\u0000\u0000\u0000\u011c\u011a\u0001\u0000\u0000"+
		"\u0000\u011c\u011d\u0001\u0000\u0000\u0000\u011d\u011f\u0001\u0000\u0000"+
		"\u0000\u011e\u011c\u0001\u0000\u0000\u0000\u011f\u0120\u0006,\u0000\u0000"+
		"\u0120Z\u0001\u0000\u0000\u0000\u0121\u0123\u0005\r\u0000\u0000\u0122"+
		"\u0121\u0001\u0000\u0000\u0000\u0122\u0123\u0001\u0000\u0000\u0000\u0123"+
		"\u0124\u0001\u0000\u0000\u0000\u0124\u0128\u0005\n\u0000\u0000\u0125\u0127"+
		"\u0007\u0005\u0000\u0000\u0126\u0125\u0001\u0000\u0000\u0000\u0127\u012a"+
		"\u0001\u0000\u0000\u0000\u0128\u0126\u0001\u0000\u0000\u0000\u0128\u0129"+
		"\u0001\u0000\u0000\u0000\u0129\\\u0001\u0000\u0000\u0000\u012a\u0128\u0001"+
		"\u0000\u0000\u0000\u012b\u012c\u0001\u0000\u0000\u0000\u012c^\u0001\u0000"+
		"\u0000\u0000\u012d\u012e\u0001\u0000\u0000\u0000\u012e`\u0001\u0000\u0000"+
		"\u0000\u000e\u0000\u00eb\u00f1\u00f7\u00f9\u00ff\u0101\u0109\u010b\u010f"+
		"\u0114\u011c\u0122\u0128\u0001\u0006\u0000\u0000";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}