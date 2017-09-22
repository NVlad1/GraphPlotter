package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.AnalysisException;
import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by Naboka Vladislav on 15.09.2017.
 */
public class AnalysisTest {


    @Before
    public void setUp(){
        FunctionLab Flab = FunctionLab.get();
        Flab.clear();
    }

    @Test
    public void function1() throws ParseException, UnknownException, AnalysisException {
        FunctionLab Flab = FunctionLab.get();
        Flab.AddFunction("x");
        Flab.ParseFunctions();
        Flab.EvaluateFunctions();
        Flab.CalculateExtrema();
        Flab.CalculateRoots();
        Flab.CalculateIntersections();
        assertEquals(Flab.getExtrNum(0),0);
        assertEquals(Flab.getRootNum(0),1);
        assertEquals(Flab.getIntersectionsNum(),0);
        assertEquals(Flab.getRoot(0,0),0,1e-7);
    }

    @Test
    public void function2() throws ParseException, UnknownException, AnalysisException {
        FunctionLab Flab = FunctionLab.get();
        Flab.AddFunction("(x-2.6)^2-2");
        initializeFlab(Flab);
        assertEquals(Flab.getExtrNum(0),1);
        assertEquals(Flab.getRootNum(0),2);
        assertEquals(Flab.getIntersectionsNum(),0);
        assertEquals(Flab.getExtrX(0,0),2.6,0.001);
        assertEquals(Flab.getExtrY(0,0),-2.0,0.001);
    }

    @Test
    public void function3() throws ParseException, UnknownException, AnalysisException {
        FunctionLab Flab = FunctionLab.get();
        Flab.AddFunction("x^5-4x^4+((((x-3)))^3)+3.0");
        initializeFlab(Flab);
        assertEquals(Flab.getExtrNum(0),2);
        assertEquals(Flab.getRootNum(0),1);
        assertEquals(Flab.getIntersectionsNum(),0);
        assertEquals(Flab.getExtrX(0,0),1.0248,0.001);
        assertEquals(Flab.getExtrY(0,0),-7.9876,0.001);
        assertEquals(Flab.getExtrX(0,1),3.19926,0.001);
        assertEquals(Flab.getExtrY(0,1),-80.8781,0.001);
        assertEquals(Flab.getRoot(0,0),3.984311,0.001);
    }

    @Test
    public void MultipleFunction1() throws ParseException, UnknownException, AnalysisException {
        FunctionLab Flab = FunctionLab.get();
        Flab.AddFunction("x");
        Flab.AddFunction("(x-2)^2+1");
        Flab.AddFunction("cos(x+2.1)");
        initializeFlab(Flab);
        assertEquals(Flab.getExtrNum(0),0);
        assertEquals(Flab.getRootNum(0),1);
        assertEquals(Flab.getExtrNum(1),1);
        assertEquals(Flab.getRootNum(1),0);
        assertEquals(Flab.getIntersectionsNum(),3);
        assertEquals(Flab.getExtrX(1,0),2.0,0.001);
        assertEquals(Flab.getIntersectX(0),1.38197,0.001);
        assertEquals(Flab.getIntersectX(1),3.61803,0.001);
        assertEquals(Flab.getIntersectX(2),-0.2630,0.001);
        assertEquals(Flab.getIntersectY(0),1.38197,0.001);
        assertEquals(Flab.getIntersectY(1),3.61803,0.001);
        assertEquals(Flab.getIntersectY(2),-0.2630,0.001);
    }

    @Test
    public void MultipleFunction2() throws ParseException, UnknownException, AnalysisException {
        FunctionLab Flab = FunctionLab.get();
        Flab.AddFunction("4x");
        Flab.AddFunction("(-2)*x");
        Flab.AddFunction("x+(x^3-x^3)*sin(x)");
        initializeFlab(Flab);
        assertEquals(Flab.getExtrNum(0),0);
        assertEquals(Flab.getRootNum(0),1);
        assertEquals(Flab.getExtrNum(1),0);
        assertEquals(Flab.getRootNum(1),1);
        assertEquals(Flab.getExtrNum(2),0);
        assertEquals(Flab.getRootNum(2),1);
        assertEquals(Flab.getRoot(0,0),0.0,1e-7);
        assertEquals(Flab.getRoot(1,0),0.0,1e-7);
        assertEquals(Flab.getRoot(2,0),0.0,1e-7);
        assertEquals(Flab.getIntersectionsNum(),3);
        assertEquals(Flab.getIntersectX(0),0.0,0.001);
        assertEquals(Flab.getIntersectX(1),0.0,0.001);
        assertEquals(Flab.getIntersectX(2),0.0,0.001);
        assertEquals(Flab.getIntersectY(0),0.0,0.001);
        assertEquals(Flab.getIntersectY(1),0.0,0.001);
        assertEquals(Flab.getIntersectY(2),0.0,0.001);
    }

    private void initializeFlab (FunctionLab Flab) throws ParseException, UnknownException, AnalysisException{
        Flab.setXmin(-20.0);
        Flab.setXmax(20.0);
        Flab.setYmin(-20.0);
        Flab.setYmax(20.0);
        Flab.ParseFunctions();
        Flab.EvaluateFunctions();
        Flab.CalculateExtrema();
        Flab.CalculateRoots();
        Flab.CalculateIntersections();
    }

}