package com.nvlad.mathapp;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;

/**
 * Created by Vlad on 25.12.2015.
 */
public class Error {
    private int ErrCode;

    public void SetError(int code){
        ErrCode=code;
    }

    public boolean isError(){
        if (ErrCode==0) return false;
        else return true;
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
//        if (ErrCode==1){builder.setMessage("Cannot parse expression");}
//        if (ErrCode==2){builder.setMessage("error code=2");}
        AlertDialog alert = builder.create();
        alert.show();
    }
    public void ShowError(Context c0, int code, int code2){
        ErrCode=code;
        Integer c2=code2+1;
        AlertDialog.Builder builder = new AlertDialog.Builder(c0);
        if (ErrCode==1) {builder.setTitle("error")
                .setMessage("Cannot parse expression "+c2.toString())
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
//        if (ErrCode==1){builder.setMessage("Cannot parse expression");}
//        if (ErrCode==2){builder.setMessage("error code=2");}
        AlertDialog alert = builder.create();
        alert.show();
    }
}
