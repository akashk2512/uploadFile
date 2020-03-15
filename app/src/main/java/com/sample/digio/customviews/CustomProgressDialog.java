package com.sample.digio.customviews;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.WindowManager;

import com.sample.digio.R;


/**
 * Custom progress dialog class.
 */
public class CustomProgressDialog extends Dialog {


    private CustomProgressDialog dialog = null;
    private ICustomProgressDialog mCallback = null;

    public interface ICustomProgressDialog{
       void onProgressDissmiss();
    }

    public CustomProgressDialog(Context context) {
        super(context);
        dialog = new CustomProgressDialog(context,R.style.CustomProgressStyle);
    }

    public CustomProgressDialog(Context context , boolean setTimeOut) {
        super(context);
        dialog = new CustomProgressDialog(context,R.style.CustomProgressStyle);
    }

    public CustomProgressDialog(Context context , boolean setTimeOut ,
                                ICustomProgressDialog callBack) {
        super(context);
        dialog = new CustomProgressDialog(context,R.style.CustomProgressStyle);
        this.mCallback = callBack;
    }

    public CustomProgressDialog(Context context, int theme) {
        super(context, theme);
    }


    public void onWindowFocusChanged(boolean hasFocus){

    }

    public CustomProgressDialog show(CharSequence message, boolean indeterminate, boolean cancelable,
                                     OnCancelListener cancelListener) {
        dialog.setTitle("");
        dialog.setContentView(R.layout.progress_hud);
        dialog.setCanceledOnTouchOutside(false);

        dialog.setCancelable(cancelable);
        if(cancelListener != null){
            dialog.setOnCancelListener(cancelListener);
        }
        dialog.getWindow().getAttributes().gravity= Gravity.CENTER;
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        lp.dimAmount=0.2f;
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        dialog.getWindow().setAttributes(lp);
        dialog.show();

        return dialog;
    }

    /**
     * Method to dismiss the dialog.
     */
    public void dissMissDialog(){
        if (dialog != null && dialog.isShowing()
                && dialog.getContext() != null) {
            try {
                dialog.dismiss();

                if(mCallback != null){
                    mCallback.onProgressDissmiss();
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
