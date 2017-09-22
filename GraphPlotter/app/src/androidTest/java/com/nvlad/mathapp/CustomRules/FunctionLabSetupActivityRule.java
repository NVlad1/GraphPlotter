package com.nvlad.mathapp.CustomRules;

import android.app.Activity;
import android.support.test.rule.ActivityTestRule;

import com.nvlad.mathapp.Model.FunctionLab;
import com.nvlad.mathapp.Model.Symbol;

/**
 * Created by Naboka Vladislav on 18.09.2017.
 */

public class FunctionLabSetupActivityRule<A extends Activity> extends ActivityTestRule<A> {
    public FunctionLabSetupActivityRule(Class<A> activityClass) {
        super(activityClass);
    }

    @Override
    protected void beforeActivityLaunched(){
        super.beforeActivityLaunched();
        FunctionLab Flab = FunctionLab.get();
        Flab.clear();
        try {
            Flab.AddFunction("x");
            Flab.AddFunction("x+2");
            Flab.AddFunction("(x-6)^3");
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Symbol.setPar_a(2.0);
        Symbol.setPar_b(-1.0);
        // Maybe prepare some mock service calls
        // Maybe override some depency injection modules with mocks
    }
}
