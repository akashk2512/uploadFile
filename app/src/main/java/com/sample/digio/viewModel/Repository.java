package com.sample.digio.viewModel;

import com.sample.digio.model.UploadRequest;
import com.sample.digio.model.UploadResponse;
import com.sample.digio.viewModel.ApiCallinterface;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Created by AKASH on 14/3/20.
 */
public class Repository {
    private ApiCallinterface apiCallinterface;

    public Repository(ApiCallinterface apiCallinterface) {
        this.apiCallinterface = apiCallinterface;
    }

    /**
     *  method to call upload API
     */

    Observable<UploadResponse> uploadFile(MultipartBody.Part body, String header, UploadRequest request){
        return apiCallinterface.uploadFile(body,header, request);
    }
}
