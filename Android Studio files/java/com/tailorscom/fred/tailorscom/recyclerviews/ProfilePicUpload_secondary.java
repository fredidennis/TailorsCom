package com.tailorscom.fred.tailorscom.recyclerviews;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.kosalgeek.android.photoutil.CameraPhoto;
import com.kosalgeek.android.photoutil.GalleryPhoto;
import com.kosalgeek.android.photoutil.ImageBase64;
import com.kosalgeek.android.photoutil.ImageLoader;
import com.tailorscom.fred.tailorscom.Constants;
import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.RequestInterface;
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ProfilePicUpload_secondary extends AppCompatActivity {

    private ProgressBar progress;
    private final String TAG = this.getClass().getName();
    ImageView camera, gallery, upload, uImage;

    CameraPhoto cameraPhoto;
    GalleryPhoto galleryPhoto;

    final int CAMERA_REQUEST = 13323;
    final int GALLERY_REQUEST = 12345;

    String selectedPhoto;

    EditText userName;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_pic_upload_secondary);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        cameraPhoto = new CameraPhoto(getApplicationContext());
        galleryPhoto = new GalleryPhoto(getApplicationContext());

        progress = (ProgressBar) findViewById(R.id.progress);

        userName = (EditText) findViewById(R.id.etuserName);

        uImage = (ImageView) findViewById(R.id.u_image);
        camera = (ImageView) findViewById(R.id.g_camera);
        gallery = (ImageView) findViewById(R.id.g_gallery);
        upload = (ImageView) findViewById(R.id.g_updload);

        try{
            Bundle bundle = null;
            String uName = bundle.getString("NAME");
            if(uName != null){
                userName.setText(""+uName+"");
            }
        }
        catch (NullPointerException e){
            Toast.makeText(getApplicationContext(),
                    "Something went while getting your name please enter it bellow", Toast.LENGTH_LONG).show();
        }





        camera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try {
                    startActivityForResult(cameraPhoto.takePhotoIntent(), CAMERA_REQUEST);
                    cameraPhoto.addToGallery();
                } catch (IOException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Went Wrong while taking photo", Toast.LENGTH_SHORT).show();
                }

            }
        });

        uImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        gallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Bitmap image = ((BitmapDrawable) uImage.getDrawable()).getBitmap();
                //new MyUpload(image, uploadName.getText().toString()).execute();
                startActivityForResult(galleryPhoto.openGalleryIntent(), GALLERY_REQUEST);
            }
        });

        upload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(selectedPhoto == null || selectedPhoto.equals("")){
                    Toast.makeText(getApplicationContext(),
                            "No image selected", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024, 1024).getBitmap();
                    String encodedImage = ImageBase64.encode(bitmap);
                    Log.d(TAG, encodedImage);

                    HashMap<String, String> postData = new HashMap<String, String>();
                    postData.put("image", encodedImage);

                    String name = userName.getText().toString();
                    String encoded = encodedImage.toString();

                    if(!name.isEmpty() && !encodedImage.isEmpty()) {

                        progress.setVisibility(View.VISIBLE);
                        uploadProcess(name, encoded);

                    } else {

                        Toast.makeText(getApplicationContext(),
                                "User name field if empty", Toast.LENGTH_SHORT).show();
                    }




                }
                 catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Went Wrong while encoding photo", Toast.LENGTH_SHORT).show();
                }

                /*try {
                    Bitmap bitmap = ImageLoader.init().from(selectedPhoto).requestSize(1024, 1024).getBitmap();
                    String encodedImage = ImageBase64.encode(bitmap);
                    Log.d(TAG, encodedImage);

                    HashMap<String, String> postData = new HashMap<String, String>();
                    postData.put("image", encodedImage);

                    PostResponseAsyncTask task = new PostResponseAsyncTask(ProfilePicUpload_secondary.this, postData, new AsyncResponse() {
                        @Override
                        public void processFinish(String s) {
                            if(s.contains("uploaded_success")){
                                Toast.makeText(getApplicationContext(),
                                        "Image uploaded successfully", Toast.LENGTH_SHORT).show();
                            }
                            else if(s.contains("uploaded_failed")){
                                Toast.makeText(getApplicationContext(),
                                        "Image not uploaded coz of something", Toast.LENGTH_SHORT).show();
                            }
                            else{
                                Toast.makeText(getApplicationContext(),
                                        "Image upload failed", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

                    String ip = Constants.BASE_URL;
                    task.execute(""+ip+"/TailorsCom-login-register/upload.php");
                    task.setEachExceptionsHandler(new EachExceptionsHandler() {
                        @Override
                        public void handleIOException(IOException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Can not connect to server", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleMalformedURLException(MalformedURLException e) {
                            Toast.makeText(getApplicationContext(),
                                    "URL error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleProtocolException(ProtocolException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Protocol error", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleUnsupportedEncodingException(UnsupportedEncodingException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Encoding error", Toast.LENGTH_SHORT).show();
                        }
                    });


                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Went Wrong while encoding photo", Toast.LENGTH_SHORT).show();
                }*/
            }
        });
    }

    public void uploadProcess(String name, String encoded){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setuploaderName(name);
        user.setEncoded(encoded);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPLOAD_PROFILE_PIC);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                ServerResponse resp = response.body();
                Toast.makeText(getApplicationContext(),
                        resp.getMessage(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();

                progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Toast.makeText(getApplicationContext(),
                        t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();

            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == CAMERA_REQUEST) {
                String photoPath = cameraPhoto.getPhotoPath();
                selectedPhoto = photoPath;
                Bitmap bitmap = null;
                try {
                    bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    uImage.setImageBitmap(getRotatedBitmap(bitmap, 90));
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Went Wrong while loading photo", Toast.LENGTH_SHORT).show();
                }
            }
            else if (requestCode == GALLERY_REQUEST) {
                Uri uri = data.getData();
                galleryPhoto.setPhotoUri(uri);
                String photoPath = galleryPhoto.getPath();
                selectedPhoto = photoPath;
                try {
                    Bitmap bitmap = ImageLoader.init().from(photoPath).requestSize(512, 512).getBitmap();
                    uImage.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(getApplicationContext(),
                            "Something Went Wrong while choosing photo", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }
    private Bitmap getRotatedBitmap(Bitmap source, float angle){
        Matrix matrix = new Matrix();
        matrix.postRotate(angle);
        Bitmap bitmap1 = Bitmap.createBitmap(source,0,0, source.getWidth(), source.getHeight(), matrix, true);
        return bitmap1;
    }

    @Override
    public void onBackPressed()
    {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }


}

