package com.edith.divisaviyafinalproject.Details;/*
Created by Akila Ishan on 2021-02-05.
*/

import android.app.Activity;
import android.app.AlertDialog;
import android.view.LayoutInflater;

import com.edith.divisaviyafinalproject.R;

public class LoadingDialog {
    Activity activity;
    AlertDialog alertDialog;

    public LoadingDialog(Activity activity) {
        this.activity = activity;
    }

    public void startLoading(){
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        LayoutInflater inflater = activity.getLayoutInflater();
        builder.setView(inflater.inflate(R.layout.loading_dialog,null));
        builder.setCancelable(false);

        alertDialog = builder.create();
        alertDialog.show();
    }

    public void dismissLoading(){
        alertDialog.dismiss();
    }
}
