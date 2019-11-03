package com.imamportal.model;

public class LocationInfo {

//    "id": 1,
//                    "division_name_eng": "Barisal",
//                    "division_name_bng": "বরিশাল",

//    "geo_division_id": 5,
//            "geo_district_id": 45,
//            "geo_upazila_id": 329,

    String id,division_name_bng,city_corporation_name_bng,district_name_bng,upazila_name_bng,union_name_bng,geo_division_id,geo_district_id,geo_upazila_id,thana_name_bng;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDivision_name_bng() {
        return division_name_bng;
    }

    public void setDivision_name_bng(String division_name_bng) {
        this.division_name_bng = division_name_bng;
    }

    public String getDistrict_name_bng() {
        return district_name_bng;
    }

    public void setDistrict_name_bng(String district_name_bng) {
        this.district_name_bng = district_name_bng;
    }

    public String getUpazila_name_bng() {
        return upazila_name_bng;
    }

    public void setUpazila_name_bng(String upazila_name_bng) {
        this.upazila_name_bng = upazila_name_bng;
    }

    public String getUnion_name_bng() {
        return union_name_bng;
    }

    public void setUnion_name_bng(String union_name_bng) {
        this.union_name_bng = union_name_bng;
    }

    public String getGeo_division_id() {
        return geo_division_id;
    }

    public void setGeo_division_id(String geo_division_id) {
        this.geo_division_id = geo_division_id;
    }

    public String getGeo_district_id() {
        return geo_district_id;
    }

    public void setGeo_district_id(String geo_district_id) {
        this.geo_district_id = geo_district_id;
    }

    public String getThana_name_bng() {
        return thana_name_bng;
    }

    public void setThana_name_bng(String thana_name_bng) {
        this.thana_name_bng = thana_name_bng;
    }

    public String getGeo_upazila_id() {
        return geo_upazila_id;
    }

    public void setGeo_upazila_id(String geo_upazila_id) {
        this.geo_upazila_id = geo_upazila_id;
    }

    public String getCity_corporation_name_bng() {
        return city_corporation_name_bng;
    }

    public void setCity_corporation_name_bng(String city_corporation_name_bng) {
        this.city_corporation_name_bng = city_corporation_name_bng;
    }
}
