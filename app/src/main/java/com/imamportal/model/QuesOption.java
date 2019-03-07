package com.imamportal.model;

public class QuesOption {

//"id": 17,
//                    "user_id": 1,
//                    "quiz_question_id": 6,
//                    "option_title": "প্রথম অপশন",
//                    "right_answer": 1,
//                    "deleted_at": null,
//                    "created_at": "2018-08-15 11:53:57",
//                    "updated_at": "2018-08-15 11:53:57"

    String id,quiz_question_id,option_title,right_answer;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getQuiz_question_id() {
        return quiz_question_id;
    }

    public void setQuiz_question_id(String quiz_question_id) {
        this.quiz_question_id = quiz_question_id;
    }

    public String getOption_title() {
        return option_title;
    }

    public void setOption_title(String option_title) {
        this.option_title = option_title;
    }

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }
}
