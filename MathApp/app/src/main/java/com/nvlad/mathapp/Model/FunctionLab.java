package com.nvlad.mathapp.Model;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.Log;

import com.nvlad.mathapp.Exception.AnalysisException;
import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

/**
 * Created by Vlad on 15.02.2016.
 */
public class FunctionLab implements Parcelable {
    private int NFunctions;
    private int NIntersect;
    private static int NFunctionsMax = 10;
    private static int numberOfDots = 300;
    private static int NMax = 50000;
    private String expr_str[];
    private Evaluate Eval[];
    private Analysis An[];
    private double xmin,xmax,ymin,ymax;
    private double x[];
    private double y[][];
    private boolean isFunctionParsed;
    private boolean isEval,isEvalExtr,isEvalRoot,isEvalInter;
    private double IntersectX[];
    private double IntersectY[];
    private int mode[];

    public FunctionLab () {
        NFunctions = 0;
        x = new double[NMax];
        y = new double[NFunctionsMax][NMax];
        IntersectX = new double[10* numberOfDots];
        IntersectY = new double[10* numberOfDots];
        expr_str = new String[NFunctionsMax];
        Eval = new Evaluate[NFunctionsMax];
        An = new Analysis[NFunctionsMax];
        mode = new int[NFunctionsMax];
        for (int i=0;i<NFunctionsMax;i++){
            Eval[i] = new Evaluate();
            An[i] = new Analysis();
            mode[i]=0;
        }
        setAllFalse();
        xmin = -20.0;
        xmax = +20.0;
        ymin = -20.0;
        ymax = +20.0;
    }

    public static final Parcelable.Creator<FunctionLab> CREATOR = new Parcelable.Creator<FunctionLab>() {
        public FunctionLab createFromParcel(Parcel in) {
            Log.d("myLogs", "createFromParcel");
            return new FunctionLab();
        }

        public FunctionLab[] newArray(int size) {
            return new FunctionLab[size];
        }
    };


    public void writeToParcel(Parcel out, int flags) {
        out.writeInt(NFunctions);
    }

    public int describeContents() {
        return NFunctions;
    }



    public void AddFunction(String s) throws ParseException {
        NFunctions++;
        if (NFunctions>NFunctionsMax) throw new ParseException(100);
        expr_str[NFunctions-1]=s;
        isFunctionParsed = false;
        isEval=false;
    }

    public void AddFunction() throws ParseException{
        NFunctions++;
        if (NFunctions>NFunctionsMax) throw new ParseException(100);
        expr_str[NFunctions-1]="";
        isFunctionParsed = false;
        isEval=false;
    }

    public void DeleteFunction(int i){
        for (int j=i;j<NFunctions-1;j++){
            expr_str[j]=expr_str[j+1];
        }
        NFunctions--;
        isEval=false;
        isFunctionParsed=false;
    }

    public void ParseFunctions() throws ParseException{
        if (NFunctions>0) ClearEmpty();
        if (!isFunctionParsed) {
            for (int i = 0; i < NFunctions; i++) {
                try {
                    Eval[i]=null;
                    Eval[i]=new Evaluate();
                    Eval[i].setString(expr_str[i]);
                    Eval[i].setExpressionEval(mode[i]);
                }
                catch (ParseException e){
                    throw new ParseException(i);
                }
            }
            isEval = false;
        }
        isFunctionParsed = true;
    }

    private double Evaluator(int i, double x) throws ParseException, UnknownException {
        if (i>=NFunctions) throw new ParseException(100);
        return Eval[i].Evaluator(x);
    }

    public void EvaluateFunctions()
            throws ParseException, UnknownException{
        if (!isFunctionParsed) ParseFunctions();
        if (!isEval) {
            for (int i = 0; i < numberOfDots; i++) {
                x[i] = xmin + (xmax - xmin) * i / numberOfDots;
                for (int j = 0; j < NFunctions; j++)
                    try {
                        y[j][i] = Evaluator(j, x[i]);
                    }
                    catch (ParseException e){
                        throw new ParseException(j);
                    }
            }
            isEvalExtr=false;
            isEvalRoot=false;
            isEvalInter=false;
        }
        isEval=true;
    }

    private double FindExtrema(double x01, double x02, int num, boolean ExT)throws ParseException, UnknownException{
        double x1=x01;
        double x2=x02;
        double x3,x4,y3,y4;
        double e = (xmax-xmin)/ numberOfDots;
        if (e>4e-5) e=4e-5;
        while(Math.abs(x2-x1)>e){
            x3=2.0/3.0*x1+1.0/3.0*x2;
            x4=1.0/3.0*x1+2.0/3.0*x2;
            y3=Evaluator(num,x3);
            y4=Evaluator(num,x4);
            if (ExT){
                if (y3>y4) {x2=x4;}
                else {x1=x3;}
            } else{
                if (y3<y4) {x2=x4;}
                else {x1=x3;}
            }
        }
        return x1;
    }

