package com.nvlad.mathapp;

import static android.R.attr.mode;

/**
 * Created by Vlad on 08.12.2015.
 */
public class Parser extends Symbol{
    private static char[] mInt = new char[] {
            '0', '1' , '2', '3', '4', '5',
            '6', '7' , '8', '9', '.'};
    private static char[] mLetter = new char[]{
            'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm',
            'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z', '_'};
    private static char[] mOperator = new char[] {
            '+', '-', '*', '/', '^', '%', '!'};
    private static int[] mOperatorRank = new int[] {
            1, 1, 2, 2, 3, 3, 4};
    private static String[] mVar = {"x","y","t","pi","e","a","b","c","d"};
    private static String[] mFunc = {"sqrt","exp","sin","cos","tg","ctg","abs","log","ln","asin","acos","atan","sh","ch"};
    private static char[] mBracket = new char[]{'(', ')'};
    private static char plus = mOperator[0];
    private static char minus = mOperator[1];
    private static char mult = mOperator[2];
    private static char div = mOperator[3];
    private static char power = mOperator[4];
    private static char mod = mOperator[5];
    private static char fact = mOperator[6];
    Expression expr;

    public Parser (Expression expr_, int mode){
//    expr = new Expression();
//        expr.equals(expr_);
      expr=expr_;
    }

    private boolean isDigit(String s, int pos){
        if (pos>=s.length()) return false;
        char ch = s.charAt(pos);
        for (int i = 0; i<mInt.length; i++){
            if (ch==mInt[i]) {return true;}
        }
        return false;
    }

    private boolean isLetter(String s, int pos){
        if (pos>=s.length()) return false;
        char ch = s.charAt(pos);
        for (int i = 0; i<mLetter.length; i++){
            if (ch==mLetter[i]) {return true;}
        }
        return false;
    }

    private boolean isOperator(char ch){
        for (int i = 0; i<mOperator.length; i++){
            if (ch==mOperator[i]) {return true;}
        }
        return false;
    }


    private boolean isBracket(char ch){
        if ((ch==mBracket[0])||(ch==mBracket[1])) return true;
        else return false;
    }


    private String getDigit(String s, int pos0) {
        int pos = pos0;
        char[] chtemp = new char[255];
        String s0;
        while (isDigit(s,pos)) {
            pos++;
            if (pos == s.length()) {
                s.getChars(pos0, pos, chtemp, 0);
                s0 = new String(chtemp,0,pos-pos0);
                return s0;
            }
        }
        s.getChars(pos0, pos, chtemp, 0);
        s0 = new String(chtemp,0,pos-pos0);
        return s0;
    }

    private OperatorType getOperator(String s, int pos){
        char ch1;
        ch1=s.charAt(pos);
        if (ch1==plus) {return OperatorType.plus;} else
        if (ch1==minus) {return OperatorType.minus;} else
        if (ch1==mult) {return OperatorType.mult;} else
        if (ch1==div) {return OperatorType.div;} else
        if (ch1==power) {return OperatorType.power;} else
        if (ch1==mod) {return OperatorType.mod;} else
        if (ch1==fact) {return OperatorType.fact;} else
        {return OperatorType._null;}
    }

    private BracketType getBracket(String s, int pos){
        char ch1;
        ch1=s.charAt(pos);
        if (ch1==mBracket[0]) {return BracketType.left;} else
        if (ch1==mBracket[1]) {return BracketType.right;} else
        {return BracketType._null;}
    }


    private int getLetter(String s, int pos0, Expression expr0, int j) throws ParseException{
        int pos = pos0+1;
        boolean correct = false;
        char[] chtemp = new char[255];
        String s0;
        while ((isDigit(s,pos))||(isLetter(s,pos))) {
            pos++;
            if (pos == s.length()) {
                s.getChars(pos0, pos, chtemp, 0);
                s0 = new String(chtemp,0,pos-pos0);
                ;
            }
        }
        s.getChars(pos0, pos, chtemp, 0);
        s0 = new String(chtemp,0,pos-pos0);
        for (int i = 0; i<mFunc.length; i++){
            if (s0.equals(mFunc[i])){
                expr0.getSymbol(j).setFunction();
                expr0.getSymbol(j).setFunctionType(FunctionType.valueOf(s0));
                correct = true;
            }
        }
        for (int i = 0; i<mVar.length; i++){
            if (s0.equals(mVar[i])){
                expr0.getSymbol(j).setVar();
                if ((mode==0)&((s0.equals(mVar[1]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==1)&((s0.equals(mVar[0]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==2)&((s0.equals(mVar[1]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==3)&((s0.equals(mVar[0]))||(s0.equals(mVar[1])))) throw new ParseException(-1);
                expr0.getSymbol(j).setVarType(VarType.valueOf(s0));
                correct = true;
            }
        }
        if (!correct) throw new ParseException(-1);
        return s0.length();
    }




    public void EvalParser(String s) throws ParseException{
        OperatorType op;
        FunctionType func;
        BracketType bra;
        char ch,ch1;
        String s0,s1;
        int len;
        double a=0.0;
        int pos = 0;
        int j = 0;
        char [] chtemp = new char[255];
        while(pos<s.length()){
            len = 0;
            if (isDigit(s,pos)) {
                s0 = getDigit(s, pos);
                len = s0.length();
                a = Double.parseDouble(s0);
                expr.getSymbol(j).setDigit();
                expr.getSymbol(j).setValue(a);
            }
            if (isOperator(s.charAt(pos))){
                expr.getSymbol(j).setOperator();
                op = getOperator(s,pos);
                expr.getSymbol(j).setOperatorType(op);
                expr.getSymbol(j).setOperatorRank(mOperatorRank[op.ordinal()]);
                len = 1;
            }
            if (isLetter(s,pos)) {
                len = getLetter(s,pos,expr,j);
            }
            if (isBracket(s.charAt(pos))) {
                expr.getSymbol(j).setBracket();
                bra = getBracket(s,pos);
                expr.getSymbol(j).setBracketType(bra);
                len = 1;
            }
            if (len==0){
                throw new ParseException(-1);
            }
            pos+=len;
            j++;
        }
    }
}
