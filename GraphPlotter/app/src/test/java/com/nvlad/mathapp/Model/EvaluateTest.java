package com.nvlad.mathapp.Model;

import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Exception.UnknownException;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 * Created by Naboka Vladislav on 15.09.2017.
 */
public class EvaluateTest {
//    private Evaluate mSubject;
    private double precision = 1e-10;
    private double easyPrecision = 1e-4;


    @Test
    public void function1() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("x", 0);
        assertEquals(mSubject.Evaluator(4.0),4.0,precision);
        assertEquals(mSubject.Evaluator(-3.0),-3.0,precision);
        assertEquals(mSubject.Evaluator(-12312312312.0),-12312312312.0,precision);
    }

    @Test
    public void function2() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("x^2", 0);
        assertEquals(mSubject.Evaluator(4.0),16.0,precision);
        assertEquals(mSubject.Evaluator(-3.0),9.0,precision);
        assertEquals(mSubject.Evaluator(-2.5),6.25,precision);
    }

    @Test
    public void function3() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("sin(x)^2", 0);
        assertEquals(mSubject.Evaluator(0.4),0.151646645,1e-7);
        assertEquals(mSubject.Evaluator(9.2),0.049679914,1e-7);
        assertEquals(mSubject.Evaluator(0.0),0.0,precision);
    }

    @Test
    public void function4() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("((((5))*x))", 0);
        assertEquals(mSubject.Evaluator(3.0),15.0,precision);
        assertEquals(mSubject.Evaluator(2.22),11.1,precision);
        assertEquals(mSubject.Evaluator(0.0),0.0,precision);
    }

    @Test
    public void function5() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("x!", 0);
        assertEquals(mSubject.Evaluator(2.0),2.0,easyPrecision);
        assertEquals(mSubject.Evaluator(3.0),6.0,easyPrecision);
        assertEquals(mSubject.Evaluator(4.0),24.0,easyPrecision);
        assertEquals(mSubject.Evaluator(5.0),120.0,easyPrecision);
        assertEquals(mSubject.Evaluator(7.0),5040.0,easyPrecision);
    }

    @Test
    public void function6() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("-3x", 0);
        assertEquals(mSubject.Evaluator(2.0),-6.0,precision);
        assertEquals(mSubject.Evaluator(3.0),-9.0,precision);
        assertEquals(mSubject.Evaluator(4.0),-12.0,precision);
        assertEquals(mSubject.Evaluator(5.0),-15.0,precision);
        assertEquals(mSubject.Evaluator(7.0),-21.0,precision);
    }

    @Test
    public void function7() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("2*((((3))+x*0))", 0);
        assertEquals(mSubject.Evaluator(2.0),6.0,precision);
        assertEquals(mSubject.Evaluator(3.0),6.0,precision);
        assertEquals(mSubject.Evaluator(4.0),6.0,precision);
        assertEquals(mSubject.Evaluator(5.0),6.0,precision);
        assertEquals(mSubject.Evaluator(7.0),6.0,precision);
    }

    @Test
    public void function8() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("sin(cos(x))", 0);
        assertEquals(mSubject.Evaluator(2.0),-0.404239,easyPrecision);
        assertEquals(mSubject.Evaluator(3.0),-0.836022,easyPrecision);
        assertEquals(mSubject.Evaluator(4.0),-0.608083,easyPrecision);
        assertEquals(mSubject.Evaluator(5.0),0.279873,easyPrecision);
        assertEquals(mSubject.Evaluator(7.0),0.684489,easyPrecision);
    }

    @Test
    public void function9() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("asin(0.8cos(x))", 0);
        assertEquals(mSubject.Evaluator(-3.0),-0.914068,easyPrecision);
        assertEquals(mSubject.Evaluator(2.0),-0.339396,easyPrecision);
        assertEquals(mSubject.Evaluator(4.0),-0.550267,easyPrecision);
        assertEquals(mSubject.Evaluator(7.0),0.647409,easyPrecision);
    }

    @Test
    public void function10() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("asin(x)", 0);
        assertEquals(mSubject.Evaluator(0.5),0.523599,easyPrecision);
        assertEquals(mSubject.Evaluator(-0.3),-0.304693,easyPrecision);
        assertTrue(Double.isNaN(mSubject.Evaluator(4.0)));
        assertTrue(Double.isNaN(mSubject.Evaluator(-2.0)));
        assertTrue(Double.isNaN(mSubject.Evaluator(11.123654)));
    }

    @Test
    public void function11() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("exp(ch(x-x/2.0))", 0);
        assertEquals(mSubject.Evaluator(-3.0),10.510866,easyPrecision);
        assertEquals(mSubject.Evaluator(0.5),2.805027,easyPrecision);
        assertEquals(mSubject.Evaluator(2.0),4.678982,easyPrecision);
        assertEquals(mSubject.Evaluator(6.6),786038.49342,easyPrecision);
    }

    @Test
    public void function12() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("(((((((((((((((((((((((((((((((((((7)))))))))))))))))))))))))))))))))))", 0);
        assertEquals(mSubject.Evaluator(-3.0),7.0,precision);
        assertEquals(mSubject.Evaluator(0.5),7.0,precision);
        assertEquals(mSubject.Evaluator(2.0),7.0,precision);
        assertEquals(mSubject.Evaluator(6.6),7.0,precision);
    }

    @Test
    public void function13() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("(abs(x)+1)^3", 0);
        assertEquals(mSubject.Evaluator(-2.0),27.0,precision);
        assertEquals(mSubject.Evaluator(-1.0),8.0,precision);
        assertEquals(mSubject.Evaluator(3.0),64.0,precision);
        assertEquals(mSubject.Evaluator(0.0),1.0,precision);
    }

    @Test
    public void function14() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("((ln(x)-(abs(0))*(55-x*3+3x*x^2-(sin(x))^3))^(x-2x+x+cos(0.0*exp(x/5-x/10.0))))", 0);
        assertEquals(mSubject.Evaluator(0.5),-0.693147,easyPrecision);
        assertEquals(mSubject.Evaluator(1.0),0.0,easyPrecision);
        assertEquals(mSubject.Evaluator(2.0),0.693147,easyPrecision);
        assertEquals(mSubject.Evaluator(4.0),1.386294,easyPrecision);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction1() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("((((_))))", 0);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction2() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("x++5", 0);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction3() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("xx", 0);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction4() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("3x2", 0);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction5() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("((x)+2", 0);
        assertEquals(mSubject.Evaluator(2.0),4.0,precision);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction6() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("aasin(x)", 0);
        assertEquals(mSubject.Evaluator(2.0),4.0,precision);
    }

    @Test(expected = ParseException.class)
    public void wrongFunction7() throws ParseException, UnknownException{
        Evaluate mSubject = new Evaluate("sinx", 0);
        assertEquals(mSubject.Evaluator(2.0),4.0,precision);
    }
}