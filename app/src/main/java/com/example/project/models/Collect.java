package com.example.project.models;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Collect implements Serializable {
    private Integer id;
    private String title;
    private String description;
    private Integer owner_id;
    private ArrayList<Root> tags;
    private String image;

    public Collect(Integer id, String title, String description, Integer owner_id, ArrayList<Root> tags, String image) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.owner_id = owner_id;
        this.tags = tags;
        this.image = image;
    }

    public Integer getId() { return id; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public Integer getOwnerId() { return owner_id; }
    public List<Root> getTags() { return tags; }
    public String getImage() { return image; }

    public void setId(Integer id) { this.id = id; }
    public void setTitle(String title) { this.title = title; }
    public void setDescription(String description) { this.description = description; }
    public void setOwnerId(Integer ownerId) { this.owner_id = ownerId; }
    public void setTags(ArrayList<Root> tags) { this.tags = tags; }
    public void setImage(String image) { this.image = image; }
}
