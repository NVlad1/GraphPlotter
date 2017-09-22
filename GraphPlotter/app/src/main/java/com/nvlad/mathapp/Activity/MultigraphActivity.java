package com.nvlad.mathapp.Activity;

import android.graphics.Color;
import android.inputmethodservice.KeyboardView;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.nvlad.mathapp.Exception.ErrorAlert;
import com.nvlad.mathapp.Exception.ParseException;
import com.nvlad.mathapp.Keyboard.CustomKeyboard;
import com.nvlad.mathapp.Keyboard.OnKeyboardStateChangedListener;
import com.nvlad.mathapp.Model.FunctionLab;
import com.nvlad.mathapp.R;


public class MultigraphActivity extends AppCompatActivity implements OnKeyboardStateChangedListener {
//public class MultigraphActivity extends Activity {

    static final int NMax = 7;
    static int N;
    private FunctionLab FLab;
    private ErrorAlert err2;
    CustomKeyboard mCustomKeyboard;
    TextView text[];
    EditText Fedit[];
    ImageButton btn[];
//    Spinner spinner[];

    @SuppressWarnings("deprecation")
    private static String fromHtml(String html){
        String result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            result = Html.fromHtml(html,Html.FROM_HTML_MODE_LEGACY).toString();
        } else {
            result = Html.fromHtml(html).toString();
        }
        return result;
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
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_multigraph);
        // Create the Keyboard
        mCustomKeyboard = new CustomKeyboard(this, R.id.keyboardview, R.id.keyboardview2, R.xml.keyboard, R.xml.keyboard2, 1, this);
        // Install the key handler
        err2 = new ErrorAlert();
        FLab = FunctionLab.get();
        N = FLab.getNFunctions();
        if (N==0){
            N++;
            try{
            FLab.AddFunction();
            }
            catch (ParseException e){
                err2.ShowError(MultigraphActivity.this,1);
            }
        }
        Fedit = new EditText[NMax];
        btn = new ImageButton[NMax];
        text = new TextView[NMax];
//        spinner = new Spinner[NMax];

        String phi = "&#966;";
        String[] data = {"y(x)", "x(y)", "r("+ fromHtml(phi)+"=x)", "x(t)=", "implicit"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, data);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        btn[0] = (ImageButton) findViewById(R.id.btn0);
        btn[1] = (ImageButton) findViewById(R.id.btn1);
        btn[2] = (ImageButton) findViewById(R.id.btn2);
        btn[3] = (ImageButton) findViewById(R.id.btn3);
        btn[4] = (ImageButton) findViewById(R.id.btn4);
        btn[5] = (ImageButton) findViewById(R.id.btn5);
        btn[6] = (ImageButton) findViewById(R.id.btn6);

        text[0] = (TextView) findViewById(R.id.text0);
        text[1] = (TextView) findViewById(R.id.text1);
        text[2] = (TextView) findViewById(R.id.text2);
        text[3] = (TextView) findViewById(R.id.text3);
        text[4] = (TextView) findViewById(R.id.text4);
        text[5] = (TextView) findViewById(R.id.text5);
        text[6] = (TextView) findViewById(R.id.text6);

       /* spinner[0] = (Spinner) findViewById(R.id.spinner0);
        spinner[1] = (Spinner) findViewById(R.id.spinner1);
        spinner[2] = (Spinner) findViewById(R.id.spinner2);
        spinner[3] = (Spinner) findViewById(R.id.spinner3);
        spinner[4] = (Spinner) findViewById(R.id.spinner4);
        spinner[5] = (Spinner) findViewById(R.id.spinner5);
        spinner[6] = (Spinner) findViewById(R.id.spinner6);*/
