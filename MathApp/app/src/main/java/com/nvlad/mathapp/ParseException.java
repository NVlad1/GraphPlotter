package com.nvlad.mathapp;

/**
 * Created by Vlad on 28.12.2015.
 */
public class ParseException extends Exception {
    private int ID;
    ParseException(int i){
        ID=i;
    }

    public int getID(){
        return ID;
    }
}
