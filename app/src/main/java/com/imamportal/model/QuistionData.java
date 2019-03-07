package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class QuistionData {

    //"id": 6,
    //            "user_id": 1,
    //            "title": "পরীক্ষামুলক প্রশ্ন দুই",
    //            "type": null,
    //            "help_text": null,
    //            "status": 1,
    //            "deleted_at": null,
    //            "created_at": "2018-08-15 11:53:57",
    //            "updated_at": "2018-08-19 10:28:45",
    //            "quiz_question_option": [

    String id,user_id,title;
    List<QuesOption> quiz_question_option =new ArrayList<>();

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<QuesOption> getQuiz_question_option() {
        return quiz_question_option;
    }

    public void setQuiz_question_option(List<QuesOption> quiz_question_option) {
        this.quiz_question_option = quiz_question_option;
    }
}
