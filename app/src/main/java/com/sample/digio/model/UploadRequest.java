package com.sample.digio.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by AKASH on 15/3/20.
 */
public class UploadRequest {
    private List<Signers> signers = new ArrayList();

    public List<Signers> getSigners() {
        return signers;
    }

    public void setSigners(List<Signers> signers) {
        this.signers = signers;
    }
}

