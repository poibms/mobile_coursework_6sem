package com.example.project.models;

public class Tags {
    private Integer id;
    private String text;

    public Tags(Integer id, String text) {
        this.id = id;
        this.text = text;
    }

    public Integer getId() { return id; }
    public String getText() { return text; }

    public void setId(Integer id) { this.id = id; }
    public void setText(String text) { this.text = text; }
}
