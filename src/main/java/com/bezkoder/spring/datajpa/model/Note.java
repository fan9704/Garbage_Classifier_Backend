package com.bezkoder.spring.datajpa.model;


import java.util.HashMap;
import java.util.Map;

public class Note {
    private String subject;
    private String content;
    private String image;
    private Map<String, String> data = new HashMap<>();

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setData(Map<String, String> data) {
        this.data = data;
    }

    public void setImage(String image) {
        this.image = image;
    }


    public String getSubject() {
        return subject;
    }

    public String getContent() {
        return content;
    }

    public Map<String, String> getData() {
        return data;
    }

    public String getImage() {
        return image;
    }


}