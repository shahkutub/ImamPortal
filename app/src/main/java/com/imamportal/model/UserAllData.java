package com.imamportal.model;

public class UserAllData {

    //    "id": 6,
//            "user_id": 16,
//            "division_id": null,
//            "district_id": null,
//            "upazila_id": null,
//            "union_id": null,
//            "village": null,
//            "word_no": "•••••",
//            "citycorporation_id": null,
//            "name": "Reaz Uddin",
//            "mobile_no": "01556984227",
//            "nid": "564654654655",
//            "dob": "1989-03-13",
//            "masjid_name": "চৈতা বাজার জামে মসজিদ",
//            "masjid_address": null,
//            "image": "20190130_1548835996.png",
//            "deleted_at": null,
//            "created_at": "2018-07-16 13:08:10",
//            "updated_at": "2019-01-30 14:13:16"

    String name,mobile_no,image,id,username,email,user_type,role;

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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUser_type() {
        return user_type;
    }

    public void setUser_type(String user_type) {
        this.user_type = user_type;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
