package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class SignUpResponse {


//    {
//        "status": "success",
//            "data": {
//        "message": "Registration Successful"
//    }
//    }

    String token;
    UserDetails user_data = new UserDetails();




    public UserDetails getUser_data() {
        return user_data;
    }

    public void setUser_data(UserDetails user_data) {
        this.user_data = user_data;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    String status;

    DataMsg data = new DataMsg();


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public DataMsg getData() {
        return data;
    }

    public void setData(DataMsg data) {
        this.data = data;
    }

    public class DataMsg {
        String message;

        List<String> email = new ArrayList<>();
        List<String> password = new ArrayList<>();

        public List<String> getEmail() {
            return email;
        }

        public void setEmail(List<String> email) {
            this.email = email;
        }

        public List<String> getPassword() {
            return password;
        }

        public void setPassword(List<String> password) {
            this.password = password;
        }

        public String getMessage() {
            return message;
        }

        public void setMessage(String message) {
            this.message = message;
        }
    }
}
