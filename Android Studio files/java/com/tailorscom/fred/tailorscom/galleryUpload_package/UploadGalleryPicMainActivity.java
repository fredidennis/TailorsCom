package com.tailorscom.fred.tailorscom.galleryUpload_package;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tailorscom.fred.tailorscom.Constants;
import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.RequestInterface;
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;
import com.tailorscom.fred.tailorscom.profilePicUpload_package.UploadProfilePicMainActivity;
import com.tailorscom.fred.tailorscom.tailorGallery_package.TailorGalleryActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadGalleryPicMainActivity extends AppCompatActivity implements View.OnClickListener{

    String selectedPhoto;
    String trueusername, trueuseremail;

    private int PICK_IMAGE_REQUEST = 1;

    private Button buttonChoose;
    private Button buttonUpload;
    private Button buttonMyGallery;


    private ImageView imageView;

    private TextView tv_uploader_name;

    public EditText userDescription, designCost;

    private Bitmap bitmap;

    private Uri filePath;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_gallery_pic_main);
        setTitle("TailorsCom : Upload Gallery");

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);
        buttonMyGallery = (Button) findViewById(R.id.buttonViewGallery);

        designCost = (EditText) findViewById(R.id.design_cost);
        userDescription = (EditText) findViewById(R.id.user_description);

        tv_uploader_name = (TextView) findViewById(R.id.tv_uploader_name);

        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);
        buttonMyGallery.setOnClickListener(this);

        Constants sharedNameEmail = new Constants();
        trueuseremail = sharedNameEmail.getSHAREDEMAIL();

        Constants sharedNameGet = new Constants();
        trueusername = sharedNameGet.getSHAREDNAME();
        tv_uploader_name.setText(trueusername);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
                selectedPhoto = "1";
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getStringImage(Bitmap bmp){
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadSelectedImage(){
        if(selectedPhoto == null){
            Toast.makeText(getApplicationContext(),
                    "No image selected", Toast.LENGTH_SHORT).show();
        }
        if(selectedPhoto != null){
            String uploadImage = getStringImage(bitmap);
            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("image", uploadImage);

            String cost_string = designCost.getText().toString();
            int int_cost = Integer.parseInt(cost_string);
            String description = userDescription.getText().toString();
            String encoded = uploadImage.toString();

            try {
                if (int_cost >= 150) {
                    if (!description.isEmpty() && !encoded.isEmpty() && !trueusername.isEmpty()) {
                        loading = ProgressDialog.show(UploadGalleryPicMainActivity.this, "Uploading Image", "Please wait...", true, true);
                        String cost = Integer.toString(int_cost);
                        uploadProcess(cost, trueusername, trueuseremail, description, encoded);
                    } else {

                        Toast.makeText(getApplicationContext(),
                                "User name field if empty", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(getApplicationContext(),
                            "The cost margin should be greater than 150", Toast.LENGTH_SHORT).show();
                }
            }catch (NoSuchFieldError e){
                Toast.makeText(getApplicationContext(),
                        "Error passing cost", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void uploadProcess(String cost, String trueusername, String trueuseremail, String description, String encoded){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setCost(cost);
        user.setuploaderName(trueusername);
        user.setPassEmail(trueuseremail);
        user.setDescription(description);
        user.setEncoded(encoded);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPLOAD_GALLERY_PIC);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                loading.dismiss();
                ServerResponse resp = response.body();
                Toast.makeText(getApplicationContext(),
                        resp.getMessage(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                loading.dismiss();
                Log.d(Constants.TAG,"failed");
                Toast.makeText(getApplicationContext(),
                        t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onClick(View v) {
        if (v == buttonChoose) {
            showFileChooser();
        }
        if(v == buttonUpload){
            uploadSelectedImage();
        }
        if(v == imageView){
            showFileChooser();
        }
        if(v == buttonMyGallery){
            Intent g = new Intent(this, TailorGalleryActivity.class);
            startActivity(g);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
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
