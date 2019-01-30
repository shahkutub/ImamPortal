package com.imamportal.model;

public class VideoModel {
//"id": 2,
//        "user_id": 1,
//        "category_id": null,
//        "title": "this is test title",
//        "description": "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod",
//        "video": "fafb152056b14ca9a10be5f63514086c.mov",
//        "url_link": "asdfaf",
//        "homepage_view": 1,
//        "status": 1,
//        "view_count": 0,
//        "deleted_at": null,
//        "created_at": "2018-07-23 10:53:13",
//        "updated_at": "2019-01-08 12:03:55"

    String category_id,type,title,description,status,video,url_link,homepage_view,view_count,created_at;
    UserDetails user_detail = new UserDetails();

    public UserDetails getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetails user_detail) {
        this.user_detail = user_detail;
    }

    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getUrl_link() {
        return url_link;
    }

    public void setUrl_link(String url_link) {
        this.url_link = url_link;
    }

    public String getHomepage_view() {
        return homepage_view;
    }

    public void setHomepage_view(String homepage_view) {
        this.homepage_view = homepage_view;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }
}
