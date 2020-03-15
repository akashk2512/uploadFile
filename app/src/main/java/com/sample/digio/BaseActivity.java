package com.sample.digio;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.sample.digio.customviews.CustomProgressDialog;

/**
 * Created by AKASH on 14/3/20.
 */
public class BaseActivity extends AppCompatActivity {
    public CustomProgressDialog mCustomProgressDialog = null;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mCustomProgressDialog = new CustomProgressDialog(this , false);

    }
}
