package com.nvlad.mathapp.Model;

/**
 * Created by Vlad on 24.11.2015.
 */

import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

import static com.nvlad.mathapp.Model.Symbol.OperatorType;
import static com.nvlad.mathapp.Model.Symbol.BracketType;
import static com.nvlad.mathapp.Model.Symbol.FunctionType;
import static com.nvlad.mathapp.Model.Symbol.VarType;

class Expression{
    private static int Nmax = 200;

    private Symbol Sym[];
    private Gamma g;

    Expression(){
        Nullify();
    }

    Symbol getSymbol (int i){
        return Sym[i];
    }

    private void setSymbol (Symbol Sym1, int i){
        Sym[i].setEqualTo(Sym1);
    }

    void Nullify(){
        Sym = new Symbol[Nmax];
        for (int i =0; i<Nmax; i++){
            Sym[i] = new Symbol();
        }
        g = new Gamma();
    }

    void ExprEvaluateVariables (double x) throws UnknownException {
        for (int i=0; i<Nmax; i++)
        {
            Sym[i].setDigit();
            Sym[i].EvaluateVariable(x);
        }
    }

    int getHighestRankPos (){
        int CurrRank = 0;
        int pos = -1;
        for (int i=0; i<Nmax; i++){
            if(Sym[i].getOperatorRank()>CurrRank){
                CurrRank=Sym[i].getOperatorRank();
                pos=i;
            }
        }
        return pos;
    }

    double EvaluateSimple(int pos) throws UnknownException{
        if (Sym[pos].getOpType()== Symbol.OperatorType.plus){return Sym[pos-1].getValue()+Sym[pos+1].getValue();} else
        if (Sym[pos].getOpType()== Symbol.OperatorType.minus){return Sym[pos-1].getValue()-Sym[pos+1].getValue();} else
        if (Sym[pos].getOpType()== Symbol.OperatorType.mult){return Sym[pos-1].getValue()*Sym[pos+1].getValue();} else
        if (Sym[pos].getOpType()== Symbol.OperatorType.div){return Sym[pos-1].getValue()/Sym[pos+1].getValue();} else
        if (Sym[pos].getOpType()== Symbol.OperatorType.power){return Math.pow(Sym[pos-1].getValue(),Sym[pos+1].getValue());} else
        if (Sym[pos].getOpType()== Symbol.OperatorType.mod){return Sym[pos-1].getValue()%Sym[pos+1].getValue();} else
        {
            throw new UnknownException();
        }
    }

    boolean UnaryOperator (int pos) throws ParseException {
        if (UnaryPostOperator(pos)){
            if (Sym[pos].getOpType()== Symbol.OperatorType.fact){
/*                double x = Sym[pos-1].getValue();
                double a = Math.floor(x);
                double b = x-a;
                double f = 1.0;
                for (int i=1; i<=a; i++){
                    f*=i;
                }
                double sum = Math.exp(Math.log(f)+b*Math.log(a+1));
                Sym[pos-1].setValue(sum);*/
                Sym[pos-1].setValue(g.GammaFunc(Sym[pos-1].getValue()+1));
                Shift(pos,1);
                return true;
            } else {
                throw new ParseException(-1);
            }
        }
        if (UnaryPreOperator(pos)){
            if (Sym[pos].getOpType()== Symbol.OperatorType.plus){
                Shift(pos,1);
                return true;
            } else if (Sym[pos].getOpType()== Symbol.OperatorType.minus){
                Sym[pos+1].setValue(-Sym[pos+1].getValue());
                Shift(pos,1);
                return true;
            } else {
                throw new ParseException(-1);
            }
        }
        return false;
    }

    private boolean UnaryPreOperator(int pos){
        return ((pos==0)||(!Sym[pos-1].isNumber()));
    }

    private boolean UnaryPostOperator(int pos){
        return (!Sym[pos+1].isNumber());
    }

    private void Shift (int pos, int step){
        for (int i=pos;i<Nmax;i++){
            if (i+step<Nmax) {
                Sym[i].setEqualTo(Sym[i+step]);
            } else{
                Sym[i].Nullify();
            }
        }
    }

