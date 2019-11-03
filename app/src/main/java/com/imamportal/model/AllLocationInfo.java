package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class AllLocationInfo {


    String table_name;
    List<LocationInfo> values = new ArrayList<>();

    public String getTable_name() {
        return table_name;
    }

    public void setTable_name(String table_name) {
        this.table_name = table_name;
    }

    public List<LocationInfo> getValues() {
        return values;
    }

    public void setValues(List<LocationInfo> values) {
        this.values = values;
    }
}
