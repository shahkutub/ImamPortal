package com.imamportal.model;

import java.util.ArrayList;
import java.util.List;

public class QuizeQuistionResponse {

    List <QuistionData> questions = new ArrayList<>();
    String quiz_time,right_answer,wrong_answer;

    public String getRight_answer() {
        return right_answer;
    }

    public void setRight_answer(String right_answer) {
        this.right_answer = right_answer;
    }

    public String getWrong_answer() {
        return wrong_answer;
    }

    public void setWrong_answer(String wrong_answer) {
        this.wrong_answer = wrong_answer;
    }

    public String getQuiz_time() {
        return quiz_time;
    }

    public void setQuiz_time(String quiz_time) {
        this.quiz_time = quiz_time;
    }

    public List<QuistionData> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuistionData> questions) {
        this.questions = questions;
    }
}
