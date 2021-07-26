package com.icarus.calculator.lib.calculator.parser;



import com.icarus.calculator.lib.calculator.parser.grammer.calculatorBaseVisitor;
import com.icarus.calculator.lib.calculator.parser.grammer.calculatorParser;

import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.TerminalNode;

import java.util.Stack;

//import org.xing.calc.parser.grammer.calculatorBaseVisitor;
//import org.xing.calc.parser.grammer.calculatorParser;

/**
 * Created by xing on 2017/1/1.
 */

public class CalculatorLatexExprVisitor extends calculatorBaseVisitor<String> {
    protected NumberParser numParser;

    public CalculatorLatexExprVisitor() {
        numParser = new NumberParser();
    }

    public enum AtomType{
        None, PosNumber, NegNumber, Sqrt, Frac, Log, Brack, Pow, Mixed
    }

    private boolean bracketsLeggal(String expr) {
        Stack<Character> brackets = new Stack<>();
        for(int i=0;i<expr.length();i++) {
            Character c = expr.charAt(i);
            if(c == '(') {
                brackets.add(c);
            } else if(c== ')') {
                if((!brackets.empty()) && brackets.peek() == '(') {
                    brackets.pop();
                }else {
                    return false;
                }
            }
        }

        if(brackets.empty()) return true;
        return false;
    }

    protected String trimBrackets(String expr) {
        if(expr == null) return null;

        if(expr.startsWith("(") && expr.endsWith(")")) {
            String substr = expr.substring(1, expr.length()-1);
            if(bracketsLeggal(substr)) return substr;
        }

        return expr;
    }


    public static AtomType getAtomType(String expr) {
        if(expr == null) return AtomType.None;

        if(expr.startsWith("-")) {
            return AtomType.NegNumber;
        }else if(expr.startsWith("\\\\sqrt")) {
            return AtomType.Sqrt;
        }else if(expr.startsWith("\\\\frac")) {
            return AtomType.Frac;
        }else if(expr.startsWith("log")) {
            return AtomType.Log;
        }else if(expr.startsWith("(") && expr.endsWith(")")) {
            return AtomType.Brack;
        }else if(expr.contains("^")) {
            return AtomType.Pow;
        }else if(expr.contains("\\\\frac")) {
            return AtomType.Frac;
        }else if(expr.contains("+") || expr.contains("-")
                    || expr.contains("÷") || expr.contains("×") ) {
            return AtomType.Mixed;
        }
        return AtomType.PosNumber;
    }

    public String[] getBrackExpr(String expr) {
        String brackResult = expr;
        String nonbrackResult = expr;
        AtomType atomType = getAtomType(expr);
        if(atomType == AtomType.NegNumber || atomType == AtomType.Frac
                || atomType == AtomType.Pow || atomType == AtomType.Log
                || atomType == AtomType.Sqrt || atomType == AtomType.Mixed) {
            brackResult = "("+expr + ")";
        }else if(atomType == AtomType.Brack) {
            nonbrackResult = trimBrackets(expr);
        }

        return new String[] {brackResult, nonbrackResult};
    }

