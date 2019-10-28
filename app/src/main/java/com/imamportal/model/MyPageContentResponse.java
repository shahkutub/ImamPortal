package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class MyPageContentResponse {
    List<Catagories> content_categories = new ArrayList<>();

    public List<Catagories> getContent_categories() {
        return content_categories;
    }

    public void setContent_categories(List<Catagories> content_categories) {
        this.content_categories = content_categories;
    }
}
