package com.sample.digio.viewModel;

import com.sample.digio.AppConstant;
import com.sample.digio.model.UploadRequest;
import com.sample.digio.model.UploadResponse;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.PartMap;

/**
 * Created by AKASH on 14/3/20.
 */
public interface ApiCallinterface {

    @Multipart
    @POST(AppConstant.POST_METHOD_UPLOAD)
    Observable<UploadResponse> uploadFile(@Part MultipartBody.Part file,
                                          @Header("authorization") String auth,
                                          @Part("request") UploadRequest request );

    @POST(AppConstant.POST_METHOD_UPLOAD_PDF)
    Observable<UploadResponse> uploadPdf();
}
