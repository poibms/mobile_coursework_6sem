package com.example.project.models;

public class Items {
    private Integer id;
    private String title;
    private String description;
    private String image;
    private Integer collection_id;

    public Items(Integer id, String title, String description, String image, Integer collection_id) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.image = image;
        this.collection_id = collection_id;
    }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImage() {
        return image;
    }

    public Integer getCollection_id() {
        return collection_id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public void setCollection_id(Integer collection_id) {
        this.collection_id = collection_id;
    }

}
