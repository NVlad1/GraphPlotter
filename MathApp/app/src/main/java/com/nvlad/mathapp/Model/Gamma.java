package com.nvlad.mathapp.Model;

/**
 * Created by Vlad on 31.05.2016.
 */

class Gamma {
    private final int Nmax = 10000;
    private final double step = 0.01;
    private final double x_in = 5.0;
    private final double x_fin = 10.0;

    Gamma() {}

    private double Stirling(double x){
        return Math.sqrt(2*Math.PI*x)*Math.pow(x/Math.exp(1),x);
    }

    private double direct(double x){
        double sum = 0.0;
        for (int i=0; i<Nmax; i++){
            sum += subGamma(step*i,x)*step;
        }
        return sum;
    }

    private double subGamma (double t, double x){
        return Math.pow(t,x-1)*Math.exp(-t);
    }

    double GammaFunc(double x){
        if (x<0) return Double.NaN;
        if (x==0) return 1.0;
        if (x<x_in) return direct(x);
        if ((x>x_in)&(x<x_fin)){
            double w = (x-x_in)/(x_fin-x_in);
            return direct(x)*(1-w)+Stirling(x)*w;
        }
        return Stirling(x);
    }
}
