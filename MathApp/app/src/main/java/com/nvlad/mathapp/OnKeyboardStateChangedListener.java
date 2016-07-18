package com.nvlad.mathapp;

import android.inputmethodservice.KeyboardView;
import android.view.View;

/**
 * Created by Vlad on 15.04.2016.
 */
public interface OnKeyboardStateChangedListener
{
    public void OnDisplay(View currentview, KeyboardView currentKeyboard);

    public void OnHide(KeyboardView currentKeyboard);
}
