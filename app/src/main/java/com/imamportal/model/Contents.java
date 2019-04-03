package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class Contents {

   List<ContentData> data = new ArrayList<>();

    public List<ContentData> getData() {
        return data;
    }

    public void setData(List<ContentData> data) {
        this.data = data;
    }
}

