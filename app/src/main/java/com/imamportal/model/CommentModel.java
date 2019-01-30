package com.imamportal.model;

public class CommentModel {

// "id": 25,
//                "user_id": 1,
//                "blog_post_id": 6,
//                "comment": "afsdf",
//                "deleted_at": null,
//                "created_at": "2019-01-06 10:37:33",
//                "updated_at": "2019-01-06 10:37:33"

    String id,user_id,blog_post_id,comment,created_at;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

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

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }
}
