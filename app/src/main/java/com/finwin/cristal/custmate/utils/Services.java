package com.finwin.cristal.custmate.utils;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

import com.finwin.cristal.custmate.R;

import cn.pedant.SweetAlert.SweetAlertDialog;

public class Services {

    public static SweetAlertDialog showProgressDialog(Context context) {


        SweetAlertDialog sweetAlertDialog = new SweetAlertDialog(context, SweetAlertDialog.PROGRESS_TYPE);
        sweetAlertDialog.setCancelable(false);
        sweetAlertDialog.setTitleText("Please wait")
                .show();

        return sweetAlertDialog;
    }

    public static Dialog showProgress(Context context) {

        Dialog warningDialog = new Dialog(context);
        warningDialog.setContentView(R.layout.layout_progress_dialog);


        warningDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        warningDialog.setCanceledOnTouchOutside(false);
        warningDialog.setCancelable(false);
        warningDialog.show();
        return warningDialog;
    }
}
