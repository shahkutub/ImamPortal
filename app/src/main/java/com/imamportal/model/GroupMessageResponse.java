package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class GroupMessageResponse {

    String current_page;

    List<ChatAppMsgDTO> data = new ArrayList<>();

    public String getCurrent_page() {
        return current_page;
    }

    public void setCurrent_page(String current_page) {
        this.current_page = current_page;
    }

    public List<ChatAppMsgDTO> getData() {
        return data;
    }

    public void setData(List<ChatAppMsgDTO> data) {
        this.data = data;
    }
}
