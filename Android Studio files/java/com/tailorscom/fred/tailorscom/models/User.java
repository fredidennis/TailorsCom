package com.tailorscom.fred.tailorscom.models;

public class User {

    private String name;
    private String email;
    private String oldname;
    private String unique_id;
    private String image_id;
    private String gender;
    private String speciality;
    private String location;
    private String contact;
    private String password;
    private String old_password;
    private String new_password;
    private String newname;
    private String newemail;
    private String newspeciality;
    private String newlocation;
    private String uploaderName;
    private String encoded;
    private String description;
    private String cost;
    private String passEmail;
    private String newcontact;
    private String newrate;
    private String newdesc;


    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getUnique_id() {
        return unique_id;
    }

    public String getImage_id() { return image_id; }

    public void setName(String name) { this.name = name; }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setGender(String gender) {this.gender = gender; }

    public void setSpeciality(String speciality) {this.speciality = speciality; }

    public void setLocation(String location) {this.location = location;}

    public void setContact(String contact) {this.contact = contact;}

    public void setPassword(String password) {
        this.password = password;
    }

    public void setOldName(String OldName) {
        this.oldname = OldName;
    }

    public void setNewName(String NewName) { this.newname = NewName; }

    public void setNewEmail(String NewNEmail) { this.newemail = NewNEmail; }

    public void setNewSpeciality (String NewSpeciality) {this.newspeciality = NewSpeciality; }

    public void setNewLocation (String NewLocation) { this.newlocation = NewLocation; }

    public void setOld_password(String old_password) {
        this.old_password = old_password;
    }

    public void setNew_password(String new_password) {
        this.new_password = new_password;
    }

    public void setuploaderName(String uploaderName) { this.uploaderName =  uploaderName; }

    public void setEncoded(String encoded) { this.encoded = encoded; }

    public void setDescription(String description) { this.description = description; }

    public void setCost(String cost) { this.cost = cost; }

    public void setPassEmail(String passEmail) { this.passEmail = passEmail; }

    public void setNewContact(String newContact) { this.newcontact = newContact; }

    public void setNewRate(String newRate) { this.newrate = newRate; }

    public void setNewDesc(String newDesc) { this.newdesc = newDesc; }

}