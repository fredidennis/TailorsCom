package com.tailorscom.fred.tailorscom.models;


import com.tailorscom.fred.tailorscom.galleryFeed_package.Like;
import com.tailorscom.fred.tailorscom.tailorGallery_package.Delete;

public class ServerRequest {

    private String operation;
    private User user;
    private Like like;
    private Like dislike;
    private Delete delete;

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public void setLike(Like like) { this.like = like; }

    public void setDislike(Like dislike) {this.dislike = dislike; }

    public void setDelete(Delete delete) {this.delete = delete;}

    public void setUser(User user) {
        this.user = user;
    }

}
