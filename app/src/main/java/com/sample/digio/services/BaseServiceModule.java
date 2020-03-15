package com.sample.digio.services;

import androidx.lifecycle.ViewModelProvider;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import com.sample.digio.AppConfig;
import com.sample.digio.viewModel.Repository;
import com.sample.digio.viewModel.ApiCallinterface;
import com.sample.digio.viewModel.ViewModelFactory;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by AKASH on 14/3/20.
 */
@Module
public class BaseServiceModule {

    @Provides
    @Singleton
    Gson provideGson(){
        GsonBuilder builder = new GsonBuilder().setFieldNamingPolicy(FieldNamingPolicy.IDENTITY);
        return builder.setLenient().create();
    }

    @Provides
    @Singleton
    Retrofit provideRetrofit(Gson gson, OkHttpClient okHttpClient){
        return new Retrofit.Builder()
                .baseUrl(AppConfig.BASE_URL_COMMON)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
    }

    @Provides
    @Singleton
    ApiCallinterface getApiCallInterface(Retrofit retrofit){
        return retrofit.create(ApiCallinterface.class);
    }

    @Provides
    @Singleton
    OkHttpClient getRequestHeader(){
        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient.Builder httpClient = new OkHttpClient.Builder();

        httpClient.addInterceptor(chain -> {
            Request original = chain.request();
            Request request = original.newBuilder().build();
            return chain.proceed(request);
        });
//        if (BuildConfig.DEBUG){
            httpClient.interceptors().add(logging);
//        }
        return httpClient.build();
    }

    @Provides
    @Singleton
    Repository getRepository(ApiCallinterface apiCallinterface){
        return new Repository(apiCallinterface);
    }

    @Provides
    @Singleton
    ViewModelProvider.Factory getViewModelFactory(Repository mRepository){
        return new ViewModelFactory(mRepository);
    }
}
