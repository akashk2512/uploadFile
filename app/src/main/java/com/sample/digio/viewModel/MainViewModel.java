package com.sample.digio.viewModel;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sample.digio.model.UploadRequest;

import java.util.Map;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by AKASH on 14/3/20.
 */
public class MainViewModel extends ViewModel {
    private Repository repository;
    private final CompositeDisposable disposables = new CompositeDisposable();
    private final MutableLiveData<ApiResponse> responseLiveData = new MutableLiveData<>();

    public MainViewModel(Repository repository) {
        this.repository = repository;
    }


    public MutableLiveData<ApiResponse> getAPIResponse() {
        return responseLiveData;
    }

    /**
     *  method to call upload API
     */

    public void uploadFile(MultipartBody.Part body, String header, UploadRequest request){
        disposables.add(repository.uploadFile(body,header,request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe(disposable -> responseLiveData.setValue(ApiResponse.loading()))
                .subscribe(
                        result -> responseLiveData.setValue(ApiResponse.success(result)),
                        error -> responseLiveData.setValue(ApiResponse.error(error))
                )
        );

    }

}
