package com.sample.digio;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProviders;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.sample.digio.customviews.CustomProgressDialog;
import com.sample.digio.model.Signers;
import com.sample.digio.model.UploadRequest;
import com.sample.digio.model.UploadResponse;
import com.sample.digio.utils.AppUtils;
import com.sample.digio.viewModel.ApiResponse;
import com.sample.digio.viewModel.MainViewModel;
import com.sample.digio.viewModel.ViewModelFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class MainActivity extends BaseActivity implements View.OnClickListener {

    @Inject
    ViewModelFactory viewModelFactory;
    MainViewModel mainViewModel;



    Context mContext;

    Button btn_upload;
    TextView txt_upload_id;
    private int REQ_CODE=102;
    private int PICK_PDF_CODE = 129;
    Uri selectedFilePath =null;

    String clientId = "AIZ67DUSNZ8TGWJV4DZ7DI3T5Z2LN2W2";
    String clientSecret = "ASN9AVKHU6HF41KTU71G3KNXLG1ET7BC";
    String credentials = clientId + ":" + clientSecret;
    // create Base64 encode string
    final String basic =
            "Basic " + Base64.encodeToString(credentials.getBytes(), Base64.NO_WRAP);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mContext = MainActivity.this;
        init();

        ((DigioApplication) getApplication()).getAppComponent().doInject(this);
        mainViewModel = ViewModelProviders.of(this,viewModelFactory).get(MainViewModel.class);
        mainViewModel.getAPIResponse().observe(this, this :: consumeResponse);
    }

    void init(){
        txt_upload_id = findViewById(R.id.txt_upload_id);
        btn_upload = findViewById(R.id.btn_upload);

        btn_upload.setOnClickListener(this);
    }

    private void consumeResponse(ApiResponse apiResponse) {
        switch (apiResponse.status){
            case LOADING:
                // show loader
                AppUtils.showCustomProgressDialog(mCustomProgressDialog, "Loading...");

                break;
            case SUCCESS:
                // hide loader
                AppUtils.dismissCustomProgress(mCustomProgressDialog);

                renderSuccessResponse(apiResponse.data);
                break;
            case FAILURE:
                // hide loader
                AppUtils.dismissCustomProgress(mCustomProgressDialog);
                AppUtils.showCustomOkCancelDialogWalletConfirm(mContext, "", apiResponse.error.getMessage(), "Retry", "Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        callUploadFile(selectedFilePath);
                    }
                },null);
                // failure
                AppUtils.showToast(mContext, getString(R.string.error_default));
                break;

                default:
                    break;
        }
    }


    private void renderSuccessResponse(Object response){
        if (response != null){
            if (response instanceof UploadResponse){
                UploadResponse uploadResponse = (UploadResponse) response;
                if (uploadResponse.getId()!=null){
                    txt_upload_id.setText("Uploaded successfully\nId: "+uploadResponse.getId());
                }else {
                    AppUtils.showToast(mContext,uploadResponse.getMessage());
                }
            }
        }

    }


    /**
     *  Method to uploadFile
     * @param fileUri
     */
    List<Signers> dataList = new ArrayList<>();

    /**
     *  Call upload file API
     * @param fileUri
     */
    private void callUploadFile(Uri fileUri){
        if (AppUtils.isNetworkAvailable(mContext)){
            dataList.clear();
            Signers request = new Signers();
            request.setIdentifier("akashk2512@gmail.com");
            dataList.add(request);
            UploadRequest uploadRequest = new UploadRequest();
            uploadRequest.setSigners(dataList);

            MultipartBody.Part body = prepareFilePart("file",fileUri);

            mainViewModel.uploadFile(body,basic,uploadRequest);

        }else {
            AppUtils.showToast(mContext, getResources().getString(R.string.error_network));

        }
    }

    public MultipartBody.Part prepareFilePart(String partName, Uri fileUri){
        File file = FileUtils.getFile(mContext,fileUri);
        RequestBody requestFile = RequestBody.create(MediaType.parse(Objects.requireNonNull(mContext.getContentResolver().getType(fileUri))),file);
        return MultipartBody.Part.createFormData(partName,file.getName(),requestFile);
    }

    public RequestBody createPartFromString (String partString) {
        return RequestBody.create(MultipartBody.FORM, partString);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_upload:
                marshmellowPermissionStorageGallery();
                break;
                default:
                    break;
        }
    }


    private void marshmellowPermissionStorageGallery() {
        if (ContextCompat.checkSelfPermission(this,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE},REQ_CODE
                    );
        } else {
            openChooser();
        }
    }

    // for PDF file call this method
    void openChooser() {
        Intent browseStorage = new Intent(Intent.ACTION_GET_CONTENT);
        browseStorage.setType("application/pdf");
        browseStorage.addCategory(Intent.CATEGORY_OPENABLE);
        startActivityForResult(Intent.createChooser(browseStorage, "Select PDF"), PICK_PDF_CODE);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode==REQ_CODE){
            if (grantResults.length>0 && grantResults[0]==PackageManager.PERMISSION_GRANTED){
                openChooser();
            }else {
//                AppUtils.showToast(mContext,"Please allow the permission");
                AppUtils.showCustomOkCancelDialogWalletConfirm(mContext, "", "Please allow the permission to use this feature", "Ok", "Cancel", new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        marshmellowPermissionStorageGallery();
                    }
                },null);


            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode==RESULT_OK){
            if (requestCode == PICK_PDF_CODE && data!=null){
                selectedFilePath = data.getData();
                Log.d("SelectedFile", ": " + selectedFilePath.toString());
                callUploadFile(selectedFilePath);
            }
        }else {
            AppUtils.showToast(mContext,"File not selected");
        }
    }



}
