package com.example.soapboxandroidstudioproject.model;

import java.util.List;

public class ResponseObject {
    private String user_id;
    private List<Result> results;
    private String language_code;
    private String result_id;
    private String time;


    public ResponseObject() {
    }

    public ResponseObject(String user_id, List<Result> results, String language_code, String result_id, String time) {
        this.user_id = user_id;
        this.results = results;
        this.language_code = language_code;
        this.result_id = result_id;
        this.time = time;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public String getLanguage_code() {
        return language_code;
    }

    public void setLanguage_code(String language_code) {
        this.language_code = language_code;
    }

    public String getResult_id() {
        return result_id;
    }

    public void setResult_id(String result_id) {
        this.result_id = result_id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "ResponseObject{" +
                "user_id='" + user_id + '\'' +
                ", results=" + results +
                ", language_code='" + language_code + '\'' +
                ", result_id='" + result_id + '\'' +
                ", time='" + time + '\'' +
                '}';
    }
}
