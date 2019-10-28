package com.imamportal.model;

public class CommentResponse {

//     "id": 30,
//    "user_id": 16,
//    "blog_post_id": 9,
//    "comment": "good",
    String user_id,blog_post_id,comment;

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getBlog_post_id() {
        return blog_post_id;
    }

    public void setBlog_post_id(String blog_post_id) {
        this.blog_post_id = blog_post_id;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }
}