    void ReplaceSimple(int pos, int len, double a){
        if (pos>0){
            Sym[pos-1].setDigit();
            Sym[pos-1].setValue(a);
            Sym[pos-1].setOperatorType(OperatorType._null);
            Sym[pos-1].setBracketType(BracketType._null);
            Sym[pos-1].setFunctionType(FunctionType._null);
            Sym[pos-1].setVarType(VarType._null);
            Sym[pos-1].setOperatorRank(0);
            Shift(pos,len);
        }
        else{
            Sym[pos].setDigit();
            Sym[pos].setValue(a);
            Sym[pos].setOperatorType(OperatorType._null);
            Sym[pos].setBracketType(BracketType._null);
            Sym[pos].setFunctionType(FunctionType._null);
            Sym[pos].setVarType(VarType._null);
            Sym[pos].setOperatorRank(0);
            Shift(pos+1,len);
        }
    }

    void setEqualTo(Expression exp){
        for (int i=0; i<Nmax; i++){
            Sym[i].setEqualTo(exp.getSymbol(i));
        }
    }

    void EvaluateVariables (double x) throws UnknownException{
        for (int i = 0; i<Nmax; i++){
            Sym[i].EvaluateVariable(x);
        }
    }

    BracketInfo getHighestBracketPos () throws ParseException{
        FunctionType func=FunctionType._null;
        int init=0;
        int fin=0;
        int Rank = 0;
        int HighestRank = 0;
        for (int i = 0; i<Nmax; i++){
            if (Sym[i].isBracket()){
                if (Sym[i].getBType()==BracketType.left){
                    Rank++;
                }
                if (Sym[i].getBType()==BracketType.right){
                    if (Rank==HighestRank) {if(fin<init) {fin=i-1;}}
                    Rank--;
                }
                if (Rank>HighestRank) {
                    HighestRank=Rank;
                    init=i+1;
                    if (i>0){
                        if (!Sym[i-1].isFunction()) {func=FunctionType._null;}
                        else func=Sym[i-1].getFType();
                    }
                    else {func=FunctionType._null;}
                }
            }
            if (Sym[i].isNull()){
                if (HighestRank==0){init=0; fin=i-1; return new BracketInfo(init,fin,func);}
            }
        }
        if (init>fin) {
            throw new ParseException(-1);
        }
        return new BracketInfo(init,fin,func);
    }

    void getExprPart(Expression expr1,int init, int fin){
        for(int i=init; i<=fin; i++){
            //expr1.setSymbol(Sym[i],i-init);
            expr1.Sym[i-init].setEqualTo(Sym[i]);
        }
    }

    void MissedMultiplicationCheck(){
        for (int i = 0; i < Nmax-1; i++){
            if (((Sym[i].isDigit())&(Sym[i+1].isVar()))||
                    ((Sym[i].isDigit())&(Sym[i+1].isLeftBracket()))||
                    ((Sym[i].isVar())&(Sym[i+1].isLeftBracket()))||
                    ((Sym[i].isVar())&(Sym[i+1].isVar()))||
                    ((Sym[i].isVar())&(Sym[i+1].isFunction()))||
                    ((Sym[i].isDigit())&(Sym[i+1].isFunction()))){InsertMult(i+1);}
        }
    }

    private void InsertMult (int pos){
        for (int i=Nmax-1; i>pos; i--){
            Sym[i].setEqualTo(Sym[i-1]);
        }
        Sym[pos].setOperator();
        Sym[pos].setValue(0.0);
        Sym[pos].setOperatorType(OperatorType.mult);
        Sym[pos].setBracketType(BracketType._null);
        Sym[pos].setFunctionType(FunctionType._null);
        Sym[pos].setVarType(VarType._null);
        Sym[pos].setOperatorRank(2);
    }


//        while(isDigit(s.charAt(pos)))
//        {pos++;if (pos==s.length()){s.getChars(0,pos,chtemp,0); s0=new String(chtemp); return Integer.parseInt(s0);}}
//        if (pos>0){
//            ch=s.charAt(pos);
//            if (ch==plus)
//        }

    static class BracketInfo{
        int init;
        int fin;
        FunctionType ftype;

        BracketInfo(int init, int fin, FunctionType ftype){
            this.init = init;
            this.fin = fin;
            this.ftype = ftype;
        }
    }

}



