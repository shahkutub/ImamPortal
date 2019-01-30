package com.imamportal.model;

public class UserDetails {

    //"name": "Delwar Hossain",
    //            "mobile_no": "01556984227",
    //            "nid": "564654654655",
    //            "dob": "1989-03-13",
    //            "masjid_name": null,
    //            "masjid_address": null,
    //            "image": "16072018_1531724890.png",

    String name,mobile_no,image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile_no() {
        return mobile_no;
    }

    public void setMobile_no(String mobile_no) {
        this.mobile_no = mobile_no;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
