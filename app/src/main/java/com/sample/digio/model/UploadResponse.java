package com.sample.digio.model;

/**
 * Created by AKASH on 14/3/20.
 */
public class UploadResponse {
    private String id;
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
