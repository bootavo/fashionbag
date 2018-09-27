package com.tarjetic.store.utilities;

import android.app.ProgressDialog;
import android.content.Context;

import com.tarjetic.store.R;

/**
 * Created by gtufinof on 15/01/18.
 */

public class ProgressDialogHelper {

    private ProgressDialog mProgressDialog;
    private Context ctx;
    private String message;

    public ProgressDialogHelper(Context ctx) {
        mProgressDialog = new ProgressDialog(ctx);
        this.ctx = ctx;
        this.message = message;
    }

    public ProgressDialogHelper(Context ctx, String message) {
        mProgressDialog = new ProgressDialog(ctx);
        this.ctx = ctx;
        this.message = message;
    }

    public void showPD(){
        mProgressDialog.setMessage(ctx.getResources().getString(R.string.pd_message));
        mProgressDialog.setCancelable(false);
        mProgressDialog.setIndeterminate(true);
        mProgressDialog.show();
    }

    public void dimissPD(){
        if(mProgressDialog.isShowing()){
            mProgressDialog.dismiss();
        }
    }

}
