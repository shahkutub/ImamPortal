package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class AllLocationResponse {

    List<AllLocationInfo> result = new ArrayList<>();

    public List<AllLocationInfo> getResult() {
        return result;
    }

    public void setResult(List<AllLocationInfo> result) {
        this.result = result;
    }
}
