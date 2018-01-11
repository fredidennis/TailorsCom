package com.tailorscom.fred.tailorscom.tailorGallery_package;

/**
 * Created by Home on 3/27/2017.
 */

public class TailorsGalleryListItem {


    private String desc;
    private String imageUrl;
    private String created_at;
    private String profile_id;
    private String likes;
    private String dislikes;
    private String starting_rate;

    public TailorsGalleryListItem(String desc, String imageUrl,
                                  String created_at, String profile_id,
                                  String likes, String dislikes,
                                  String starting_rate) {
        this.desc = desc;
        this.imageUrl = imageUrl;
        this.created_at = created_at;
        this.profile_id = profile_id;
        this.likes = likes;
        this.dislikes = dislikes;
        this.starting_rate = starting_rate;
    }

    public String getDesc() { return desc; }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getProfile_id() { return  profile_id; }

    public String getLikes() { return likes; }

    public String getDislikes() { return  dislikes; }

    public String getStarting_rate() {return starting_rate; }
}
