package com.imamportal.model;

public class SeraContentData {

    SeraContentDataDetails user_details = new SeraContentDataDetails();

    public SeraContentDataDetails getUser_details() {
        return user_details;
    }

    public void setUser_details(SeraContentDataDetails user_details) {
        this.user_details = user_details;
    }

    public class SeraContentDataDetails {

//        "name": "Delwar Hossain",
//                "mobile_no": "01556984227",
//                "nid": "564654654655",
//                "dob": "1989-03-13",
//                "masjid_name": null,
//                "masjid_address": null,
//                "image": "16072018_1531724890.png",

        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
