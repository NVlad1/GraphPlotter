package com.nvlad.mathapp;

/**
 * Created by Vlad on 07.12.2015.
 */
public class Symbol {
    public static enum SymbolType{number, operator, variable, bracket, function, errorsymbol, _null};
    public static enum OperatorType{plus, minus, mult, div, power, mod, fact, _null};
    public static enum FunctionType{sqrt, exp, sin, cos, tg, ctg, abs, log, ln, asin, acos, atan, sh, ch, _null};
    public static enum BracketType{left,right, _null};
    public static enum VarType{x,y,t,pi,e,a,b,c,d,_null};
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

    public Symbol(){
        Nullify();
    }

    public class A{
        int num;
        public A() {num=0;}
    }

    public boolean isDigit(){
        if (SType == SymbolType.number) return true;
        else return false;
    }

    public boolean isNumber(){
        if ((SType == SymbolType.number)||(SType == SymbolType.variable)) return true;
        else return false;
    }

    public boolean isOperator(){
        if (SType == SymbolType.operator) return true;
        else return false;
    }

    public boolean isBracket(){
        if (SType == SymbolType.bracket) return true;
        else return false;
    }

    public boolean isLeftBracket(){
        if ((SType == SymbolType.bracket)&(BType == BracketType.left)) return true;
        else return false;
    }

    public boolean isFunction(){
        if (SType == SymbolType.function) return true;
        else return false;
    }

    public boolean isVar(){
        if (SType == SymbolType.variable) return true;
        else return false;
    }

    public boolean isError(){
        if (SType == SymbolType.errorsymbol) return true;
        else return false;
    }

    public boolean isNull(){
        if (SType == SymbolType._null) return true;
        else return false;
    }

    public void setDigit(){
        SType = SymbolType.number;
    }

    public void setOperator(){
        SType = SymbolType.operator;
    }

    public void setFunction(){
        SType = SymbolType.function;
    }

    public void setBracket(){
        SType = SymbolType.bracket;
    }

    public void setVar(){
        SType = SymbolType.variable;
    }

    public void setError(){
        SType = SymbolType.errorsymbol;
    }

    public double getValue(){
        return value;
    }

    public OperatorType getOpType(){
        return OpType;
    }

    public FunctionType getFType(){
        return FType;
    }

    public BracketType getBType(){
        return BType;
    }

    public void setValue(double a){
        value = a;
    }

    public void setOperatorType(OperatorType a){
        OpType = a;
    }

    public void setFunctionType(FunctionType a){
        FType = a;
    }

    public void setBracketType(BracketType a){
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

    public void setVarType(VarType a) {VType = a;}

    public void setOperatorRank(int i){
        OpRank = i;
    }

    public int getOperatorRank(){
        return OpRank;
    }

    public void EvaluateVariable(double x) throws UnknownException{
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
    public void equals (Symbol b)
    {
        SType=b.SType;
        OpType=b.OpType;
        OpRank=b.OpRank;
        value=b.value;
        FType=b.FType;
        BType=b.BType;
        VType=b.VType;
    }

    public void Nullify()
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
