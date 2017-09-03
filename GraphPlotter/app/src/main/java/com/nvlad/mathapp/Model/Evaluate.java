package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

import static com.nvlad.mathapp.Model.Symbol.FunctionType;

/**
 * Created by Vlad on 08.12.2015.
 */
class Evaluate{
    private Expression expr,expr0;
    private String s0;

    Evaluate(){
        expr0 = new Expression();
        expr = new Expression();
        s0 = "";
    }

    public void setString(String s){
        s0 = s;
    }

    void setExpressionEval(int mode) throws ParseException {
        expr0.Nullify();
        Parser p = new Parser(expr0, mode);
        p.EvalParser(s0);
    }

    private double EvaluateFunction (FunctionType Type, Expression expr) throws ParseException, UnknownException {
        double a = EvaluatorBracket(expr);
        if (Type==FunctionType.sqrt) {return Math.sqrt(a);}
        if (Type==FunctionType.exp) {return Math.exp(a);}
        if (Type==FunctionType.sin) {return Math.sin(a);}
        if (Type==FunctionType.cos) {return Math.cos(a);}
        if (Type==FunctionType.tg) {return Math.tan(a);}
        if (Type==FunctionType.ctg) {return 1.0/Math.tan(a);}
        if (Type==FunctionType.log) {return Math.log10(a);}
        if (Type==FunctionType.ln) {return Math.log(a);}
        if (Type==FunctionType.asin) {return Math.asin(a);}
        if (Type==FunctionType.acos) {return Math.acos(a);}
        if (Type==FunctionType.atan) {return Math.atan(a);}
        if (Type==FunctionType.abs) {return Math.abs(a);}
        if (Type==FunctionType.sh) {return (Math.exp(a)-Math.exp(-a))/2.0;}
        if (Type==FunctionType.ch) {return (Math.exp(a)+Math.exp(-a))/2.0;}
        throw new ParseException(-1);
    }

    double Evaluator(double x) throws ParseException, UnknownException {
        double a;
        expr.setEqualTo(expr0);
        expr.EvaluateVariables(x);
        expr.OperatorCheck();
        Expression expr1 = new Expression();
        while(true){
            Expression.BracketInfo br_info = expr.getHighestBracketPos();
            expr.getExprPart(expr1,br_info.init,br_info.fin);
            if (br_info.ftype!=FunctionType._null){
                a = EvaluateFunction(br_info.ftype,expr1);
                expr.ReplaceSimple(br_info.init-1,br_info.fin-br_info.init+3,a);
            }
            else {
                a = EvaluatorBracket(expr1);
                expr.ReplaceSimple(br_info.init,br_info.fin-br_info.init+2,a);
            }
            expr1.Nullify();
            if ((br_info.init==0)&(br_info.fin==0)) {return a;}
        }
    }

    private double EvaluatorBracket(Expression expr_) throws ParseException, UnknownException {
        int pos;
        while (true){
            pos = expr_.getHighestRankPos ();
            if (pos==-1) {return expr_.getSymbol(0).getValue();} else{

                if (!expr_.UnaryOperator(pos)) {
                    double a = expr_.EvaluateSimple(pos);
                    expr_.ReplaceSimple(pos, 2, a);
                }
            }
        }
    }

}
