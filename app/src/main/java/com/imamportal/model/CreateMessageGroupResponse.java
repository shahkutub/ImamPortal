package com.imamportal.model;

public class CreateMessageGroupResponse {

//    status": "success",
//    "group_id": 4,
//    "group_name": "Test Group From API New"

    String status,group_id,group_name;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getGroup_id() {
        return group_id;
    }

    public void setGroup_id(String group_id) {
        this.group_id = group_id;
    }

    public String getGroup_name() {
        return group_name;
    }

    public void setGroup_name(String group_name) {
        this.group_name = group_name;
    }
}
