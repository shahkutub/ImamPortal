package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class AlllDataInfo {


    String table_name;
    List<DataInfo> values = new ArrayList<>();

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public List<DataInfo> getValues() {
        return values;
    }

    public void setValues(List<DataInfo> values) {
        this.values = values;
    }
}
