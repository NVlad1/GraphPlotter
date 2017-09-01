package com.nvlad.mathapp.Exception;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Vlad on 25.12.2015.
 */
public class ErrorAlert {
    private int ErrCode;

    public void setError(int code){
        ErrCode=code;
    }

    public boolean isError(){
        return ErrCode!=0;
    }

    public void ShowError(Context c0, int code){
        ErrCode=code;
        AlertDialog.Builder builder = new AlertDialog.Builder(c0);
        if (ErrCode==1) {builder.setTitle("error")
                .setMessage("Cannot parse expression")
                .setCancelable(false)
                .setNegativeButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });}
        if (ErrCode==2) {builder.setTitle("error")
                .setMessage("error code=2")
                .setCancelable(false)
                .setNegativeButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });}
        if (ErrCode==3) {builder.setTitle("error")
                .setMessage("error in graph analysis")
                .setCancelable(false)
                .setNegativeButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });}
        if (ErrCode==4) {builder.setTitle("error")
                .setMessage("Wrong number format")
                .setCancelable(false)
                .setNegativeButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });}
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void ShowError(Context c0, int code, int exprID){
        if (code!=1) return;
        ErrCode=code;
        AlertDialog.Builder builder = new AlertDialog.Builder(c0);
        builder.setTitle("error")
                .setMessage("Cannot parse function "+Integer.toString(exprID+1))
                .setCancelable(false)
                .setNegativeButton("ok",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        });
        AlertDialog alert = builder.create();
        alert.show();
    }
}
