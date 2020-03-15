package com.sample.digio.utils;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.method.ScrollingMovementMethod;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;


import com.sample.digio.R;
import com.sample.digio.customviews.CustomProgressDialog;

/**
 * Created by AKASH on 14/3/20.
 */
public class AppUtils {


    /**
     * Method to show the Toast message.
     *
     * @param message String which indicates the message to be displayed as
     *                Toast.
     */

    public static void showToast(Context context, String message) {
        if (context != null) {
            Toast toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
            toast.setGravity(Gravity.CENTER, 0, 0);
            toast.show();
        }

    }

    /**
     * Method to check if a device is connected to internet or not
     *
     * @param context
     * @return true - if the device connected to internet; false - otherwise
     */
    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager
                .getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    /**
     * Method to show the custom progress dialog.
     *
     * @param mProgressDialog
     * @param mMessage
     */
    public static void showCustomProgressDialog(CustomProgressDialog mProgressDialog, String mMessage) {
        try {
            mProgressDialog.show(mMessage, true, false, null);
            mProgressDialog.setCancelable(false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Method to dismiss custom progress dialog.
     */
    public static void dismissCustomProgress(CustomProgressDialog mProgressDialog) {
        if (mProgressDialog != null && mProgressDialog.getContext() != null) {
            mProgressDialog.dissMissDialog();
        }
    }



    public static Dialog dialog_ok_cancel_dialog;
    public static Dialog showCustomOkCancelDialogWalletConfirm(Context context, String title, String message,
                                                               String positiveBtnTxt,
                                                               String negativeBtnTxt,
                                                               final View.OnClickListener positivecallback,
                                                               final View.OnClickListener negativecallback) {

        dialog_ok_cancel_dialog = new Dialog(context, R.style.NewDialog_Booking_Confirm);
        dialog_ok_cancel_dialog.setContentView(R.layout.custom_ok_cancel_layout_wallet_confirm);
        dialog_ok_cancel_dialog.setCancelable(false);
        dialog_ok_cancel_dialog.setCanceledOnTouchOutside(false);

        TextView txtTitle = (TextView) dialog_ok_cancel_dialog.findViewById(R.id.txt_custompopup_title);
        TextView txtMessage = (TextView) dialog_ok_cancel_dialog.findViewById(R.id.txt_custompopup_message);
        txtMessage.setMovementMethod(new ScrollingMovementMethod());
        TextView txtPositive = (TextView) dialog_ok_cancel_dialog.findViewById(R.id.txt_custompopup_ok);
        TextView txtNegativePositive = (TextView) dialog_ok_cancel_dialog.findViewById(R.id.txt_custompopup_cancel);

        txtTitle.setText(title);
        txtMessage.setText(message);

        txtPositive.setText(positiveBtnTxt);
        txtNegativePositive.setText(negativeBtnTxt);


        txtPositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ok_cancel_dialog.dismiss();
                if (positivecallback != null) {
                    positivecallback.onClick(v);
                }
            }
        });

        txtNegativePositive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog_ok_cancel_dialog.dismiss();
                if (negativecallback != null) {
                    negativecallback.onClick(v);
                }
            }
        });

        dialog_ok_cancel_dialog.show();

        return null;
    }

}
