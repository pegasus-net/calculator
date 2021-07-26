// Generated from src/org/xing/calc/cmd/grammer/cmd.g4 by ANTLR 4.6
package com.icarus.calculator.lib.calculator.cmd.grammer;

import org.antlr.v4.runtime.NoViableAltException;
import org.antlr.v4.runtime.Parser;
import org.antlr.v4.runtime.ParserRuleContext;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.RuntimeMetaData;
import org.antlr.v4.runtime.Token;
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.Vocabulary;
import org.antlr.v4.runtime.VocabularyImpl;
import org.antlr.v4.runtime.atn.ATN;
import org.antlr.v4.runtime.atn.ATNDeserializer;
import org.antlr.v4.runtime.atn.ParserATNSimulator;
import org.antlr.v4.runtime.atn.PredictionContextCache;
import org.antlr.v4.runtime.dfa.DFA;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

@SuppressWarnings({"all", "warnings", "unchecked", "unused", "cast"})
public class cmdParser extends Parser {
	static { RuntimeMetaData.checkVersion("4.6", RuntimeMetaData.VERSION); }

	protected static final DFA[] _decisionToDFA;
	protected static final PredictionContextCache _sharedContextCache =
		new PredictionContextCache();
	public static final int
		T__0=1, T__1=2, T__2=3, T__3=4, T__4=5, T__5=6, T__6=7, T__7=8, T__8=9, 
		T__9=10, T__10=11, T__11=12, T__12=13, T__13=14, T__14=15, T__15=16, T__16=17, 
		T__17=18, T__18=19, T__19=20, T__20=21, T__21=22, T__22=23, WS=24;
	public static final int
		RULE_command = 0, RULE_qingping = 1, RULE_goback = 2, RULE_help = 3, RULE_update = 4, 
		RULE_theme = 5, RULE_engine = 6, RULE_close = 7, RULE_stop = 8;
	public static final String[] ruleNames = {
		"command", "qingping", "goback", "help", "update", "theme", "engine", 
		"close", "stop"
	};

