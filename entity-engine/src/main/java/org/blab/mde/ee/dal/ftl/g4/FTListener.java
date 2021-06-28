// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FT.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;

import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FTParser}.
 */
public interface FTListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FTParser#interpreter}.
	 * @param ctx the parse tree
	 */
	void enterInterpreter(FTParser.InterpreterContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTParser#interpreter}.
	 * @param ctx the parse tree
	 */
	void exitInterpreter(FTParser.InterpreterContext ctx);
}