    @Override
    public String visitExpression(calculatorParser.ExpressionContext ctx) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<ctx.getChildCount();i++) {
            ParseTree child = ctx.getChild(i);

            if(child instanceof TerminalNode) {
                int type = ((TerminalNode) child).getSymbol().getType();
                if(type == calculatorParser.PLUS) {
                    result.append("+");
                } else {
                    result.append("-");
                }
            }else {
                String expr = visit(child);
                AtomType atomType = getAtomType(expr);
                if(atomType == AtomType.NegNumber && result.length() > 0) {
                    result.append("("+expr+")");
                }else {
                    result.append(expr);
                }
            }
        }
        return result.toString();
    }

    @Override
    public String visitMultiplyingExpression(
            calculatorParser.MultiplyingExpressionContext ctx) {
        StringBuilder result = new StringBuilder();
        for(int i=0;i<ctx.getChildCount();i++) {
            ParseTree child = ctx.getChild(i);

            if(child instanceof TerminalNode) {
                int type = ((TerminalNode) child).getSymbol().getType();
                if(type == calculatorParser.DIV) {
                    result.append("/");
                } else {
                    result.append("*");
                }
            }else {
                String expr = visit(child);
                AtomType atomType = getAtomType(expr);
                if(atomType == AtomType.NegNumber && result.length() > 0) {
                    result.append("("+expr+")");
                }else {
                    result.append(expr);
                }
            }
        }
        return result.toString();
    }

    @Override
    public String visitPowExpression(calculatorParser.PowExpressionContext ctx) {
        int index = 0;
        String result = visit(ctx.getChild(index++));

        while (index < ctx.getChildCount()) {
            String[] brackExpr = getBrackExpr(result);
            TerminalNode node = (TerminalNode) ctx.getChild(index);
            if(node.getSymbol().getType() == calculatorParser.DE) {
                if (ctx.getChild(index + 1) instanceof TerminalNode) {
                    int type = ((TerminalNode) ctx.getChild(index + 1)).getSymbol()
                            .getType();
                    if (type == calculatorParser.PINGFANG) {
                        result = "{" + brackExpr[0] + "}^2";
                    } else if (type == calculatorParser.LIFANG) {
                        result = "{" + brackExpr[0] + "}^3";
                    } else if (type == calculatorParser.KAIFANG) {
                        result = "\\\\sqrt{" + brackExpr[1] + "}";
                    } else {
                        System.err.println("未处理的终端节点:visitChinaPowExpression");
                    }
                    index += 2;
                } else {
                    String pow = visit(ctx.getChild(index + 1));
                    result = "{" + brackExpr[0] + "}^{" + pow + "}";
                    index += 3;
                }
            } else {
                String pow = visit(ctx.getChild(index + 1));
                result = "{" + brackExpr[0] + "}^{" + pow + "}";
                index += 2;
            }
        }
        return result;
    }

    @Override
    public String visitAtom(calculatorParser.AtomContext ctx) {
        if(ctx.MINUS() != null) {
            return "-" + visit(ctx.getChild(1));
        }else if(ctx.PLUS() != null) {
            return visit(ctx.getChild(1));
        }if(ctx.getChildCount() == 3) {
            if(ctx.FRAC() != null) {
                String expr0 = visit(ctx.getChild(0));
                String expr2 = visit(ctx.getChild(2));

                //对于负的分数特殊处理
                String negTag  = "";
                boolean neg0 = expr0.startsWith("-");
                boolean neg2 = expr2.startsWith("-");
                if(neg0 != neg2) {
                    if(neg0) {
                        expr0 = expr0.substring(1);
                    }else {
                        expr2 = expr2.substring(1);
                    }
                    negTag = "-";
                }

                String[] brackExpr0 = getBrackExpr(expr0);
                String[] brackExpr2 = getBrackExpr(expr2);
                if(ctx.FRAC().getText().equals("分之")) {
                    return negTag+"\\\\frac{"+brackExpr2[1]+"}{"+brackExpr0[1]+"}";
                }else {
                    return negTag+"\\\\frac{"+brackExpr0[1]+"}{"+brackExpr2[1]+"}";
                }
            }else {
                String[] brackExpr = getBrackExpr(visit(ctx.expression()));
                return "("+brackExpr[1]+")";
            }
        }else if(ctx.getChildCount() == 5) {
            String expr0 = visit(ctx.getChild(0));
            String expr2 = visit(ctx.getChild(2));
            String expr4 = visit(ctx.getChild(4));
            //三又2/3 这种被语音识别引擎处理过的表达方式
            if(ctx.getChild(3).getText().equals("/")) {
                expr2 = visit(ctx.getChild(4));
                expr4 = visit(ctx.getChild(2));
            }
            return expr0+"\\\\frac{"+expr4+"}{"+expr2+"}";
        }else {
            return visit(ctx.getChild(0));
        }
    }

    @Override
    public String visitFunction(calculatorParser.FunctionContext ctx) {
        if(ctx.funcnameEx() != null) {
            calculatorParser.FuncnameExContext func = ctx.funcnameEx();
            String firstNum = visit(ctx.getChild(0));
            String secondNum = visit(ctx.getChild(2));
            if(firstNum == null || secondNum == null) return null;

            String[] brackFirstNum = getBrackExpr(firstNum);
            String[] brackSecondNum = getBrackExpr(secondNum);
            if(func.DUISHU() != null) {
                return "log_{"+brackFirstNum[0]+"}"+brackSecondNum[0];
            }else if(func.GENHAO() != null) {
                return "\\\\sqrt["+brackFirstNum[0]+"]{"+brackSecondNum[1]+"}";
            }
        } else if(ctx.funcname() != null){
            String expr = visit(ctx.getChild(1));
            calculatorParser.FuncnameContext func = ctx.funcname();
            if(expr == null) return null;

            String[] brackExpr = getBrackExpr(expr);
            if(func.SIN() != null) {
                return "sin("+brackExpr[1]+")";
            }else if(func.COS() != null) {
                return "cos("+brackExpr[1]+")";
            }else if(func.TAN() != null) {
                return "tan("+brackExpr[1]+")";
            }else if(func.ASIN() != null) {
                return "asin("+brackExpr[1]+")";
            }else if(func.ACOS() != null) {
                return "acos("+brackExpr[1]+")";
            }else if(func.ATAN() != null) {
                return "atan("+brackExpr[1]+")";
            }else if(func.LG() != null) {
                return "lg("+brackExpr[1]+")";
            }else if(func.LOG() != null) {
                return "log("+brackExpr[1]+")";
            }else if(func.LN() != null) {
                return "ln("+brackExpr[1]+")";
            }else if(func.GENHAO() != null) {
                return "\\\\sqrt{"+brackExpr[1]+"}";
            }else if(func.DUISHU() != null) {
                return "ln("+brackExpr[1]+")";
            }else{
                System.err.println("Not supported function '"+ctx.funcname()+"'");
            }
        }else if(ctx.postFuncname() != null) {
            calculatorParser.PostFuncnameContext postFuncname = ctx.postFuncname();
            String expr = visit(ctx.getChild(0));
            String[] brackExpr = getBrackExpr(expr);

            if(postFuncname.KAIFANG() != null
                    || postFuncname.KAIPINGFANG() != null) {
                return "\\\\sqrt{"+brackExpr[1]+"}";
            }else if(postFuncname.PINGFANG() != null) {
                if(postFuncname.GEN() != null) {
                    return "\\\\sqrt{"+brackExpr[1]+"}";
                }else {
                    return expr+"^2";
                }
            }else if(postFuncname.KAILIFANG() != null) {
                return "\\\\sqrt[3]{"+brackExpr[1]+"}";
            }else if(postFuncname.LIFANG() != null) {
                if(postFuncname.GEN() != null) {
                    return "\\\\sqrt[3]{"+brackExpr[1]+"}";
                }else {
                    return expr+"^3";
                }
            }
        }

        return null;
    }

    @Override
    public String visitNumber(calculatorParser.NumberContext ctx) {
        try{
            String expr = ctx.getText();
            StringBuilder result = new StringBuilder();
            if(ctx.PAI() != null || ctx.DU() != null) {
                if (ctx.DIGIT().isEmpty()) {
                    result.append("\\\\pi");
                }else {
                    numParser.parse(expr.substring(0, expr.length() - 1));
                    result.append(numParser.getReadExpr());

                    if (ctx.PAI() != null) {
                        result.append("\\\\pi");
                    } else {
                        result.append("\\\\degree");
                    }
                }
            } else {
                numParser.parse(ctx.getText());
                result.append(numParser.getReadExpr());
            }
            return result.toString();
        }catch(Exception ex) {
            return null;
        }
    }
}
