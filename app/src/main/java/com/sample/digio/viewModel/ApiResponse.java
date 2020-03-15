package com.sample.digio.viewModel;

import io.reactivex.annotations.NonNull;
import io.reactivex.annotations.Nullable;

import static com.sample.digio.viewModel.Status.ERROR;
import static com.sample.digio.viewModel.Status.FAILURE;
import static com.sample.digio.viewModel.Status.LOADING;
import static com.sample.digio.viewModel.Status.SUCCESS;

/**
 * Created by AKASH on 14/3/20.
 */
public class ApiResponse {

    public final Status status;

    @Nullable
    public final Object data;

    @Nullable
    public final Throwable error;


    public ApiResponse(Status status, Object data, Throwable error) {
        this.status = status;
        this.data = data;
        this.error = error;

    }

    public static ApiResponse loading() {
        return new ApiResponse(LOADING, null, null);
    }

    public static ApiResponse success(@NonNull Object data) {

//        if (errorCode==200){
            return new ApiResponse(SUCCESS,data,null);
//        }else {
//            return new ApiResponse(ERROR,data,null,message,errorCode);
//        }
    }


    public static ApiResponse error(@NonNull Throwable error){
        return new ApiResponse(FAILURE,null,error);
    }

}
