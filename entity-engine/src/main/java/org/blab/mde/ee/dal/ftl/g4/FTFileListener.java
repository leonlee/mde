// Generated from /Users/shaoyuqi/gitsrc/mde/mde/entity-engine/src/main/java/org/blab/mde/ee/dal/ftl/g4/FTFile.g4 by ANTLR 4.7
package org.blab.mde.ee.dal.ftl.g4;


import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link FTFileParser}.
 */
public interface FTFileListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link FTFileParser#group}.
	 * @param ctx the parse tree
	 */
	void enterGroup(FTFileParser.GroupContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#group}.
	 * @param ctx the parse tree
	 */
	void exitGroup(FTFileParser.GroupContext ctx);
	/**
	 * Enter a parse tree produced by {@link FTFileParser#oldStyleHeader}.
	 * @param ctx the parse tree
	 */
	void enterOldStyleHeader(FTFileParser.OldStyleHeaderContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#oldStyleHeader}.
	 * @param ctx the parse tree
	 */
	void exitOldStyleHeader(FTFileParser.OldStyleHeaderContext ctx);
	/**
	 * Enter a parse tree produced by {@link FTFileParser#groupName}.
	 * @param ctx the parse tree
	 */
	void enterGroupName(FTFileParser.GroupNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#groupName}.
	 * @param ctx the parse tree
	 */
	void exitGroupName(FTFileParser.GroupNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link FTFileParser#delimiters}.
	 * @param ctx the parse tree
	 */
	void enterDelimiters(FTFileParser.DelimitersContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#delimiters}.
	 * @param ctx the parse tree
	 */
	void exitDelimiters(FTFileParser.DelimitersContext ctx);
	/**
	 * Enter a parse tree produced by {@link FTFileParser#def}.
	 * @param ctx the parse tree
	 */
	void enterDef(FTFileParser.DefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#def}.
	 * @param ctx the parse tree
	 */
	void exitDef(FTFileParser.DefContext ctx);
	/**
	 * Enter a parse tree produced by {@link FTFileParser#templateDef}.
	 * @param ctx the parse tree
	 */
	void enterTemplateDef(FTFileParser.TemplateDefContext ctx);
	/**
	 * Exit a parse tree produced by {@link FTFileParser#templateDef}.
	 * @param ctx the parse tree
	 */
	void exitTemplateDef(FTFileParser.TemplateDefContext ctx);
}