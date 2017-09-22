package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.AnalysisException;
import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

/**
 * Created by Vlad on 02.02.2016.
 */
class Analysis {
    private int NumberOfExtrema;
    private int NumberOfRoots;
    private static final int MaxNumberOfExtrema = 500;
    private static final int MaxNumberOfRoots = 500;
    private double ExtrX[];
    private double ExtrY[];
    private double RootX[];
    private boolean isExtremaMaximum[]; // true for maximum, false for minimum;
    private int MaxIter=5000;


    Analysis(){
        ExtrX = new double[MaxNumberOfExtrema];
        ExtrY = new double[MaxNumberOfExtrema];
        RootX = new double[MaxNumberOfRoots];
        isExtremaMaximum = new boolean[MaxNumberOfExtrema];
        NumberOfExtrema=0;
        NumberOfRoots=0;
    }

    void ClearAll(){
/*        for (int i=0;i<NumberOfExtrema;i++){
            ExtrX[i]=0;
            ExtrY[i]=0;
        }
        for (int i=0;i<NumberOfRoots;i++) RootX[i]=0;*/
        NumberOfExtrema=0;
        NumberOfRoots=0;
    }

    void AddExtrema(double x, double y, boolean z){
        ExtrX[NumberOfExtrema]=x;
        ExtrY[NumberOfExtrema]=y;
        isExtremaMaximum[NumberOfExtrema]=z;
        NumberOfExtrema++;
        if (Math.abs(y)<1e-5){RootX[NumberOfRoots]=x; NumberOfRoots++;}
    }

    private void AddRoot(double xmin, double ymin, double xmax, double ymax, Evaluate Eval)
                                                    throws ParseException, UnknownException, AnalysisException
    {
        double x,y,dy,y1;
        int i=0;
        final double dx = 0.001;
        x=(xmax+xmin)/2.0;
        y=Eval.Evaluator(x);
        while (Math.abs(y)>0.0001) {
            y = Eval.Evaluator(x);
            dy = Eval.Evaluator(x + dx) - y;
            y1 = dy / dx;
            x-=y/y1;
            i++;
            if (i>MaxIter) throw new AnalysisException();
        }
        RootX[NumberOfRoots]=x;
        NumberOfRoots++;
    }

    void CalculateRoots(double xmin, double xmax, double ymin, double ymax, Evaluate Eval) //ymin and ymax should be y0[0] and y0[xmax]
                                                    throws ParseException, UnknownException, AnalysisException
    {
        if (NumberOfExtrema==0) {
            if(ymax*ymin<=0) AddRoot(xmin, ymin, xmax, ymax, Eval);
        }
        else
        {
            for (int i = 0; i <= NumberOfExtrema; i++) {
                if ((i == 0) & (ymin * ExtrY[0] < 0)) AddRoot(xmin, ymin, ExtrX[0], ExtrY[0], Eval);
                if (NumberOfExtrema > 0) {
                    if ((i == NumberOfExtrema) & (ymax * ExtrY[NumberOfExtrema - 1] < 0))
                        AddRoot(ExtrX[NumberOfExtrema - 1], ExtrY[NumberOfExtrema - 1], xmax, ymax, Eval);
                }
                if ((i > 0) & (i < NumberOfExtrema)) {
                    if (ExtrY[i - 1] * ExtrY[i] < 0)
                        AddRoot(ExtrX[i - 1], ExtrY[i - 1], ExtrX[i], ExtrY[i], Eval);
                }
            }
        }
    }

    int getExtrNum(){
        return NumberOfExtrema;
    }
    int getRootNum(){
        return NumberOfRoots;
    }

    double getExtrX(int i){
        return ExtrX[i];
    }
    double getExtrY(int i){
        return ExtrY[i];
    }
    double getRoot(int i){
        return RootX[i];
    }
}
