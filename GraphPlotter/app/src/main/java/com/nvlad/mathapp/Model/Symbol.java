package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.UnknownException;

/**
 * Created by Vlad on 07.12.2015.
 */
public class Symbol {
    enum SymbolType{number, operator, variable, bracket, function, errorsymbol, _null};
    enum OperatorType{plus, minus, mult, div, power, mod, fact, _null};
    enum FunctionType{sqrt, exp, sin, cos, tg, ctg, abs, log, ln, asin, acos, atan, sh, ch, _null};
    enum BracketType{left,right, _null};
    enum VarType{x,y,t,pi,e,a,b,c,d,_null};
    private static final double pi = 3.1415;
    private static final double e = 2.7183;
    private static double par_a=0.0;
    private static double par_b=0.0;
    private static double par_c=0.0;
    private static double par_d=0.0;
    private SymbolType SType = SymbolType._null;
    private OperatorType OpType = OperatorType._null;
    private FunctionType FType = FunctionType._null;
    private BracketType BType = BracketType._null;
    private VarType VType = VarType._null;
    private int OpRank = 0;
    private double value = 0.0;

    Symbol(){
        Nullify();
    }

    boolean isDigit(){
        return SType == SymbolType.number;
    }

    boolean isNumber(){
        return (SType == SymbolType.number)||(SType == SymbolType.variable);
    }

    boolean isOperator(){
        return SType == SymbolType.operator;
    }

    boolean isBracket(){
        return SType == SymbolType.bracket;
    }

    boolean isLeftBracket(){
        return (SType == SymbolType.bracket)&(BType == BracketType.left);
    }

    boolean isFunction(){
        return SType == SymbolType.function;
    }

    boolean isVar(){
        return SType == SymbolType.variable;
    }

    boolean isError(){
        return SType == SymbolType.errorsymbol;
    }

    boolean isNull(){
        return SType == SymbolType._null;
    }

    void setDigit(){
        SType = SymbolType.number;
    }

    void setOperator(){
        SType = SymbolType.operator;
    }

    void setFunction(){
        SType = SymbolType.function;
    }

    void setBracket(){
        SType = SymbolType.bracket;
    }

    void setVar(){
        SType = SymbolType.variable;
    }

    void setError(){
        SType = SymbolType.errorsymbol;
    }

    double getValue(){
        return value;
    }

    OperatorType getOpType(){
        return OpType;
    }

    FunctionType getFType(){
        return FType;
    }

    BracketType getBType(){
        return BType;
    }

    void setValue(double a){
        value = a;
    }

    void setOperatorType(OperatorType a){
        OpType = a;
    }

    void setFunctionType(FunctionType a){
        FType = a;
    }

    void setBracketType(BracketType a){
        BType = a;
    }

    public static void setPar_a(double a){par_a = a;}
    public static void setPar_b(double b){par_b = b;}
    public static void setPar_c(double c){par_c = c;}
    public static void setPar_d(double d){par_d = d;}
    public static double getPar_a(){return par_a;}
    public static double getPar_b(){return par_b;}
    public static double getPar_c(){return par_c;}
    public static double getPar_d(){return par_d;}

    void setVarType(VarType a) {VType = a;}

    void setOperatorRank(int i){
        OpRank = i;
    }

    int getOperatorRank(){
        return OpRank;
    }

    void EvaluateVariable(double x) throws UnknownException {
        if (isVar()){
//            setDigit();
            if (VType==VarType.x) value=x; else
            if (VType==VarType.y) value=x; else
            if (VType==VarType.t) value=x; else
            if (VType==VarType.pi) value=pi; else
            if (VType==VarType.e) value=e; else
            if (VType==VarType.a) value=par_a; else
            if (VType==VarType.b) value=par_b; else
            if (VType==VarType.c) value=par_c; else
            if (VType==VarType.d) value=par_d; else
                throw new UnknownException();
        }
    }

    void setEqualTo (Symbol b)
    {
        SType=b.SType;
        OpType=b.OpType;
        OpRank=b.OpRank;
        value=b.value;
        FType=b.FType;
        BType=b.BType;
        VType=b.VType;
    }

    void Nullify()
    {
        SType=SymbolType._null;
        OpType=OperatorType._null;
        OpRank=0;
        value=0.0;
        FType=FunctionType._null;
        BType=BracketType._null;
        VType=VarType._null;
    }
}
