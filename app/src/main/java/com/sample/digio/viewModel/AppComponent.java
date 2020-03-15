package com.sample.digio.viewModel;

import com.sample.digio.MainActivity;
import com.sample.digio.services.BaseServiceModule;

import javax.inject.Singleton;

import dagger.Component;

/**
 * Created by AKASH on 14/3/20.
 */

@Component(modules = {AppModule.class, BaseServiceModule.class})
@Singleton
public interface AppComponent {
    /** inject view methods **/
    void doInject(MainActivity activity);
}
