// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FT.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;

import org.blab.mde.ee.dal.ftl.FTAgg;

import org.antlr.v4.runtime.Lexer;
import org.antlr.v4.runtime.CharStream;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FTLexer extends Lexer {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SUB_FT=1, BIGSTRING_NO_NL=2, COMMENT=3, LINE_COMMENT=4, WS=5;
	public static String[] channelNames = {
		"DEFAULT_TOKEN_CHANNEL", "HIDDEN"
	};

	public static String[] modeNames = {
		"DEFAULT_MODE"
	};

	public static final String[] ruleNames = {
		"SUB_FT", "BIGSTRING_NO_NL", "COMMENT", "LINE_COMMENT", "WS"
	};

	private static final String[] _LITERAL_NAMES = {
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, "SUB_FT", "BIGSTRING_NO_NL", "COMMENT", "LINE_COMMENT", "WS"
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


	public FTAgg ftAgg;



	public FTLexer(CharStream input) {
		super(input);
		_interp = new LexerATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}

	@Override
	public String getGrammarFileName() { return "FT.g4"; }

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
		case 2:
			COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 3:
			LINE_COMMENT_action((RuleContext)_localctx, actionIndex);
			break;
		case 4:
			WS_action((RuleContext)_localctx, actionIndex);
			break;
		}
	}
	private void COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 0:
			skip();
			break;
		}
	}
	private void LINE_COMMENT_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 1:
			skip();
			break;
		}
	}
	private void WS_action(RuleContext _localctx, int actionIndex) {
		switch (actionIndex) {
		case 2:
			skip();
			break;
		}
	}

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\2\7C\b\1\4\2\t\2\4"+
		"\3\t\3\4\4\t\4\4\5\t\5\4\6\t\6\3\2\3\2\3\2\7\2\21\n\2\f\2\16\2\24\13\2"+
		"\3\2\3\2\3\3\3\3\3\3\3\3\7\3\34\n\3\f\3\16\3\37\13\3\3\3\3\3\3\3\3\4\3"+
		"\4\3\4\3\4\7\4(\n\4\f\4\16\4+\13\4\3\4\3\4\3\4\3\4\3\4\3\5\3\5\3\5\3\5"+
		"\7\5\66\n\5\f\5\16\59\13\5\3\5\5\5<\n\5\3\5\3\5\3\5\3\6\3\6\3\6\4\35)"+
		"\2\7\3\3\5\4\7\5\t\6\13\7\3\2\6\5\2C\\aac|\7\2//\62;C\\aac|\4\2\f\f\17"+
		"\17\5\2\13\f\17\17\"\"\2G\2\3\3\2\2\2\2\5\3\2\2\2\2\7\3\2\2\2\2\t\3\2"+
		"\2\2\2\13\3\2\2\2\3\r\3\2\2\2\5\27\3\2\2\2\7#\3\2\2\2\t\61\3\2\2\2\13"+
		"@\3\2\2\2\r\16\7>\2\2\16\22\t\2\2\2\17\21\t\3\2\2\20\17\3\2\2\2\21\24"+
		"\3\2\2\2\22\20\3\2\2\2\22\23\3\2\2\2\23\25\3\2\2\2\24\22\3\2\2\2\25\26"+
		"\7@\2\2\26\4\3\2\2\2\27\30\7>\2\2\30\31\7\'\2\2\31\35\3\2\2\2\32\34\13"+
		"\2\2\2\33\32\3\2\2\2\34\37\3\2\2\2\35\36\3\2\2\2\35\33\3\2\2\2\36 \3\2"+
		"\2\2\37\35\3\2\2\2 !\7\'\2\2!\"\7@\2\2\"\6\3\2\2\2#$\7\61\2\2$%\7,\2\2"+
		"%)\3\2\2\2&(\13\2\2\2\'&\3\2\2\2(+\3\2\2\2)*\3\2\2\2)\'\3\2\2\2*,\3\2"+
		"\2\2+)\3\2\2\2,-\7,\2\2-.\7\61\2\2./\3\2\2\2/\60\b\4\2\2\60\b\3\2\2\2"+
		"\61\62\7\61\2\2\62\63\7\61\2\2\63\67\3\2\2\2\64\66\n\4\2\2\65\64\3\2\2"+
		"\2\669\3\2\2\2\67\65\3\2\2\2\678\3\2\2\28;\3\2\2\29\67\3\2\2\2:<\7\17"+
		"\2\2;:\3\2\2\2;<\3\2\2\2<=\3\2\2\2=>\7\f\2\2>?\b\5\3\2?\n\3\2\2\2@A\t"+
		"\5\2\2AB\b\6\4\2B\f\3\2\2\2\b\2\22\35)\67;\5\3\4\2\3\5\3\3\6\4";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}