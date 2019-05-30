package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class ChatUserResponse {

    String current_page;
    List<ChatUserModel> data = new ArrayList<>();

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<ChatUserModel> getData() {
        return data;
    }

    public void setData(List<ChatUserModel> data) {
        this.data = data;
    }
}
