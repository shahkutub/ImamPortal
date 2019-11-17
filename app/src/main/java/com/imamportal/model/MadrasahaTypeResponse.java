package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class MadrasahaTypeResponse {

    List<MadrasaTypeInfo> madrasahtype = new ArrayList<>();

    public List<MadrasaTypeInfo> getMadrasahtype() {
        return madrasahtype;
    }

    public void setMadrasahtype(List<MadrasaTypeInfo> madrasahtype) {
        this.madrasahtype = madrasahtype;
    }
}