	private static final String[] _LITERAL_NAMES = {
		null, "'清屏'", "'清空'", "'清除'", "'全部'", "'撤销'", "'取消'", "'倒退'", "'后退'", 
		"'删除'", "'帮助'", "'示例'", "'说明'", "'升级'", "'版本'", "'主题'", "'风格'", "'背景'", 
		"'引擎'", "'百度'", "'讯飞'", "'退出'", "'关闭'", "'暂停'"
	};
	private static final String[] _SYMBOLIC_NAMES = {
		null, null, null, null, null, null, null, null, null, null, null, null, 
		null, null, null, null, null, null, null, null, null, null, null, null, 
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

	@Override
	public String getGrammarFileName() { return "cmd.g4"; }

	@Override
	public String[] getRuleNames() { return ruleNames; }

	@Override
	public String getSerializedATN() { return _serializedATN; }

	@Override
	public ATN getATN() { return _ATN; }

	public cmdParser(TokenStream input) {
		super(input);
		_interp = new ParserATNSimulator(this,_ATN,_decisionToDFA,_sharedContextCache);
	}
	public static class CommandContext extends ParserRuleContext {
		public QingpingContext qingping() {
			return getRuleContext(QingpingContext.class,0);
		}
		public GobackContext goback() {
			return getRuleContext(GobackContext.class,0);
		}
		public HelpContext help() {
			return getRuleContext(HelpContext.class,0);
		}
		public UpdateContext update() {
			return getRuleContext(UpdateContext.class,0);
		}
		public ThemeContext theme() {
			return getRuleContext(ThemeContext.class,0);
		}
		public EngineContext engine() {
			return getRuleContext(EngineContext.class,0);
		}
		public CloseContext close() {
			return getRuleContext(CloseContext.class,0);
		}
		public StopContext stop() {
			return getRuleContext(StopContext.class,0);
		}
		public CommandContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_command; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitCommand(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CommandContext command() throws RecognitionException {
		CommandContext _localctx = new CommandContext(_ctx, getState());
		enterRule(_localctx, 0, RULE_command);
		try {
			setState(26);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
			case T__1:
			case T__2:
			case T__3:
				enterOuterAlt(_localctx, 1);
				{
				setState(18);
				qingping();
				}
				break;
			case T__4:
			case T__5:
			case T__6:
			case T__7:
			case T__8:
				enterOuterAlt(_localctx, 2);
				{
				setState(19);
				goback();
				}
				break;
			case T__9:
			case T__10:
			case T__11:
				enterOuterAlt(_localctx, 3);
				{
				setState(20);
				help();
				}
				break;
			case T__12:
			case T__13:
				enterOuterAlt(_localctx, 4);
				{
				setState(21);
				update();
				}
				break;
			case T__14:
			case T__15:
			case T__16:
				enterOuterAlt(_localctx, 5);
				{
				setState(22);
				theme();
				}
				break;
			case T__17:
			case T__18:
			case T__19:
				enterOuterAlt(_localctx, 6);
				{
				setState(23);
				engine();
				}
				break;
			case T__20:
			case T__21:
				enterOuterAlt(_localctx, 7);
				{
				setState(24);
				close();
				}
				break;
			case T__22:
				enterOuterAlt(_localctx, 8);
				{
				setState(25);
				stop();
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

	public static class QingpingContext extends ParserRuleContext {
		public GobackContext goback() {
			return getRuleContext(GobackContext.class,0);
		}
		public QingpingContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_qingping; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitQingping(this);
			else return visitor.visitChildren(this);
		}
	}

	public final QingpingContext qingping() throws RecognitionException {
		QingpingContext _localctx = new QingpingContext(_ctx, getState());
		enterRule(_localctx, 2, RULE_qingping);
		try {
			setState(33);
			_errHandler.sync(this);
			switch (_input.LA(1)) {
			case T__0:
				enterOuterAlt(_localctx, 1);
				{
				setState(28);
				match(T__0);
				}
				break;
			case T__1:
				enterOuterAlt(_localctx, 2);
				{
				setState(29);
				match(T__1);
				}
				break;
			case T__2:
				enterOuterAlt(_localctx, 3);
				{
				setState(30);
				match(T__2);
				}
				break;
			case T__3:
				enterOuterAlt(_localctx, 4);
				{
				{
				setState(31);
				match(T__3);
				setState(32);
				goback();
				}
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

	public static class GobackContext extends ParserRuleContext {
		public GobackContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_goback; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitGoback(this);
			else return visitor.visitChildren(this);
		}
	}

	public final GobackContext goback() throws RecognitionException {
		GobackContext _localctx = new GobackContext(_ctx, getState());
		enterRule(_localctx, 4, RULE_goback);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(35);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__4) | (1L << T__5) | (1L << T__6) | (1L << T__7) | (1L << T__8))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class HelpContext extends ParserRuleContext {
		public HelpContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_help; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitHelp(this);
			else return visitor.visitChildren(this);
		}
	}

	public final HelpContext help() throws RecognitionException {
		HelpContext _localctx = new HelpContext(_ctx, getState());
		enterRule(_localctx, 6, RULE_help);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(37);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__9) | (1L << T__10) | (1L << T__11))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class UpdateContext extends ParserRuleContext {
		public UpdateContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_update; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitUpdate(this);
			else return visitor.visitChildren(this);
		}
	}

	public final UpdateContext update() throws RecognitionException {
		UpdateContext _localctx = new UpdateContext(_ctx, getState());
		enterRule(_localctx, 8, RULE_update);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(39);
			_la = _input.LA(1);
			if ( !(_la==T__12 || _la==T__13) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class ThemeContext extends ParserRuleContext {
		public ThemeContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_theme; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitTheme(this);
			else return visitor.visitChildren(this);
		}
	}

	public final ThemeContext theme() throws RecognitionException {
		ThemeContext _localctx = new ThemeContext(_ctx, getState());
		enterRule(_localctx, 10, RULE_theme);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(41);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__14) | (1L << T__15) | (1L << T__16))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class EngineContext extends ParserRuleContext {
		public EngineContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_engine; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitEngine(this);
			else return visitor.visitChildren(this);
		}
	}

	public final EngineContext engine() throws RecognitionException {
		EngineContext _localctx = new EngineContext(_ctx, getState());
		enterRule(_localctx, 12, RULE_engine);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(43);
			_la = _input.LA(1);
			if ( !((((_la) & ~0x3f) == 0 && ((1L << _la) & ((1L << T__17) | (1L << T__18) | (1L << T__19))) != 0)) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class CloseContext extends ParserRuleContext {
		public CloseContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_close; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitClose(this);
			else return visitor.visitChildren(this);
		}
	}

	public final CloseContext close() throws RecognitionException {
		CloseContext _localctx = new CloseContext(_ctx, getState());
		enterRule(_localctx, 14, RULE_close);
		int _la;
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(45);
			_la = _input.LA(1);
			if ( !(_la==T__20 || _la==T__21) ) {
			_errHandler.recoverInline(this);
			}
			else {
				if ( _input.LA(1)== Token.EOF ) matchedEOF = true;
				_errHandler.reportMatch(this);
				consume();
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

	public static class StopContext extends ParserRuleContext {
		public StopContext(ParserRuleContext parent, int invokingState) {
			super(parent, invokingState);
		}
		@Override
        public int getRuleIndex() { return RULE_stop; }
		@Override
		public <T> T accept(ParseTreeVisitor<? extends T> visitor) {
			if ( visitor instanceof cmdVisitor ) return ((cmdVisitor<? extends T>)visitor).visitStop(this);
			else return visitor.visitChildren(this);
		}
	}

	public final StopContext stop() throws RecognitionException {
		StopContext _localctx = new StopContext(_ctx, getState());
		enterRule(_localctx, 16, RULE_stop);
		try {
			enterOuterAlt(_localctx, 1);
			{
			setState(47);
			match(T__22);
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
		"\3\u0430\ud6d1\u8206\uad2d\u4417\uaef1\u8d80\uaadd\3\32\64\4\2\t\2\4\3"+
		"\t\3\4\4\t\4\4\5\t\5\4\6\t\6\4\7\t\7\4\b\t\b\4\t\t\t\4\n\t\n\3\2\3\2\3"+
		"\2\3\2\3\2\3\2\3\2\3\2\5\2\35\n\2\3\3\3\3\3\3\3\3\3\3\5\3$\n\3\3\4\3\4"+
		"\3\5\3\5\3\6\3\6\3\7\3\7\3\b\3\b\3\t\3\t\3\n\3\n\3\n\2\2\13\2\4\6\b\n"+
		"\f\16\20\22\2\b\3\2\7\13\3\2\f\16\3\2\17\20\3\2\21\23\3\2\24\26\3\2\27"+
		"\30\64\2\34\3\2\2\2\4#\3\2\2\2\6%\3\2\2\2\b\'\3\2\2\2\n)\3\2\2\2\f+\3"+
		"\2\2\2\16-\3\2\2\2\20/\3\2\2\2\22\61\3\2\2\2\24\35\5\4\3\2\25\35\5\6\4"+
		"\2\26\35\5\b\5\2\27\35\5\n\6\2\30\35\5\f\7\2\31\35\5\16\b\2\32\35\5\20"+
		"\t\2\33\35\5\22\n\2\34\24\3\2\2\2\34\25\3\2\2\2\34\26\3\2\2\2\34\27\3"+
		"\2\2\2\34\30\3\2\2\2\34\31\3\2\2\2\34\32\3\2\2\2\34\33\3\2\2\2\35\3\3"+
		"\2\2\2\36$\7\3\2\2\37$\7\4\2\2 $\7\5\2\2!\"\7\6\2\2\"$\5\6\4\2#\36\3\2"+
		"\2\2#\37\3\2\2\2# \3\2\2\2#!\3\2\2\2$\5\3\2\2\2%&\t\2\2\2&\7\3\2\2\2\'"+
		"(\t\3\2\2(\t\3\2\2\2)*\t\4\2\2*\13\3\2\2\2+,\t\5\2\2,\r\3\2\2\2-.\t\6"+
		"\2\2.\17\3\2\2\2/\60\t\7\2\2\60\21\3\2\2\2\61\62\7\31\2\2\62\23\3\2\2"+
		"\2\4\34#";
	public static final ATN _ATN =
		new ATNDeserializer().deserialize(_serializedATN.toCharArray());
	static {
		_decisionToDFA = new DFA[_ATN.getNumberOfDecisions()];
		for (int i = 0; i < _ATN.getNumberOfDecisions(); i++) {
			_decisionToDFA[i] = new DFA(_ATN.getDecisionState(i), i);
		}
	}
}