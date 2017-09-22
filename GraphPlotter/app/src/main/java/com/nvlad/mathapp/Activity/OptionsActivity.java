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
import com.nvlad.mathapp.R;


public class OptionsActivity extends AppCompatActivity implements OnKeyboardStateChangedListener {
    private FunctionLab FLab;
    Double xmin,xmax,ymin,ymax;
    EditText mXminField, mXmaxField, mYminField, mYmaxField;
    CustomKeyboard mCustomKeyboard;

    final ErrorAlert err1 = new ErrorAlert();

    private void SaveData(EditText edit, int ID){
        double x;
        String str = edit.getText().toString();
        try {
            x = Double.parseDouble(str);
        }
        catch(NumberFormatException e){
            err1.ShowError(OptionsActivity.this, 4);
            edit.setText("0.0");
            x=0.0;
        }
        if (ID==0) FLab.setXmin(4.0/3.0*x-1.0/3.0*FLab.getXmax()) ;
        if (ID==1) FLab.setXmax(4.0/3.0*x-1.0/3.0*FLab.getXmin());
        if (ID==2) FLab.setYmin(4.0/3.0*x-1.0/3.0*FLab.getYmax());
        if (ID==3) FLab.setYmax(4.0/3.0*x-1.0/3.0*FLab.getYmin());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        FLab = FunctionLab.get();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.id.keyboardview, R.xml.keyboard3, R.xml.keyboard3, 1, this);
        xmin = 0.75*FLab.getXmin()+0.25*FLab.getXmax();
        xmax = 0.25*FLab.getXmin()+0.75*FLab.getXmax();
        ymin = 0.75*FLab.getYmin()+0.25*FLab.getYmax();
        ymax = 0.25*FLab.getYmin()+0.75*FLab.getYmax();
        mXminField = mCustomKeyboard.registerEditText(R.id.xmin_str,xmin.toString());
        mXmaxField = mCustomKeyboard.registerEditText(R.id.xmax_str,xmax.toString());
        mYminField = mCustomKeyboard.registerEditText(R.id.ymin_str,ymin.toString());
        mYmaxField = mCustomKeyboard.registerEditText(R.id.ymax_str,ymax.toString());
        mXminField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(mXminField,0);
                }

            }
        });
        mXmaxField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(mXmaxField,1);
                }

            }
        });
        mYminField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(mYminField,2);
                }

            }
        });
        mYmaxField.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            public void onFocusChange(View v, boolean hasFocus) {
                if(!hasFocus) {
                    SaveData(mYmaxField,3);
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
    public void onBackPressed() {
        SaveData(mXminField,0);
        SaveData(mXmaxField,1);
        SaveData(mYminField,2);
        SaveData(mYmaxField,3);
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else
        if ( mCustomKeyboard.isCustomKeyboard2Visible() ) mCustomKeyboard.hideCustomKeyboard2(); else this.finish();
//        super.onBackPressed();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }
}
