package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class AmarpataContentResponse {

    Contents contents = new Contents ();
    Contents audios = new Contents ();
    Contents videos = new Contents ();
    Contents photo_galleries = new Contents ();
    List<Catagories> content_categories = new ArrayList<>();
    List<Catagories> multimedia_categories = new ArrayList<>();

    public Contents getPhoto_galleries() {
        return photo_galleries;
    }

    public void setPhoto_galleries(Contents photo_galleries) {
        this.photo_galleries = photo_galleries;
    }

    public Contents getVideos() {
        return videos;
    }

    public void setVideos(Contents videos) {
        this.videos = videos;
    }

    public Contents getAudios() {
        return audios;
    }

    public void setAudios(Contents audios) {
        this.audios = audios;
    }

    public List<Catagories> getMultimedia_categories() {
        return multimedia_categories;
    }

    public void setMultimedia_categories(List<Catagories> multimedia_categories) {
        this.multimedia_categories = multimedia_categories;
    }

    public List<Catagories> getContent_categories() {
        return content_categories;
    }

    public void setContent_categories(List<Catagories> content_categories) {
        this.content_categories = content_categories;
    }

    public Contents getContents() {
        return contents;
    }

    public void setContents(Contents contents) {
        this.contents = contents;
    }
}
