package com.nvlad.mathapp.Keyboard;

import android.app.Activity;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.text.Editable;
import android.text.InputType;
import android.text.Layout;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

/**
 * Created by Vlad on 13.03.2016.
 */


public class CustomKeyboard {
    private final static int CodeDelete   = -5; // Keyboard.KEYCODE_DELETE
    private final static int CodeCancel   = -3; // Keyboard.KEYCODE_CANCEL
    private final static int CodePrev     = 55000;
    private final static int CodeAllLeft  = 55001;
    private final static int CodeLeft     = 55002;
    private final static int CodeRight    = 55003;
    private final static int CodeAllRight = 55004;
    private final static int CodeNext     = 55005;
    private final static int CodeClear    = 55006;
    private final static int CodeAnotherKeyboard    = 55007;
    private final static int CodeSin = 56001;
    private final static int CodeCos = 56002;
    private final static int CodeTg = 56003;
    private final static int CodeCtg = 56004;
    private final static int CodeExp = 56005;
    private final static int CodeSqrt = 56006;
    private final static int CodeAbs = 56007;
    private final static int CodeLog = 56008;
    private final static int CodeLn = 56009;
    private final static int CodeAsin = 56010;
    private final static int CodeAcos = 56011;
    private final static int CodeAtg = 56012;
    private final static int CodeSh = 56013;
    private final static int CodeCh = 56014;
//    private Keyboard mKeyboard, mKeyboard2;
    private KeyboardView mKeyboardView, mKeyboardView2;
    private OnKeyboardStateChangedListener mStateListener;
    private Activity     mHostActivity;
    public CustomKeyboard(Activity host, int viewid, int viewid2, int layoutid, int layoutid2, int mode, OnKeyboardStateChangedListener listener) {
        mHostActivity= host;
        mStateListener=listener;
        if (mode==1) {
            mKeyboardView2 = (KeyboardView) mHostActivity.findViewById(viewid2);
            mKeyboardView2.setKeyboard(new Keyboard(mHostActivity, layoutid2));
            mKeyboardView2.setPreviewEnabled(false); // NOTE Do not show the preview balloons
            mKeyboardView2.setOnKeyboardActionListener(mOnKeyboardActionListener2);
            hideCustomKeyboard2();
            mKeyboardView = (KeyboardView) mHostActivity.findViewById(viewid);
            mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
            mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
            mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        } else{
            mKeyboardView = (KeyboardView) mHostActivity.findViewById(viewid);
            mKeyboardView.setKeyboard(new Keyboard(mHostActivity, layoutid));
            mKeyboardView.setPreviewEnabled(false); // NOTE Do not show the preview balloons
            mKeyboardView.setOnKeyboardActionListener(mOnKeyboardActionListener);
        }
        // Hide the standard keyboard initially
        mHostActivity.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
    }
    private OnKeyboardActionListener mOnKeyboardActionListener = new OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
//            if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
            if (focusCurrent == null) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // Handle key
            if (primaryCode == CodeCancel) {
                hideCustomKeyboard();
            } else if (primaryCode == CodeDelete) {
                if (editable != null && start > 0) editable.delete(start - 1, start);
            } else if (primaryCode == CodeClear) {
                if (editable != null) editable.clear();
            } else if (primaryCode == CodeAnotherKeyboard) {
                hideCustomKeyboard();
                showCustomKeyboard2(focusCurrent);
            } else {// Insert character
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }

        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };
    private KeyboardView.OnKeyboardActionListener mOnKeyboardActionListener2 = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onKey(int primaryCode, int[] keyCodes) {
            // Get the EditText and its Editable
            View focusCurrent = mHostActivity.getWindow().getCurrentFocus();
//          if( focusCurrent==null || focusCurrent.getClass()!=EditText.class ) return;
            if (focusCurrent == null) return;
            EditText edittext = (EditText) focusCurrent;
            Editable editable = edittext.getText();
            int start = edittext.getSelectionStart();
            // Handle key
            if (primaryCode == CodeCancel) {
                hideCustomKeyboard2();
            } else if (primaryCode == CodeDelete) {
                if (editable != null && start > 0) editable.delete(start - 1, start);
            } else if (primaryCode == CodeClear) {
                if (editable != null) editable.clear();
            } else if (primaryCode == CodeAnotherKeyboard) {
                hideCustomKeyboard2();
                showCustomKeyboard(focusCurrent);
            } else if (primaryCode == CodeSin) {
                editable.insert(start, "sin(");
            } else if (primaryCode == CodeCos) {
                editable.insert(start, "cos(");
            } else if (primaryCode == CodeTg) {
                editable.insert(start, "tg(");
            } else if (primaryCode == CodeCtg) {
                editable.insert(start, "ctg(");
            } else if (primaryCode == CodeExp) {
                editable.insert(start, "exp(");
            } else if (primaryCode == CodeSqrt) {
                editable.insert(start, "sqrt(");
            } else if (primaryCode == CodeAbs) {
                editable.insert(start, "abs(");
            } else if (primaryCode == CodeLog) {
                editable.insert(start, "log(");
            } else if (primaryCode == CodeLn) {
                editable.insert(start, "ln(");
            } else if (primaryCode == CodeAsin) {
                editable.insert(start, "asin(");
            } else if (primaryCode == CodeAcos) {
                editable.insert(start, "acos(");
            } else if (primaryCode == CodeAtg) {
                editable.insert(start, "atg(");
            } else if (primaryCode == CodeSh) {
                editable.insert(start, "sh(");
            } else if (primaryCode == CodeCh) {
                editable.insert(start, "ch(");
            } else {// Insert character
                editable.insert(start, Character.toString((char) primaryCode));
            }
        }


        @Override public void onPress(int arg0) {
        }

        @Override public void onRelease(int primaryCode) {
        }

        @Override public void onText(CharSequence text) {
        }

        @Override public void swipeDown() {
        }

        @Override public void swipeLeft() {
        }

        @Override public void swipeRight() {
        }

        @Override public void swipeUp() {
        }
    };

    public void hideCustomKeyboard() {
        mKeyboardView.setVisibility(View.GONE);
        mKeyboardView.setEnabled(false);
        mStateListener.OnHide(mKeyboardView);
    }

    private void showCustomKeyboard( View v ) {
        mKeyboardView.setVisibility(View.VISIBLE);
        mKeyboardView.setEnabled(true);
        mStateListener.OnDisplay(v, mKeyboardView);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public boolean isCustomKeyboardVisible() {
        return mKeyboardView.getVisibility() == View.VISIBLE;
    }

    public void openKeyboard2(View v)
    {
        mKeyboardView2.setVisibility(View.VISIBLE);
        mKeyboardView2.setEnabled(true);
        if( v!=null)((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public void hideCustomKeyboard2() {
        mKeyboardView2.setVisibility(View.GONE);
        mKeyboardView2.setEnabled(false);
    }

    private void showCustomKeyboard2( View v ) {
        mKeyboardView2.setVisibility(View.VISIBLE);
        mKeyboardView2.setEnabled(true);
        if( v!=null ) ((InputMethodManager)mHostActivity.getSystemService(Activity.INPUT_METHOD_SERVICE)).hideSoftInputFromWindow(v.getWindowToken(), 0);
    }

    public boolean isCustomKeyboard2Visible() {
    //    return mKeyboardView2.getVisibility() == View.VISIBLE;
        return false;
    }

    public EditText registerEditText(int resid, String str) {
        // Find the EditText 'resid'
        final EditText edittext= (EditText)mHostActivity.findViewById(resid);
        // Make the custom keyboard appear
        edittext.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            // NOTE By setting the on focus listener, we can show the custom keyboard when the edit box gets focus, but also hide it when the edit box loses focus
            @Override public void onFocusChange(View v, boolean hasFocus) {
                if( hasFocus ) showCustomKeyboard(v); else hideCustomKeyboard();
            }
        });
        edittext.setOnClickListener(new View.OnClickListener() {
            // NOTE By setting the on click listener, we can show the custom keyboard again, by tapping on an edit box that already had focus (but that had the keyboard hidden).
            @Override public void onClick(View v) {
                showCustomKeyboard(v);
            }
        });
        // Disable standard keyboard hard way
        // NOTE There is also an easy way: 'edittext.setInputType(InputType.TYPE_NULL)' (but you will not have a cursor, and no 'edittext.setCursorVisible(true)' doesn't work )
        edittext.setOnTouchListener(new View.OnTouchListener() {
            @Override public boolean onTouch(View v, MotionEvent event) {
                EditText edittext = (EditText) v;
                int inType = edittext.getInputType();       // Backup the input type
                edittext.setInputType(InputType.TYPE_NULL); // Disable standard keyboard
                edittext.onTouchEvent(event);               // Call native handler
                edittext.setInputType(inType);              // Restore input type
                return true; // Consume touch event
            }
        });
        // Disable spell check (hex strings look like words to Android)
        edittext.setInputType(edittext.getInputType() | InputType.TYPE_TEXT_FLAG_NO_SUGGESTIONS);
        edittext.setText(str);
        View.OnTouchListener otl = new View.OnTouchListener() {

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (!(isCustomKeyboardVisible())) {
                    showCustomKeyboard(v);
                }
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        Layout layout = ((EditText) v).getLayout();
                        float x = event.getX() + edittext.getScrollX();
                        int offset = layout.getOffsetForHorizontal(0, x);
                        if (offset > 0)
                            if (x > layout.getLineMax(0))
                                edittext.setSelection(offset);     // touch was at the end of the text
                            else
                                edittext.setSelection(offset - 1);
                        break;
                    case MotionEvent.ACTION_MOVE:
                        layout = ((EditText) v).getLayout();
                        x = event.getX() + edittext.getScrollX();
                        offset = layout.getOffsetForHorizontal(0, x);
                        if (offset > 0)
                            if (x > layout.getLineMax(0))
                                edittext.setSelection(offset);     // Touchpoint was at the end of the text
                            else
                                edittext.setSelection(offset - 1);
                        break;

                }
                v.requestFocus();
                return true;
            }
        };
        edittext.setOnTouchListener(otl);
        return edittext;
    }


}
