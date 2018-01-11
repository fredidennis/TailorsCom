package com.tailorscom.fred.tailorscom.galleryFeed_package;

/**
 * Created by Home on 3/16/2017.
 */

public class ListItem {
    private String likes;
    private String dislikes;
    private String head;
    private String desc;
    private String imageUrl;
    private String cost;
    private String email;
    private String image_id;

    public ListItem(String likes, String dislikes, String head, String desc, String imageUrl, String cost, String email, String image_id) {
        this.likes = likes;
        this.dislikes = dislikes;
        this.head = head;
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.cost = cost;
        this.email = email;
        this.image_id = image_id;
    }

    public String getLikes() { return likes; }

    public String getDislikes() { return dislikes; }

    public String getHead() {
        return head;
    }

    public String getDesc() {
        return desc;
    }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCost() { return cost; }

    public String getEmail() { return email; }

    public String getImageId() { return image_id; }
}
