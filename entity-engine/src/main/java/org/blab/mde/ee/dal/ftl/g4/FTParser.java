// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FT.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;

import org.blab.mde.ee.dal.ftl.FTAgg;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;
import java.util.List;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FTParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		SUB_FT=1, BIGSTRING_NO_NL=2, COMMENT=3, LINE_COMMENT=4, WS=5;
	public static final int
		RULE_interpreter = 0;
	public static final String[] ruleNames = {
		"interpreter"
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

	@Override
	public String getGrammarFileName() { return "FT.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	public FTAgg ftAgg;


	public FTParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class InterpreterContext extends ParserRuleContext {
		public FTAgg ftAgg;
		public List<TerminalNode> SUB_FT() { return getTokens(FTParser.SUB_FT); }
		public TerminalNode SUB_FT(int i) {
			return getToken(FTParser.SUB_FT, i);
		}
		public InterpreterContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public InterpreterContext(ParserRuleContext parent, int invokingState, FTAgg ftAgg) {
			super(parent, invokingState);
			this.ftAgg = ftAgg;
		}
		@Override public int getRuleIndex() { return RULE_interpreter; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTListener ) ((FTListener)listener).enterInterpreter(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTListener ) ((FTListener)listener).exitInterpreter(this);
		}
	}

	public final InterpreterContext interpreter(FTAgg ftAgg) throws RecognitionException {
		InterpreterContext _localctx = new InterpreterContext(_ctx, getState(), ftAgg);
		enterRule(_localctx, 0, RULE_interpreter);
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(5);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(2);
					match(SUB_FT);
					}
					}
				}
				setState(7);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,0,_ctx);
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

	public static final String _serializedATN =
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\7\13\4\2\t\2\3\2"+
		"\7\2\6\n\2\f\2\16\2\t\13\2\3\2\3\7\2\3\2\2\2\2\n\2\7\3\2\2\2\4\6\7\3\2"+
		"\2\5\4\3\2\2\2\6\t\3\2\2\2\7\b\3\2\2\2\7\5\3\2\2\2\b\3\3\2\2\2\t\7\3\2"+
		"\2\2\3\7";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}