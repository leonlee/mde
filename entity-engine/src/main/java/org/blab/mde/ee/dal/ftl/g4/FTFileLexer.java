// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FTFile.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;

import org.blab.mde.ee.dal.ftl.utils.*;
import org.blab.mde.ee.dal.ftl.*;
import java.io.File;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FTFileLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
		T__9=10, ID=11, IDS=12, STRING=13, BIGSTRING_NO_NL=14, BIGSTRING=15, COMMENT=16,
		LINE_COMMENT=17, WS=18;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"T__0", "T__1", "T__2", "T__3", "T__4", "T__5", "T__6", "T__7", "T__8",
		"T__9", "ID", "IDS", "STRING", "BIGSTRING_NO_NL", "BIGSTRING", "COMMENT",
		"LINE_COMMENT", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'import'", "'group'", "':'", "'implements'", "','", "';'", "'.'",
		"'delimiters'", "'@'", "'::='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "ID",
		"IDS", "STRING", "BIGSTRING_NO_NL", "BIGSTRING", "COMMENT", "LINE_COMMENT",
		"WS"
	};
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


	public FTFile ftFile;

	@Override
	public String getSourceName() {
	    String fullFileName = super.getSourceName();
	    File f = new File(fullFileName); // strip to simple name
	    return f.getName();
	}


	public FTFileLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FTFile.g4"; }

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

	@Override
	public void action(RuleContext _localctx, int ruleIndex, int actionIndex) {
		switch (ruleIndex) {
		case 12:
			STRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 13:
			BIGSTRING_NO_NL_action((RuleContext)_localctx, actionIndex);
			break;
		case 14:
			BIGSTRING_action((RuleContext)_localctx, actionIndex);
			break;
		case 15:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 16:
			LINE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 17:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void STRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:


			break;
		case 1:

			        String txt = getText().replaceAll("\\\\\"","\"");
					setText(txt);

			break;
		}
	}
	private void BIGSTRING_NO_NL_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:

			        String txt = getText().replaceAll("\\%\\\\>","\\%>");
					setText(txt);

			break;
		}
	}
	private void BIGSTRING_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 3:

			        String txt = getText();
			        txt = MiscUtil.replaceEscapedRightAngle(txt); // replace \> with > unless <\\>
					setText(txt);

			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 4:
			skip();
			break;
		}
	}
	private void LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 5:
			skip();
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 6:
			skip();
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\24\u00b7\b\1\4\2"+
		"\t\2\4\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\4"+
		"\13\t\13\4\f\t\f\4\r\t\r\4\16\t\16\4\17\t\17\4\20\t\20\4\21\t\21\4\22"+
		"\t\22\4\23\t\23\3\2\3\2\3\2\3\2\3\2\3\2\3\2\3\3\3\3\3\3\3\3\3\3\3\3\3"+
		"\4\3\4\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\b"+
		"\3\b\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\t\3\n\3\n\3\13\3\13\3\13"+
		"\3\13\3\f\3\f\7\f[\n\f\f\f\16\f^\13\f\3\r\3\r\3\r\7\rc\n\r\f\r\16\rf\13"+
		"\r\3\16\3\16\3\16\3\16\3\16\3\16\3\16\3\16\7\16p\n\16\f\16\16\16s\13\16"+
		"\3\16\3\16\3\16\3\17\3\17\3\17\3\17\7\17|\n\17\f\17\16\17\177\13\17\3"+
		"\17\3\17\3\17\3\17\3\17\3\20\3\20\3\20\3\20\3\20\3\20\3\20\3\20\7\20\u008e"+
		"\n\20\f\20\16\20\u0091\13\20\3\20\3\20\3\20\3\20\3\20\3\21\3\21\3\21\3"+
		"\21\7\21\u009c\n\21\f\21\16\21\u009f\13\21\3\21\3\21\3\21\3\21\3\21\3"+
		"\22\3\22\3\22\3\22\7\22\u00aa\n\22\f\22\16\22\u00ad\13\22\3\22\5\22\u00b0"+
		"\n\22\3\22\3\22\3\22\3\23\3\23\3\23\4\u008f\u009d\2\24\3\3\5\4\7\5\t\6"+
		"\13\7\r\b\17\t\21\n\23\13\25\f\27\r\31\16\33\17\35\20\37\21!\22#\23%\24"+
		"\3\2\n\5\2C\\aac|\7\2//\62;C\\aac|\3\2$$\5\2\f\f$$^^\3\2@@\3\2^^\4\2\f"+
		"\f\17\17\5\2\13\f\17\17\"\"\2\u00c3\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2"+
		"\2\2\t\3\2\2\2\2\13\3\2\2\2\2\r\3\2\2\2\2\17\3\2\2\2\2\21\3\2\2\2\2\23"+
		"\3\2\2\2\2\25\3\2\2\2\2\27\3\2\2\2\2\31\3\2\2\2\2\33\3\2\2\2\2\35\3\2"+
		"\2\2\2\37\3\2\2\2\2!\3\2\2\2\2#\3\2\2\2\2%\3\2\2\2\3\'\3\2\2\2\5.\3\2"+
		"\2\2\7\64\3\2\2\2\t\66\3\2\2\2\13A\3\2\2\2\rC\3\2\2\2\17E\3\2\2\2\21G"+
		"\3\2\2\2\23R\3\2\2\2\25T\3\2\2\2\27X\3\2\2\2\31_\3\2\2\2\33g\3\2\2\2\35"+
		"w\3\2\2\2\37\u0085\3\2\2\2!\u0097\3\2\2\2#\u00a5\3\2\2\2%\u00b4\3\2\2"+
		"\2\'(\7k\2\2()\7o\2\2)*\7r\2\2*+\7q\2\2+,\7t\2\2,-\7v\2\2-\4\3\2\2\2."+
		"/\7i\2\2/\60\7t\2\2\60\61\7q\2\2\61\62\7w\2\2\62\63\7r\2\2\63\6\3\2\2"+
		"\2\64\65\7<\2\2\65\b\3\2\2\2\66\67\7k\2\2\678\7o\2\289\7r\2\29:\7n\2\2"+
		":;\7g\2\2;<\7o\2\2<=\7g\2\2=>\7p\2\2>?\7v\2\2?@\7u\2\2@\n\3\2\2\2AB\7"+
		".\2\2B\f\3\2\2\2CD\7=\2\2D\16\3\2\2\2EF\7\60\2\2F\20\3\2\2\2GH\7f\2\2"+
		"HI\7g\2\2IJ\7n\2\2JK\7k\2\2KL\7o\2\2LM\7k\2\2MN\7v\2\2NO\7g\2\2OP\7t\2"+
		"\2PQ\7u\2\2Q\22\3\2\2\2RS\7B\2\2S\24\3\2\2\2TU\7<\2\2UV\7<\2\2VW\7?\2"+
		"\2W\26\3\2\2\2X\\\t\2\2\2Y[\t\3\2\2ZY\3\2\2\2[^\3\2\2\2\\Z\3\2\2\2\\]"+
		"\3\2\2\2]\30\3\2\2\2^\\\3\2\2\2_d\5\27\f\2`a\7\60\2\2ac\5\27\f\2b`\3\2"+
		"\2\2cf\3\2\2\2db\3\2\2\2de\3\2\2\2e\32\3\2\2\2fd\3\2\2\2gq\7$\2\2hi\7"+
		"^\2\2ip\7$\2\2jk\7^\2\2kp\n\4\2\2lm\b\16\2\2mp\7\f\2\2np\n\5\2\2oh\3\2"+
		"\2\2oj\3\2\2\2ol\3\2\2\2on\3\2\2\2ps\3\2\2\2qo\3\2\2\2qr\3\2\2\2rt\3\2"+
		"\2\2sq\3\2\2\2tu\7$\2\2uv\b\16\3\2v\34\3\2\2\2wx\7>\2\2xy\7\'\2\2y}\3"+
		"\2\2\2z|\13\2\2\2{z\3\2\2\2|\177\3\2\2\2}{\3\2\2\2}~\3\2\2\2~\u0080\3"+
		"\2\2\2\177}\3\2\2\2\u0080\u0081\7\'\2\2\u0081\u0082\7@\2\2\u0082\u0083"+
		"\3\2\2\2\u0083\u0084\b\17\4\2\u0084\36\3\2\2\2\u0085\u0086\7>\2\2\u0086"+
		"\u0087\7>\2\2\u0087\u008f\3\2\2\2\u0088\u0089\7^\2\2\u0089\u008e\7@\2"+
		"\2\u008a\u008b\7^\2\2\u008b\u008e\n\6\2\2\u008c\u008e\n\7\2\2\u008d\u0088"+
		"\3\2\2\2\u008d\u008a\3\2\2\2\u008d\u008c\3\2\2\2\u008e\u0091\3\2\2\2\u008f"+
		"\u0090\3\2\2\2\u008f\u008d\3\2\2\2\u0090\u0092\3\2\2\2\u0091\u008f\3\2"+
		"\2\2\u0092\u0093\7@\2\2\u0093\u0094\7@\2\2\u0094\u0095\3\2\2\2\u0095\u0096"+
		"\b\20\5\2\u0096 \3\2\2\2\u0097\u0098\7\61\2\2\u0098\u0099\7,\2\2\u0099"+
		"\u009d\3\2\2\2\u009a\u009c\13\2\2\2\u009b\u009a\3\2\2\2\u009c\u009f\3"+
		"\2\2\2\u009d\u009e\3\2\2\2\u009d\u009b\3\2\2\2\u009e\u00a0\3\2\2\2\u009f"+
		"\u009d\3\2\2\2\u00a0\u00a1\7,\2\2\u00a1\u00a2\7\61\2\2\u00a2\u00a3\3\2"+
		"\2\2\u00a3\u00a4\b\21\6\2\u00a4\"\3\2\2\2\u00a5\u00a6\7\61\2\2\u00a6\u00a7"+
		"\7\61\2\2\u00a7\u00ab\3\2\2\2\u00a8\u00aa\n\b\2\2\u00a9\u00a8\3\2\2\2"+
		"\u00aa\u00ad\3\2\2\2\u00ab\u00a9\3\2\2\2\u00ab\u00ac\3\2\2\2\u00ac\u00af"+
		"\3\2\2\2\u00ad\u00ab\3\2\2\2\u00ae\u00b0\7\17\2\2\u00af\u00ae\3\2\2\2"+
		"\u00af\u00b0\3\2\2\2\u00b0\u00b1\3\2\2\2\u00b1\u00b2\7\f\2\2\u00b2\u00b3"+
		"\b\22\7\2\u00b3$\3\2\2\2\u00b4\u00b5\t\t\2\2\u00b5\u00b6\b\23\b\2\u00b6"+
		"&\3\2\2\2\r\2\\doq}\u008d\u008f\u009d\u00ab\u00af\t\3\16\2\3\16\3\3\17"+
		"\4\3\20\5\3\21\6\3\22\7\3\23\b";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}