package com.imamportal.model;

public class AlquranAlhadits {

//    "category_id": 52,
//            "type": 4,
//            "title": "this is test title",
//            "description": "<p>asfasas</p>",
//            "status": 1,
//            "image": "08102018_1538974711.jpeg",
//            "file": "08102018_1538970972.pdf",

    String category_id,type,title,description,status,image,file,created_at;

   UserDetails user_detail = new UserDetails();

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
}
