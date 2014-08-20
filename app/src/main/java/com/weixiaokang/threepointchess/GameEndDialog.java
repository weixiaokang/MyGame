package com.weixiaokang.threepointchess;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;


public class GameEndDialog extends AlertDialog {

    private AlertDialog alertDialog;
    public GameEndDialog(Context context, int tabStringId) {
        super(context);
        Builder builder = new Builder(context);
        builder.setTitle(R.string.hint);
        builder.setMessage(tabStringId);
        builder.setNeutralButton(R.string.sure, new OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        alertDialog = builder.create();
    }

    public void showDialog() {
        alertDialog.show();
    }
}