    public void CalculateExtrema() throws ParseException, UnknownException{
        double x0;
        if (!isEvalExtr) {
            for (int j = 0; j < NFunctions; j++) {
                for (int i = 0; i < numberOfDots; i++) {
                    if (i >= 2)
                        if ((y[j][i] - y[j][i - 1]) * (y[j][i - 1] - y[j][i - 2]) <= 0) {
                            if (y[j][i] > y[j][i - 1]) {
                                x0 = FindExtrema(x[i - 2], x[i], j, false);
                                An[j].AddExtrema(x0, Evaluator(j, x0), false);
//                                An[j].AddExtrema(x[i - 1], y[j][i - 1], false);
                            }
                            if (y[j][i] < y[j][i - 1]) {
                                x0 = FindExtrema(x[i - 2], x[i], j, true);
                                An[j].AddExtrema(x0, Evaluator(j, x0), true);
//                                An[j].AddExtrema(x[i - 1], y[j][i - 1], true);
                            }
                        }
                }
            }
            isEvalExtr = true;
        }
    }

    public void CalculateRoots() throws ParseException,UnknownException,AnalysisException {
        if (!isEvalRoot) {
            for (int j = 0; j < NFunctions; j++) {
                An[j].CalculateRoots(xmin, xmax, y[j][0], y[j][numberOfDots - 1], Eval[j]);
            }
            isEvalRoot = true;
        }
    }

    public void CalculateIntersections() throws ParseException, UnknownException, AnalysisException
    {
        double y_temp[];
        int root_num;
        if (!isEvalInter) {
            NIntersect = 0;
            y_temp = new double[numberOfDots];
            for (int j = 0; j < NFunctions - 1; j++)
                for (int k = j + 1; k < NFunctions; k++) {
                    if ((mode[j] == 0) & (mode[k] == 0)) {
                        Evaluate Eval_inter = new Evaluate();
                        Eval_inter.setString(expr_str[j] + "-(" + expr_str[k] + ")");
                        Eval_inter.setExpressionEval(0);
                        Analysis An_inter = new Analysis();
                        for (int i = 0; i < numberOfDots; i++) {
                            x[i] = xmin + (xmax - xmin) * i / numberOfDots;
                            y_temp[i] = Eval_inter.Evaluator(x[i]);
                        }
                        for (int i = 0; i < numberOfDots; i++) {
                            if (i >= 2)
                                if ((y_temp[i] - y_temp[i - 1]) * (y_temp[i - 1] - y_temp[i - 2]) < 0) {
                                    if (y_temp[i] > y_temp[i - 1])
                                        An_inter.AddExtrema(x[i - 1], y_temp[i - 1], false);
                                    if (y_temp[i] < y_temp[i - 1])
                                        An_inter.AddExtrema(x[i - 1], y_temp[i - 1], true);
                                }
                        }
                        An_inter.CalculateRoots(xmin, xmax, y_temp[0], y_temp[numberOfDots - 1], Eval_inter);
                        root_num = An_inter.getRootNum();
                        for (int i = 0; i < root_num; i++) {
                            IntersectX[NIntersect] = An_inter.getRoot(i);
                            IntersectY[NIntersect] = Eval[j].Evaluator(IntersectX[NIntersect]);
                            if ((!Double.isNaN(IntersectX[NIntersect])) & (!Double.isInfinite(IntersectX[NIntersect])))
                                NIntersect++;
                        }
                    }
                }
            isEvalInter = true;
        }
    }

    public int getExtrNum(int j){return An[j].getExtrNum();}
    public double getExtrX(int j, int i){return An[j].getExtrX(i);}
    public double getExtrY(int j, int i){return An[j].getExtrY(i);}
    public int getRootNum(int j){return An[j].getRootNum();}
    public double getRoot(int j, int i){return An[j].getRoot(i);}
    public int getIntersectionsNum(){return NIntersect;}
    public double getIntersectX(int i){return IntersectX[i];}
    public double getIntersectY(int i){return IntersectY[i];}
    public double getX(int i){return x[i];}
    public double getY(int j, int i){return y[j][i];}
    public double getXmin(){return xmin;}
    public double getXmax(){return xmax;}
    public double getYmin(){return ymin;}
    public double getYmax(){return ymax;}
    public int getnumberofdots(){return numberOfDots;}
    public void setXmin(double x){xmin=x;}
    public void setXmax(double x){xmax=x;}
    public void setYmin(double x){ymin=x;}
    public void setYmax(double x){ymax=x;}
    public void setMode(int i, int j){mode[i]=j;}
    public int getMode(int i) {return mode[i];}
    public int getNFunctions(){
        return NFunctions;
    }
    public String getString(int i){
        return expr_str[i];
    }
    public void setEvalFalse(){
        isEval=false;
    }
    public boolean isParsed(){
        return isFunctionParsed;
    }
    public void setAllFalse() {
        isFunctionParsed = false;
        isEval=false;
        isEvalExtr=false;
        isEvalRoot=false;
        isEvalInter=false;
        for (int i=0;i<NFunctionsMax;i++) An[i].ClearAll();
    }

    public void setString(int i, String s){
        expr_str[i]=s;
        isFunctionParsed = false;
        isEval=false;
    }

    private void ClearEmpty(){
        for (int i=NFunctions-1; i>=0; i--){
            if (expr_str[i].length()==0){
                DeleteFunction(i);
                isFunctionParsed = false;
                isEval=false;
            }
        }
    }

}
