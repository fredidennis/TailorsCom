package com.tailorscom.fred.tailorscom.models;


import com.tailorscom.fred.tailorscom.galleryFeed_package.Like;
import com.tailorscom.fred.tailorscom.tailorGallery_package.Delete;

public class ServerResponse {

    private String result;
    private String message;
    private User user;
    private User image_id;
    private Like like;
    private Like dislike;
    private Delete delete;

    public String getResult() {
        return result;
    }

    public String getMessage() {
        return message;
    }

    public User getUser() {
        return user;
    }

    public Like getLike() { return like; }

    public Like getDislike() { return dislike; }

    public Delete getDelete() { return delete; }
}
