package com.nvlad.mathapp.Activity;

import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.nvlad.mathapp.Exception.ErrorAlert;
import com.nvlad.mathapp.Keyboard.CustomKeyboard;
import com.nvlad.mathapp.Keyboard.OnKeyboardStateChangedListener;
import com.nvlad.mathapp.Model.FunctionLab;
import com.nvlad.mathapp.Model.Symbol;
import com.nvlad.mathapp.R;


public class ParamsActivity extends AppCompatActivity implements OnKeyboardStateChangedListener {
    private FunctionLab FLab;
    EditText par_a,par_b,par_c,par_d;
    Double _a,_b,_c,_d;
    boolean isChanged;
    CustomKeyboard mCustomKeyboard;
    final ErrorAlert err1 = new ErrorAlert();

    private void SaveData(EditText edit, int ID){
        double x;
        String str = edit.getText().toString();
//                str = str.replaceAll("[^\\d.]", "");
        try {
            x = Double.parseDouble(str);
        }
        catch(NumberFormatException e){
            err1.ShowError(ParamsActivity.this, 4);
            edit.setText("0.0");
            x=0.0;
        }
        if (ID==0) Symbol.setPar_a(x);
        if (ID==1) Symbol.setPar_b(x);
        if (ID==2) Symbol.setPar_c(x);
        if (ID==3) Symbol.setPar_d(x);
        isChanged=true;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_params);
        FLab = FunctionLab.get();
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.id.keyboardview, R.xml.keyboard3, R.xml.keyboard3, 1, this);
        isChanged=false;
        par_a = (EditText) findViewById(R.id.params_a);
        par_b = (EditText) findViewById(R.id.params_b);
        par_c = (EditText) findViewById(R.id.params_c);
        par_d = (EditText) findViewById(R.id.params_d);
        _a = Symbol.getPar_a();
        _b = Symbol.getPar_b();
        _c = Symbol.getPar_c();
        _d = Symbol.getPar_d();
        par_a = mCustomKeyboard.registerEditText(R.id.params_a,_a.toString());
        par_b = mCustomKeyboard.registerEditText(R.id.params_b,_b.toString());
        par_c = mCustomKeyboard.registerEditText(R.id.params_c,_c.toString());
        par_d = mCustomKeyboard.registerEditText(R.id.params_d,_d.toString());
        par_a.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(par_a,0);
                }

            }
        });
        par_b.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(par_b,1);
                }

            }
        });
        par_c.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(par_c,2);
                }

            }
        });
        par_d.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(par_d,3);
                }

            }
        });
    }

    @Override
    public void OnDisplay(View currentview, KeyboardView currentKeyboard) {
        ScrollView mScroll = (ScrollView) findViewById(R.id.scroll_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.MATCH_PARENT);
        params.addRule(RelativeLayout.ABOVE,  currentKeyboard.getId());
        mScroll.setLayoutParams(params);
        mScroll.scrollTo(0, currentview.getBaseline()); //Scrolls to focused EditText

    }

    @Override
    public void OnHide(KeyboardView currentKeyboard) {
        ScrollView mScroll = (ScrollView) findViewById(R.id.scroll_content);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        mScroll.setLayoutParams(params);

    }

    @Override
    public void onBackPressed(){
        SaveData(par_a,0);
        SaveData(par_b,1);
        SaveData(par_c,2);
        SaveData(par_d,3);
        if (isChanged) {
            FLab.setAllFalse();
        }
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else
        if ( mCustomKeyboard.isCustomKeyboard2Visible() ) mCustomKeyboard.hideCustomKeyboard2(); else this.finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_params, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
