package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class DokkotarGolpoModel {


    String category_id,title,description,view_count,created_at;

    UserDetails user_detail = new UserDetails();

    List<LikePostModel> like_post = new ArrayList<>();
    List<LikePostModel> comment = new ArrayList<>();

    public String getCategory_id() {
        return category_id;
    }

    public void setCategory_id(String category_id) {
        this.category_id = category_id;
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

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public UserDetails getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetails user_detail) {
        this.user_detail = user_detail;
    }

    public List<LikePostModel> getLike_post() {
        return like_post;
    }

    public void setLike_post(List<LikePostModel> like_post) {
        this.like_post = like_post;
    }

    public List<LikePostModel> getComment() {
        return comment;
    }

    public void setComment(List<LikePostModel> comment) {
        this.comment = comment;
    }
}