/*        for (int i=0; i<NMax; i++){
            final int i1=i;
            spinner[i].setAdapter(adapter);
            spinner[i].setSelection(0);
            spinner[i].setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view,
                                           int position, long id) {
                    // показываем позиция нажатого элемента
//                    Toast.makeText(getBaseContext(), "Position = " + position, Toast.LENGTH_SHORT).show();
                    FLab.setMode(i1,position);
                }
                @Override
                public void onNothingSelected(AdapterView<?> arg0) {
                }
            });
        }*/
        text[0] = (TextView) findViewById(R.id.text0);
        text[1] = (TextView) findViewById(R.id.text1);
        text[2] = (TextView) findViewById(R.id.text2);
        text[3] = (TextView) findViewById(R.id.text3);
        text[4] = (TextView) findViewById(R.id.text4);
        text[5] = (TextView) findViewById(R.id.text5);
        text[6] = (TextView) findViewById(R.id.text6);

        Fedit[0] = mCustomKeyboard.registerEditText(R.id.Fedit0,FLab.getString(0));
        Fedit[1] = mCustomKeyboard.registerEditText(R.id.Fedit1,FLab.getString(1));
        Fedit[2] = mCustomKeyboard.registerEditText(R.id.Fedit2,FLab.getString(2));
        Fedit[3] = mCustomKeyboard.registerEditText(R.id.Fedit3,FLab.getString(3));
        Fedit[4] = mCustomKeyboard.registerEditText(R.id.Fedit4,FLab.getString(4));
        Fedit[5] = mCustomKeyboard.registerEditText(R.id.Fedit5,FLab.getString(5));
        Fedit[6] = mCustomKeyboard.registerEditText(R.id.Fedit6,FLab.getString(6));

        Fedit[0].setTextColor(Color.BLACK);
        Fedit[1].setTextColor(Color.BLUE);
        Fedit[2].setTextColor(Color.RED);
        Fedit[3].setTextColor(Color.GREEN);
        Fedit[4].setTextColor(Color.CYAN);
        Fedit[5].setTextColor(Color.MAGENTA);
        Fedit[6].setTextColor(Color.DKGRAY);
        /*text[0].setTextColor(Color.BLACK);
        text[1].setTextColor(Color.BLUE);
        text[2].setTextColor(Color.RED);
        text[3].setTextColor(Color.GREEN);
        text[4].setTextColor(Color.CYAN);
        text[5].setTextColor(Color.YELLOW);
        text[6].setTextColor(Color.MAGENTA);*/

        for (int i=N; i<NMax; i++){
            Fedit[i].setVisibility(View.GONE);
//            spinner[i].setVisibility(View.GONE);
            text[i].setVisibility(View.GONE);
            btn[i].setVisibility(View.GONE);
        }

        for (int i=0; i<NMax; i++){
            final int i1=i;
            btn[i].setBackgroundColor(Color.TRANSPARENT);
            btn[i].setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    for (int j=i1; j<N-1; j++){
                        Fedit[j].setText(Fedit[j+1].getText());
                    }
                    Fedit[N-1].setText("");
                    Fedit[N-1].setVisibility(View.GONE);
                    text[N-1].setVisibility(View.GONE);
 //                   spinner[N-1].setVisibility(View.GONE);
                    btn[N-1].setVisibility(View.GONE);
                    FLab.DeleteFunction(N - 1);
                    N--;
                }
            });
        }

        for (int i=0;i<NMax;i++){
        final int i1=i;
            Fedit[i].addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence c, int start, int before, int count) {
                    FLab.setString(i1,c.toString());
                }

            // this space intentionally left blank
                public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
                }

                public void afterTextChanged(Editable c) {
                     FLab.setString(i1,c.toString());
//                   xmin = Double.parseDouble(c.toString());
//                   MainActivity.setXmin(xmin);
                }
            });

        }
    }

    @Override
    public void onBackPressed() {
        try {
            FLab.ParseFunctions();
        }
        catch (ParseException e)
        {
            if ((e.getID()>=0)&(e.getID()< FLab.getnumberofdots())){
                err2.ShowError(MultigraphActivity.this,1,e.getID());
            }
            else err2.ShowError(MultigraphActivity.this,1);
        }
        if( mCustomKeyboard.isCustomKeyboardVisible() ) mCustomKeyboard.hideCustomKeyboard(); else
        if ( mCustomKeyboard.isCustomKeyboard2Visible() ) mCustomKeyboard.hideCustomKeyboard2(); else this.finish();
//        super.onBackPressed();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
/*        try {
            FLab.ParseFunctions();
        }
        catch (ParseException e)
        {
            if ((e.getID()>=0)&(e.getID()< FLab.getnumberofdots())){
                err2.ShowError(MultigraphActivity.this,1,e.getID());
            }
            else err2.ShowError(MultigraphActivity.this,1);
        }*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_multigraph, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_mult_add:
                if (N<NMax) {
                   Fedit[N].setVisibility(View.VISIBLE);
 //                  spinner[N].setVisibility(View.VISIBLE);
                   text[N].setVisibility(View.VISIBLE);
                   btn[N].setVisibility(View.VISIBLE);
                   try{
                        FLab.AddFunction("");
                        N++;
                    }
                   catch(ParseException e){
                    //
                   }
                }

                return true;
            case R.id.menu_item_mult_rem:
                if (N>1){
                    Fedit[N-1].setVisibility(View.GONE);
//                    spinner[N-1].setVisibility(View.GONE);
                    text[N-1].setVisibility(View.GONE);
                    btn[N-1].setVisibility(View.GONE);
                    FLab.DeleteFunction(N - 1);
                    N--;
                }
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }}
