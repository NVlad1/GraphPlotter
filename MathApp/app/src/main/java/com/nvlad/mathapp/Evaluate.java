package com.nvlad.mathapp;

/**
 * Created by Vlad on 08.12.2015.
 */
public class Evaluate extends Symbol{
    Expression expr,expr0;
    String s0;

    public Evaluate(){
        expr0 = new Expression();
        expr0.Nullify();
        expr = new Expression();
        expr.Nullify();
        s0 = "";
    }

    public void setString(String s){
        s0 = s;
    }

    public void setExpressionEval(int mode) throws ParseException{
        expr0.Nullify();
        Parser p = new Parser(expr0, mode);
        p.EvalParser(s0);
    }

    public double EvaluateFunction (FunctionType Type, Expression expr, double x) throws ParseException, UnknownException {
        double a = EvaluatorBracket(expr,x);
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

    public double Evaluator(double x) throws ParseException, UnknownException {
        A init, fin;
        double a;
        FunctionType func=FunctionType._null;
        init = new A(); fin = new A();
        expr.equals(expr0);
        expr.EvaluateVariables(x);
        expr.OperatorCheck();
        Expression expr1 = new Expression();
        while(true){
            func=expr.getHighestBracketPos(init,fin);
            expr.getExprPart(expr1,init.num,fin.num);
            if (func!=FunctionType._null){
                a = EvaluateFunction(func,expr1,x);
                expr.ReplaceSimple(init.num-1,fin.num-init.num+3,a);
            }
            else {
                a = EvaluatorBracket(expr1,x);
                expr.ReplaceSimple(init.num,fin.num-init.num+2,a);
            }
            expr1.Nullify();
            if ((init.num==0)&(fin.num==0)) {return a;}
        }
    }

    public double EvaluatorBracket(Expression expr_, double x) throws ParseException, UnknownException {
        double a=0.0;
        int pos;
        while (true){
            pos = expr_.getHighestRankPos ();
            if (pos==-1) {return expr_.getSymbol(0).getValue();} else{

                if (!expr_.UnaryOperator(pos)) {
                    a = expr_.EvaluateSimple(pos);
                    expr_.ReplaceSimple(pos, 2, a);
                }
            }
        }
    }

}
