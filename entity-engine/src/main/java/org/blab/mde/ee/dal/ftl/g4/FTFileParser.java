// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FTFile.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;


import java.util.List;

import org.blab.mde.ee.dal.ftl.utils.*;
import org.blab.mde.ee.dal.ftl.*;
import java.io.File;

import org.antlr.v4.runtime.atn.*;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.*;
import org.antlr.v4.runtime.tree.*;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class FTFileParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.7", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9,
		T__9=10, ID=11, IDS=12, STRING=13, BIGSTRING_NO_NL=14, BIGSTRING=15, COMMENT=16,
		LINE_COMMENT=17, WS=18, TRUE=19, FALSE=20;
	public static final int
		RULE_group = 0, RULE_oldStyleHeader = 1, RULE_groupName = 2, RULE_delimiters = 3,
		RULE_def = 4, RULE_templateDef = 5;
	public static final String[] ruleNames = {
		"group", "oldStyleHeader", "groupName", "delimiters", "def", "templateDef"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'import'", "'group'", "':'", "'implements'", "','", "';'", "'.'",
		"'delimiters'", "'@'", "'::='"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, "ID",
		"IDS", "STRING", "BIGSTRING_NO_NL", "BIGSTRING", "COMMENT", "LINE_COMMENT",
		"WS", "TRUE", "FALSE"
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
	public String getGrammarFileName() { return "FTFile.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }


	public FTFile ftFile;


	@Override
	public String getSourceName() {
	    String fullFileName = super.getSourceName();
	    File f = new File(fullFileName); // strip to simple name
	    return f.getName();
	}

	public FTFileParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class GroupContext extends ParserRuleContext {
		public FTFile ftFile;
		public String prefix;
		public Token IDS;
		public TerminalNode EOF() { return getToken(FTFileParser.EOF, 0); }
		public OldStyleHeaderContext oldStyleHeader() {
			return getRuleContext(OldStyleHeaderContext.class,0);
		}
		public DelimitersContext delimiters() {
			return getRuleContext(DelimitersContext.class,0);
		}
		public List<TerminalNode> IDS() { return getTokens(FTFileParser.IDS); }
		public TerminalNode IDS(int i) {
			return getToken(FTFileParser.IDS, i);
		}
		public List<DefContext> def() {
			return getRuleContexts(DefContext.class);
		}
		public DefContext def(int i) {
			return getRuleContext(DefContext.class,i);
		}
		public GroupContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public GroupContext(ParserRuleContext parent, int invokingState, FTFile ftFile, String prefix) {
			super(parent, invokingState);
			this.ftFile = ftFile;
			this.prefix = prefix;
		}
		@Override public int getRuleIndex() { return RULE_group; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterGroup(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitGroup(this);
		}
	}

	public final GroupContext group(FTFile ftFile,String prefix) throws RecognitionException {
		GroupContext _localctx = new GroupContext(_ctx, getState(), ftFile, prefix);
		enterRule(_localctx, 0, RULE_group);

		FTFileLexer lexer = (FTFileLexer)_input.getTokenSource();
		this.ftFile = lexer.ftFile = ftFile;

		int _la;
		try {
			int _alt;
			enterOuterAlt(_localctx, 1);
			{
			setState(13);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__1) {
				{
				setState(12);
				oldStyleHeader();
				}
			}

			setState(16);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__7) {
				{
				setState(15);
				delimiters();
				}
			}

			setState(23);
			_errHandler.sync(this);
			_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			while ( _alt!=1 && _alt!=org.antlr.v4.runtime.atn.ATN.INVALID_ALT_NUMBER ) {
				if ( _alt==1+1 ) {
					{
					{
					setState(18);
					match(T__0);
					setState(19);
					((GroupContext)_localctx).IDS = match(IDS);
					ftFile.importTemplates(((GroupContext)_localctx).IDS);
					}
					}
				}
				setState(25);
				_errHandler.sync(this);
				_alt = getInterpreter().adaptivePredict(_input,2,_ctx);
			}
			setState(29);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__8 || _la==ID) {
				{
				{
				setState(26);
				def(prefix);
				}
				}
				setState(31);
				_errHandler.sync(this);
				_la = _input.LA(1);
			}
			setState(32);
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

	public static class OldStyleHeaderContext extends ParserRuleContext {
		public List<TerminalNode> ID() { return getTokens(FTFileParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FTFileParser.ID, i);
		}
		public OldStyleHeaderContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_oldStyleHeader; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterOldStyleHeader(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitOldStyleHeader(this);
		}
	}

	public final OldStyleHeaderContext oldStyleHeader() throws RecognitionException {
		OldStyleHeaderContext _localctx = new OldStyleHeaderContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_oldStyleHeader);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(34);
			match(T__1);
			setState(35);
			match(ID);
			setState(38);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__2) {
				{
				setState(36);
				match(T__2);
				setState(37);
				match(ID);
				}
			}

			setState(49);
			_errHandler.sync(this);
			_la = _input.LA(1);
			if (_la==T__3) {
				{
				setState(40);
				match(T__3);
				setState(41);
				match(ID);
				setState(46);
				_errHandler.sync(this);
				_la = _input.LA(1);
				while (_la==T__4) {
					{
					{
					setState(42);
					match(T__4);
					setState(43);
					match(ID);
					}
					}
					setState(48);
					_errHandler.sync(this);
					_la = _input.LA(1);
				}
				}
			}

			setState(51);
			match(T__5);
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

	public static class GroupNameContext extends ParserRuleContext {
		public String name;
		public Token a;
		public List<TerminalNode> ID() { return getTokens(FTFileParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FTFileParser.ID, i);
		}
		public GroupNameContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_groupName; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterGroupName(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitGroupName(this);
		}
	}

	public final GroupNameContext groupName() throws RecognitionException {
		GroupNameContext _localctx = new GroupNameContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_groupName);
		StringBuilder buf = new StringBuilder();
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(53);
			((GroupNameContext)_localctx).a = match(ID);
			buf.append((((GroupNameContext)_localctx).a!=null?((GroupNameContext)_localctx).a.getText():null));
			setState(60);
			_errHandler.sync(this);
			_la = _input.LA(1);
			while (_la==T__6) {
				{
				{
				setState(55);
				match(T__6);
				setState(56);
				((GroupNameContext)_localctx).a = match(ID);
				buf.append((((GroupNameContext)_localctx).a!=null?((GroupNameContext)_localctx).a.getText():null));
				}
				}
				setState(62);
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

	public static class DelimitersContext extends ParserRuleContext {
		public Token a;
		public Token b;
		public List<TerminalNode> STRING() { return getTokens(FTFileParser.STRING); }
		public TerminalNode STRING(int i) {
			return getToken(FTFileParser.STRING, i);
		}
		public DelimitersContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override public int getRuleIndex() { return RULE_delimiters; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterDelimiters(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitDelimiters(this);
		}
	}

	public final DelimitersContext delimiters() throws RecognitionException {
		DelimitersContext _localctx = new DelimitersContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_delimiters);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(63);
			match(T__7);
			setState(64);
			((DelimitersContext)_localctx).a = match(STRING);
			setState(65);
			match(T__4);
			setState(66);
			((DelimitersContext)_localctx).b = match(STRING);

			     	ftFile.delimiterStartChar=((DelimitersContext)_localctx).a.getText().charAt(1);
			        ftFile.delimiterStopChar=((DelimitersContext)_localctx).b.getText().charAt(1);

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

	public static class DefContext extends ParserRuleContext {
		public String prefix;
		public TemplateDefContext templateDef() {
			return getRuleContext(TemplateDefContext.class,0);
		}
		public DefContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public DefContext(ParserRuleContext parent, int invokingState, String prefix) {
			super(parent, invokingState);
			this.prefix = prefix;
		}
		@Override public int getRuleIndex() { return RULE_def; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitDef(this);
		}
	}

	public final DefContext def(String prefix) throws RecognitionException {
		DefContext _localctx = new DefContext(_ctx, getState(), prefix);
		enterRule(_localctx, 8, RULE_def);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(69);
			templateDef(prefix);
			}
		}
		catch (RecognitionException re) {



		}
		finally {
			exitRule();
		}
		return _localctx;
	}

	public static class TemplateDefContext extends ParserRuleContext {
		public String prefix;
		public Token enclosing;
		public Token name;
		public Token STRING;
		public Token BIGSTRING;
		public Token BIGSTRING_NO_NL;
		public TerminalNode STRING() { return getToken(FTFileParser.STRING, 0); }
		public TerminalNode BIGSTRING() { return getToken(FTFileParser.BIGSTRING, 0); }
		public TerminalNode BIGSTRING_NO_NL() { return getToken(FTFileParser.BIGSTRING_NO_NL, 0); }
		public List<TerminalNode> ID() { return getTokens(FTFileParser.ID); }
		public TerminalNode ID(int i) {
			return getToken(FTFileParser.ID, i);
		}
		public TemplateDefContext(ParserRuleContext parent, int invokingState) { super(parent, invokingState); }
		public TemplateDefContext(ParserRuleContext parent, int invokingState, String prefix) {
			super(parent, invokingState);
			this.prefix = prefix;
		}
		@Override public int getRuleIndex() { return RULE_templateDef; }
		@Override
		public void enterRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).enterTemplateDef(this);
		}
		@Override
		public void exitRule(ParseTreeListener listener) {
			if ( listener instanceof FTFileListener ) ((FTFileListener)listener).exitTemplateDef(this);
		}
	}

	public final TemplateDefContext templateDef(String prefix) throws RecognitionException {
		TemplateDefContext _localctx = new TemplateDefContext(_ctx, getState(), prefix);
		enterRule(_localctx, 10, RULE_templateDef);

		    String template=null;
		    int n=0; // num char to strip from left, right of template def

		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(76);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__8:
				{
				setState(71);
				match(T__8);
				setState(72);
				((TemplateDefContext)_localctx).enclosing = match(ID);
				setState(73);
				match(T__6);
				setState(74);
				((TemplateDefContext)_localctx).name = match(ID);
				}
				break;
			case ID:
				{
				setState(75);
				((TemplateDefContext)_localctx).name = match(ID);
				}
				break;
			default:
				throw new NoViableAltException(this);
			}
			setState(78);
			match(T__9);
			Token templateToken = _input.LT(1);
			setState(87);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case STRING:
				{
				setState(80);
				((TemplateDefContext)_localctx).STRING = match(STRING);
				template=(((TemplateDefContext)_localctx).STRING!=null?((TemplateDefContext)_localctx).STRING.getText():null); n=1;
				}
				break;
			case BIGSTRING:
				{
				setState(82);
				((TemplateDefContext)_localctx).BIGSTRING = match(BIGSTRING);
				template=(((TemplateDefContext)_localctx).BIGSTRING!=null?((TemplateDefContext)_localctx).BIGSTRING.getText():null); n=2;
				}
				break;
			case BIGSTRING_NO_NL:
				{
				setState(84);
				((TemplateDefContext)_localctx).BIGSTRING_NO_NL = match(BIGSTRING_NO_NL);
				template=(((TemplateDefContext)_localctx).BIGSTRING_NO_NL!=null?((TemplateDefContext)_localctx).BIGSTRING_NO_NL.getText():null); n=2;
				}
				break;
			case EOF:
			case T__8:
			case ID:
				{

					    	template = "";
					    	String msg = "missing template at '"+_input.LT(1).getText()+"'";

				}
				break;
			default:
				throw new NoViableAltException(this);
			}

				    if ( (((TemplateDefContext)_localctx).name!=null?((TemplateDefContext)_localctx).name.getTokenIndex():0) >= 0 ) { // if ID missing
						template = MiscUtil.strip(template, n);
						String templateName = (((TemplateDefContext)_localctx).name!=null?((TemplateDefContext)_localctx).name.getText():null);
						if ( prefix.length()>0 ) templateName = prefix+(((TemplateDefContext)_localctx).name!=null?((TemplateDefContext)_localctx).name.getText():null);
						ftFile.defineTemplate(templateName, templateToken,
													 template, ((TemplateDefContext)_localctx).name);
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
		"\3\u608b\ua72a\u8133\ub9ed\u417c\u3be7\u7786\u5964\3\26^\4\2\t\2\4\3\t"+
		"\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\3\2\5\2\20\n\2\3\2\5\2\23\n\2\3\2\3"+
		"\2\3\2\7\2\30\n\2\f\2\16\2\33\13\2\3\2\7\2\36\n\2\f\2\16\2!\13\2\3\2\3"+
		"\2\3\3\3\3\3\3\3\3\5\3)\n\3\3\3\3\3\3\3\3\3\7\3/\n\3\f\3\16\3\62\13\3"+
		"\5\3\64\n\3\3\3\3\3\3\4\3\4\3\4\3\4\3\4\7\4=\n\4\f\4\16\4@\13\4\3\5\3"+
		"\5\3\5\3\5\3\5\3\5\3\6\3\6\3\7\3\7\3\7\3\7\3\7\5\7O\n\7\3\7\3\7\3\7\3"+
		"\7\3\7\3\7\3\7\3\7\3\7\5\7Z\n\7\3\7\3\7\3\7\3\31\2\b\2\4\6\b\n\f\2\2\2"+
		"c\2\17\3\2\2\2\4$\3\2\2\2\6\67\3\2\2\2\bA\3\2\2\2\nG\3\2\2\2\fN\3\2\2"+
		"\2\16\20\5\4\3\2\17\16\3\2\2\2\17\20\3\2\2\2\20\22\3\2\2\2\21\23\5\b\5"+
		"\2\22\21\3\2\2\2\22\23\3\2\2\2\23\31\3\2\2\2\24\25\7\3\2\2\25\26\7\16"+
		"\2\2\26\30\b\2\1\2\27\24\3\2\2\2\30\33\3\2\2\2\31\32\3\2\2\2\31\27\3\2"+
		"\2\2\32\37\3\2\2\2\33\31\3\2\2\2\34\36\5\n\6\2\35\34\3\2\2\2\36!\3\2\2"+
		"\2\37\35\3\2\2\2\37 \3\2\2\2 \"\3\2\2\2!\37\3\2\2\2\"#\7\2\2\3#\3\3\2"+
		"\2\2$%\7\4\2\2%(\7\r\2\2&\'\7\5\2\2\')\7\r\2\2(&\3\2\2\2()\3\2\2\2)\63"+
		"\3\2\2\2*+\7\6\2\2+\60\7\r\2\2,-\7\7\2\2-/\7\r\2\2.,\3\2\2\2/\62\3\2\2"+
		"\2\60.\3\2\2\2\60\61\3\2\2\2\61\64\3\2\2\2\62\60\3\2\2\2\63*\3\2\2\2\63"+
		"\64\3\2\2\2\64\65\3\2\2\2\65\66\7\b\2\2\66\5\3\2\2\2\678\7\r\2\28>\b\4"+
		"\1\29:\7\t\2\2:;\7\r\2\2;=\b\4\1\2<9\3\2\2\2=@\3\2\2\2><\3\2\2\2>?\3\2"+
		"\2\2?\7\3\2\2\2@>\3\2\2\2AB\7\n\2\2BC\7\17\2\2CD\7\7\2\2DE\7\17\2\2EF"+
		"\b\5\1\2F\t\3\2\2\2GH\5\f\7\2H\13\3\2\2\2IJ\7\13\2\2JK\7\r\2\2KL\7\t\2"+
		"\2LO\7\r\2\2MO\7\r\2\2NI\3\2\2\2NM\3\2\2\2OP\3\2\2\2PQ\7\f\2\2QY\b\7\1"+
		"\2RS\7\17\2\2SZ\b\7\1\2TU\7\21\2\2UZ\b\7\1\2VW\7\20\2\2WZ\b\7\1\2XZ\b"+
		"\7\1\2YR\3\2\2\2YT\3\2\2\2YV\3\2\2\2YX\3\2\2\2Z[\3\2\2\2[\\\b\7\1\2\\"+
		"\r\3\2\2\2\f\17\22\31\37(\60\63>NY";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}