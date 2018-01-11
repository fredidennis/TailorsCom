package com.tailorscom.fred.tailorscom.findTailor_package;

/**
 * Created by Home on 3/21/2017.
 */

public class FindFeedListItem {
    private String name;
    private String email;
    private String desc;
    private String gender;
    private String speciality;
    private String location;
    private String contact;
    private String imageUrl;
    private String created_at;
    private String starting_rate;
    private String profile_id;
    private String likes;
    private String dislikes;

    public FindFeedListItem(String name, String email, String desc,
                            String gender, String speciality, String location,
                            String contact, String imageUrl, String created_at,
                            String starting_rate, String profile_id, String likes,
                            String dislikes) {
        this.name = name;
        this.email = email;
        this.desc = desc;
        this.gender = gender;
        this.speciality = speciality;
        this.location = location;
        this.contact = contact;
        this.imageUrl = imageUrl;
        this.created_at = created_at;
        this.starting_rate = starting_rate;
        this.profile_id = profile_id;
        this.likes = likes;
        this.dislikes = dislikes;
    }

    public String getName() { return name; }

    public String getEmail() { return email; }

    public String getDesc() { return desc; }

    public String getGender() { return gender; }

    public String getSpeciality() {
        return speciality;
    }

    public String getLocation() { return location; }

    public String getContact() { return contact; }

    public String getImageUrl(){
        return imageUrl;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getStarting_rate() { return starting_rate; }

    public String getProfile_id() { return  profile_id; }

    public String getLikes() { return likes; }

    public String getDislikes() { return  dislikes; }

}
