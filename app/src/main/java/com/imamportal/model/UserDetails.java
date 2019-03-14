package com.imamportal.model;

public class UserDetails {

    //"name": "Delwar Hossain",
    //            "mobile_no": "01556984227",
    //            "nid": "564654654655",
    //            "dob": "1989-03-13",
    //            "masjid_name": null,
    //            "masjid_address": null,
    //            "image": "16072018_1531724890.png",

    //  "id": 16,
    //        "username": "reaz",
    //        "email": "reaz@gmail.com",
    //        "user_type": "imam",
    //        "role": 2,
    //        "status": 1,

    String name,mobile_no,image,id,username,email,user_type,role;

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
