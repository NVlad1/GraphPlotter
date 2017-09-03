package com.nvlad.mathapp.Exception;

/**
 * Created by Vlad on 28.12.2015.
 */
public class ParseException extends Exception {
    private int ID;
    public ParseException(int i){
        ID=i;
    }

    public int getID(){
        return ID;
    }
}
