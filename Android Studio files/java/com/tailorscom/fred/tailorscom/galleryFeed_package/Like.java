package com.tailorscom.fred.tailorscom.galleryFeed_package;

/**
 * Created by Home on 3/20/2017.
 */

public class Like {

    private String image_id;
    private String like;
    private String dislike;

    public String getLike() {
        return like;
    }
    public String getDislike() { return dislike; }
    public String getImage_id() { return image_id; }

    public void setImage_id (String image_id) { this.image_id = image_id; }
    public void setLike (String like) { this.like = like; }
    public void setDislike (String dislike) { this.dislike = dislike; }
}
