package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.ParseException;

import static com.nvlad.mathapp.Model.Symbol.BracketType;
import static com.nvlad.mathapp.Model.Symbol.FunctionType;
import static com.nvlad.mathapp.Model.Symbol.OperatorType;
import static com.nvlad.mathapp.Model.Symbol.VarType;

/**
 * Created by Vlad on 08.12.2015.
 */

class Parser{
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
    private Expression expr;
    private int mode;

    Parser (Expression expr, int mode){
        this.mode = mode;
        this.expr=expr;
    }

    private boolean isDigit(String s, int pos){
        if (pos>=s.length()) return false;
        char ch = s.charAt(pos);
        for (char ch1:mInt){
            if (ch==ch1) {return true;}
        }
        return false;
    }

    private boolean isLetter(String s, int pos){
        if (pos>=s.length()) return false;
        char ch = s.charAt(pos);
        for (char ch1: mLetter){
            if (ch==ch1) {return true;}
        }
        return false;
    }

    private boolean isOperator(char ch){
        for (char ch1: mOperator){
            if (ch==ch1) {return true;}
        }
        return false;
    }


    private boolean isBracket(char ch){
        return ((ch==mBracket[0])||(ch==mBracket[1]));
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


    private LetterParseResult getLetter(String s, int pos0, Expression expr0, int j) throws ParseException {
        int pos = pos0+1;
        boolean correct = false;
        char[] chtemp = new char[255];
        String s0;
        while ((isDigit(s,pos))||(isLetter(s,pos))) pos++;
        s.getChars(pos0, pos, chtemp, 0);
        s0 = new String(chtemp,0,pos-pos0);
        for (String s1: mFunc){
            if (s0.equals(s1)){
                expr0.getSymbol(j).setFunction();
                expr0.getSymbol(j).setFunctionType(FunctionType.valueOf(s0));
                correct = true;
            }
        }
        for (String s1: mVar){
            if (s0.equals(s1)){
                expr0.getSymbol(j).setVar();
                if ((mode==0)&((s0.equals(mVar[1]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==1)&((s0.equals(mVar[0]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==2)&((s0.equals(mVar[1]))||(s0.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==3)&((s0.equals(mVar[0]))||(s0.equals(mVar[1])))) throw new ParseException(-1);
                expr0.getSymbol(j).setVarType(VarType.valueOf(s0));
                correct = true;
            }
        }
        if (!correct) {
            if (s0.length()==2){
                return doubleLetterCheck(s0,expr0,j);
            }
            throw new ParseException(-1);
        }
        return new LetterParseResult(s0.length(),false);
    }

    private LetterParseResult doubleLetterCheck(String s0, Expression expr0, int j) throws ParseException{
        String s01 = s0.substring(0,1);
        String s02 = s0.substring(1,2);
        boolean correctFirst = false;
        boolean correctSecond = false;

        for (String s1: mVar){
            if (s01.equals(s1)){
                expr0.getSymbol(j).setVar();
                if ((mode==0)&((s01.equals(mVar[1]))||(s01.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==1)&((s01.equals(mVar[0]))||(s01.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==2)&((s01.equals(mVar[1]))||(s01.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==3)&((s01.equals(mVar[0]))||(s01.equals(mVar[1])))) throw new ParseException(-1);
                expr0.getSymbol(j).setVarType(VarType.valueOf(s01));
                correctFirst = true;
            }
        }

        for (String s1: mVar){
            if (s02.equals(s1)){
                expr0.getSymbol(j+1).setVar();
                if ((mode==0)&((s02.equals(mVar[1]))||(s02.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==1)&((s02.equals(mVar[0]))||(s02.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==2)&((s02.equals(mVar[1]))||(s02.equals(mVar[2])))) throw new ParseException(-1);
                if ((mode==3)&((s02.equals(mVar[0]))||(s02.equals(mVar[1])))) throw new ParseException(-1);
                expr0.getSymbol(j+1).setVarType(VarType.valueOf(s02));
                correctSecond = true;
            }
        }
        if ((correctFirst&correctSecond)&&!(s01.equals(s02))) return new LetterParseResult(s0.length(),true);
        else throw new ParseException(-1);
    }




    void EvalParser(String s) throws ParseException{
        int len;
        int pos = 0;
        int j = 0;
        while(pos<s.length()){
            len = 0;
            if (isDigit(s,pos)) {
                String s0 = getDigit(s, pos);
                len = s0.length();
                double a = Double.parseDouble(s0);
                expr.getSymbol(j).setDigit();
                expr.getSymbol(j).setValue(a);
            }
            if (isOperator(s.charAt(pos))){
                expr.getSymbol(j).setOperator();
                OperatorType op = getOperator(s,pos);
                expr.getSymbol(j).setOperatorType(op);
                expr.getSymbol(j).setOperatorRank(mOperatorRank[op.ordinal()]);
                len = 1;
            }
            if (isLetter(s,pos)) {
                LetterParseResult res = getLetter(s,pos,expr,j);
                len = res.length;
                if (res.isDoubleLetter) j++;
            }
            if (isBracket(s.charAt(pos))) {
                expr.getSymbol(j).setBracket();
                BracketType bra = getBracket(s,pos);
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

    private class LetterParseResult{
        boolean isDoubleLetter;
        int length;

        LetterParseResult(int length, boolean isDoubleLetter){
            this.length = length;
            this.isDoubleLetter = isDoubleLetter;
        }
    }

}
