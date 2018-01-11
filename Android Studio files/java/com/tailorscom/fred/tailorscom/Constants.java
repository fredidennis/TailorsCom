package com.tailorscom.fred.tailorscom;

//http://192.168.137.1/
//http://tailorscom.000webhostapp.com
public class Constants {

    public static final String BASE_URL = "http://fcdd4036.ngrok.io";
    public static final String REGISTER_OPERATION = "register";
    public static final String LOGIN_OPERATION = "login";
    public static final String CHANGE_PASSWORD_OPERATION = "chgPass";
    public static final String EDIT_PROFILE_OPERATION = "edtProf";
    public static final String UPLOAD_PROFILE_PIC = "uplProfPic";
    public static final String UPLOAD_GALLERY_PIC = "uplGalleryPic";
    public static final String UPLOAD_LIKES = "uplLikes";
    public static final String UPLOAD_DISLIKES = "uplDisLikes";
    public static final String UPLOAD_PROFILE_LIKES = "uplProfLikes";
    public static final String UPLOAD_PROFILE_DISLIKES = "uplProfDislikes";
    public static final String DELETE_TAILOR_GALLERY = "delGallery";


    public static final String SUCCESS = "success";
    public static final String FAILURE = "failure";
    public static final String IS_LOGGED_IN = "isLoggedIn";

    public static final String NAME = "name";
    public static final String EMAIL = "email";
    public static final String UNIQUE_ID = "unique_id";
    public static final String IMAGE_ID = "image_id";
    public static final String DELETE = "deleted";
    public static final String LIKES = "_like";
    public static final String DISLIKES = "_dislike";

    public static String SHAREDNAME;
    public String getSHAREDNAME() {
        return SHAREDNAME;
    }
    public void setSHAREDNAME(String sharedname) { this.SHAREDNAME = sharedname; }

    public static String SHAREDEMAIL;
    public String getSHAREDEMAIL() { return SHAREDEMAIL; }
    public void setSHAREDEMAIL(String sharedemail) { this.SHAREDEMAIL = sharedemail; }


    public static final String TAG = "TailorsCom";

}
