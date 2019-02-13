package com.haolaike.hotlikescan.utils;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

public class TipDialogUtil {
    public static AlertDialog showConfirm(Activity activity, String title, String confirm) {
        AlertDialog.Builder builder = dialogBuilder(activity, title);
        builder.setPositiveButton(confirm, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
        return dialog;
    }

    private static AlertDialog.Builder dialogBuilder(Activity activity, String message) {
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setCancelable(false);
        builder.setMessage(message);
        return builder;
    }
}
