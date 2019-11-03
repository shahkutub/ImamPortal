package com.imamportal.model;

public class BlogPostSearchDetails {


    String id,title,description,view_count;

    UserAllData user_detail = new UserAllData();

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public UserAllData getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserAllData user_detail) {
        this.user_detail = user_detail;
    }
}
