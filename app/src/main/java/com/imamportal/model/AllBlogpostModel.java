package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class AllBlogpostModel {

//    "category_id": 52,
//            "type": 4,
//            "title": "this is test title",
//            "description": "<p>asfasas</p>",
//            "status": 1,
//            "image": "08102018_1538974711.jpeg",
//            "file": "08102018_1538970972.pdf",

    String category_id,type,title,description,status,audio,view_count,created_at,image,file,question,answer,video,Url_link;

    UserDetails user_detail = new UserDetails();

    List<CommentModel> comment = new ArrayList<>();
    List<CommentModel> like_post = new ArrayList<>();

    public String getUrl_link() {
        return Url_link;
    }

    public void setUrl_link(String url_link) {
        Url_link = url_link;
    }


    public String getVideo() {
        return video;
    }

    public void setVideo(String video) {
        this.video = video;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getFile() {
        return file;
    }

    public void setFile(String file) {
        this.file = file;
    }

    public List<CommentModel> getLike_post() {
        return like_post;
    }

    public void setLike_post(List<CommentModel> like_post) {
        this.like_post = like_post;
    }

    public List<CommentModel> getComment() {
        return comment;
    }

    public void setComment(List<CommentModel> comment) {
        this.comment = comment;
    }

    public UserDetails getUser_detail() {
        return user_detail;
    }

    public void setUser_detail(UserDetails user_detail) {
        this.user_detail = user_detail;
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

    public String getAudio() {
        return audio;
    }

    public void setAudio(String audio) {
        this.audio = audio;
    }

    public String getView_count() {
        return view_count;
    }

    public void setView_count(String view_count) {
        this.view_count = view_count;
    }
}
