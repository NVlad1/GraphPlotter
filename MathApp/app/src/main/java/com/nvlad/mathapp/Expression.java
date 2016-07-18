package com.nvlad.mathapp;

/**
 * Created by Vlad on 24.11.2015.
 */
public class Expression extends Symbol{
    static int Nmax = 50;
   //for operators: 0 for plus, 1 for minus, 2 for mult, 3 for div;

    private Symbol Sym[];
    private Gamma g;

    public Expression(){
        Sym = new Symbol[Nmax];
        for (int i =0; i<Nmax; i++){
            Sym[i] = new Symbol();
        }
        g = new Gamma();
    }

    public Symbol getSymbol (int i){
        return Sym[i];
    }

    public void setSymbol (Symbol Sym1, int i){
        Sym[i].equals(Sym1);
    }

    void ExprEvaluateVariables (double x) throws UnknownException{
        for (int i=0; i<Nmax; i++)
        {
            Sym[i].setDigit();
            Sym[i].EvaluateVariable(x);
        }
    }

    public int getHighestRankPos (){
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

    public double EvaluateSimple(int pos) throws UnknownException{
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

    public boolean UnaryOperator (int pos) throws ParseException{
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
        if (pos==0) return true;
        if (!Sym[pos-1].isNumber()) {return true;}
        else return false;
    }

    private boolean UnaryPostOperator(int pos){
        if (pos==0) return true;
        if (!Sym[pos+1].isNumber()) {return true;}
        else return false;
    }

    private void Shift (int pos, int step){
        for (int i=pos;i<Nmax;i++){
            if (i+step<Nmax) {
                Sym[i].equals(Sym[i+step]);
            } else{
                Sym[i].Nullify();
            }
        }
    }

    public void ReplaceSimple(int pos, int len, double a){
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

    public void equals(Expression exp){
        for (int i=0; i<Nmax; i++){
            Sym[i].equals(exp.getSymbol(i));
        }
    }

    public void EvaluateVariables (double x) throws UnknownException{
        x=x;
        for (int i = 0; i<Nmax; i++){
            Sym[i].EvaluateVariable(x);
        }
    }

    public FunctionType getHighestBracketPos (A init, A fin) throws ParseException{
        FunctionType func=FunctionType._null;
        init.num=0;
        fin.num=0;
        int Rank = 0;
        int HighestRank = 0;
        for (int i = 0; i<Nmax; i++){
            if (Sym[i].isBracket()){
                if (Sym[i].getBType()==BracketType.left){
                    Rank++;
                }
                if (Sym[i].getBType()==BracketType.right){
                    if (Rank==HighestRank) {if(fin.num<init.num) {fin.num=i-1;}}
                    Rank--;
                }
                if (Rank>HighestRank) {
                    HighestRank=Rank;
                    init.num=i+1;
                    if (i>0){
                        if (!Sym[i-1].isFunction()) {func=FunctionType._null;}
                        else func=Sym[i-1].getFType();
                    }
                    else {func=FunctionType._null;}
                }
            }
            if (Sym[i].isNull()){
                if (HighestRank==0){init.num=0; fin.num=i-1; return func;}
            }
        }
        if (init.num>fin.num) {
            throw new ParseException(-1);
        }
        return func;
    }

    public void getExprPart(Expression expr1,int init, int fin){
        for(int i=init; i<=fin; i++){
            expr1.setSymbol(Sym[i],i-init);
        }
    }

    public void OperatorCheck(){
        for (int i = 0; i < Nmax-1; i++){
            if (((Sym[i].isDigit())&(Sym[i+1].isVar()))||
                    ((Sym[i].isDigit())&(Sym[i+1].isLeftBracket()))
                    ||((Sym[i].isVar())&(Sym[i+1].isLeftBracket()))){InsertMult(i+1);}
        }
    }

    private void InsertMult (int pos){
        for (int i=Nmax-1; i>pos; i--){
            Sym[i].equals(Sym[i-1]);
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

    }



