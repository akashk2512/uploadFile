package com.sample.digio;

import android.app.Application;

import com.sample.digio.viewModel.AppComponent;
import com.sample.digio.viewModel.DaggerAppComponent;

/**
 * Created by AKASH on 14/3/20.
 */
public class DigioApplication extends Application {

    private DigioApplication mDigioApplication;
    AppComponent appComponent;

    @Override
    public void onCreate() {
        super.onCreate();

        appComponent = DaggerAppComponent.builder().build();
    }

    public AppComponent getAppComponent(){
        return appComponent;
    }
}
