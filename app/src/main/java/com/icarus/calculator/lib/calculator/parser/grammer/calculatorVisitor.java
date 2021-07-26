// Generated from src/org/xing/calc/parser/grammer/calculator.g4 by ANTLR 4.6
package com.icarus.calculator.lib.calculator.parser.grammer;
import org.antlr.v4.runtime.tree.ParseTreeVisitor;

/**
 * This interface defines a complete generic visitor for a parse tree produced
 * by {@link calculatorParser}.
 *
 * @param <T> The return type of the visit operation. Use {@link Void} for
 * operations with no return type.
 */
public interface calculatorVisitor<T> extends ParseTreeVisitor<T> {
	/**
	 * Visit a parse tree produced by {@link calculatorParser#expression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitExpression(calculatorParser.ExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#multiplyingExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitMultiplyingExpression(calculatorParser.MultiplyingExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#powExpression}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPowExpression(calculatorParser.PowExpressionContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#atom}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitAtom(calculatorParser.AtomContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#function}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFunction(calculatorParser.FunctionContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#funcname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncname(calculatorParser.FuncnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#funcnameEx}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitFuncnameEx(calculatorParser.FuncnameExContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#postFuncname}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitPostFuncname(calculatorParser.PostFuncnameContext ctx);
	/**
	 * Visit a parse tree produced by {@link calculatorParser#number}.
	 * @param ctx the parse tree
	 * @return the visitor result
	 */
	T visitNumber(calculatorParser.NumberContext ctx);
}