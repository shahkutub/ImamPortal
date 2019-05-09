package com.imamportal.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;
import io.realm.annotations.Required;

public class BookmarkContent extends RealmObject {

    public static final String PROPERTY_CONTENT = "content";
    public static final String PROPERTY_TITLE = "title";

    @Required
    public String content;
    public String title;
}
