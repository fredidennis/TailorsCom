package com.tailorscom.fred.tailorscom.profilePicUpload_package;

import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
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
import android.widget.Toast;

import com.tailorscom.fred.tailorscom.Constants;
import com.tailorscom.fred.tailorscom.LoginFragment;
import com.tailorscom.fred.tailorscom.ProfileFragment;
import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.RequestInterface;
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;
import com.tailorscom.fred.tailorscom.models.User;


import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UploadProfilePicMainActivity extends AppCompatActivity implements View.OnClickListener{

    public static final String SERVER_ADRESS = Constants.BASE_URL+"/TailorsCom-login-register/";
    public static final String UPLOAD_URL = ""+SERVER_ADRESS+"upload.php";
    public static final String UPLOAD_KEY = "image";
    public static final String TAG = "MY MESSAGE";

    String selectedPhoto;
    String trueusername;

    private int PICK_IMAGE_REQUEST = 1895;
    final int PIC_CROP = 1164;

    private Button buttonChoose;
    private Button buttonUpload;


    private ImageView imageView;

    public EditText userName;

    private Bitmap bitmap, selectedBitmap;

    private static Uri filePath;

    ProgressDialog loading;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_prof_pic_main);
        setTitle("Upload Profile Picture");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        buttonChoose = (Button) findViewById(R.id.buttonChoose);
        buttonUpload = (Button) findViewById(R.id.buttonUpload);


        userName = (EditText) findViewById(R.id.userName);

        imageView = (ImageView) findViewById(R.id.imageView);

        imageView.setOnClickListener(this);
        buttonChoose.setOnClickListener(this);
        buttonUpload.setOnClickListener(this);

        Constants sharedNameGet = new Constants();
        trueusername = sharedNameGet.getSHAREDNAME();
        userName.setText(trueusername);

    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
        }

    private void performCrop(Uri picUri) {
        try {
            Intent cropIntent = new Intent("com.android.camera.action.CROP");
            cropIntent.setDataAndType(picUri, "image/*");
            cropIntent.putExtra("crop", true);
            cropIntent.putExtra("aspectX", 1);
            cropIntent.putExtra("aspectY", 1);
            cropIntent.putExtra("outputX", 128);
            cropIntent.putExtra("outputY", 128);
            cropIntent.putExtra("return-data", true);
            startActivityForResult(cropIntent, PIC_CROP);
        }
        catch (ActivityNotFoundException anfe) {
            String errorMessage = "Whoops - your device doesn't support the crop action!";
            Toast toast = Toast.makeText(this, errorMessage, Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {

            filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                performCrop(filePath);
                //imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }

        } else if (requestCode == PIC_CROP) {
            if (data != null) {
                Bundle extras = data.getExtras();
                selectedBitmap = extras.getParcelable("data");
                imageView.setImageBitmap(selectedBitmap);
                selectedPhoto = "1";
            }else{
                Toast.makeText(this, "data is null", Toast.LENGTH_SHORT).show();
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
            String uploadImage = getStringImage(selectedBitmap);
            HashMap<String, String> postData = new HashMap<String, String>();
            postData.put("image", uploadImage);


            String username = userName.getText().toString();
            String encoded = uploadImage.toString();


            if (!username.isEmpty() && !encoded.isEmpty()) {
                loading = ProgressDialog.show(UploadProfilePicMainActivity.this, "Uploading Image", "Please wait...", true, true);
                uploadProcess(username, encoded);
            } else {
                Toast.makeText(getApplicationContext(),
                        "User name field if empty", Toast.LENGTH_SHORT).show();
            }

        }
    }

    private void uploadProcess(String username, String encoded){


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        User user = new User();
        user.setuploaderName(username);
        user.setEncoded(encoded);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPLOAD_PROFILE_PIC);
        request.setUser(user);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {

                loading.dismiss();
                ServerResponse resp = response.body();
                Toast.makeText(getApplicationContext(),
                        resp.getMessage()+"\nPlease Login Again", Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), resp.getMessage(), Snackbar.LENGTH_LONG).show();
                //progress.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                loading.dismiss();
                //progress.setVisibility(View.INVISIBLE);
                Log.d(Constants.TAG,"failed");
                Toast.makeText(getApplicationContext(),
                        t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                //Snackbar.make(getView(), t.getLocalizedMessage(), Snackbar.LENGTH_LONG).show();
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
/*
    private void uploadImage() {
        if (selectedPhoto == null) {
            Toast.makeText(getApplicationContext(),
                    "No image selected", Toast.LENGTH_SHORT).show();

        }
        if (selectedPhoto != null){
            class UploadImage extends AsyncTask<Bitmap, Void, String> {

                private ProgressDialog loading;
                RequestHandler rh = new RequestHandler();

                @Override
                protected void onPreExecute() {
                    super.onPreExecute();
                    loading = ProgressDialog.show(UploadProfilePicMainActivity.this, "Uploading Image", "Please wait...", true, true);
                }

                @Override
                protected void onPostExecute(String s) {
                    super.onPostExecute(s);
                    loading.dismiss();
                    Toast.makeText(getApplicationContext(), s, Toast.LENGTH_LONG).show();
                }

                @Override
                protected String doInBackground(Bitmap... params) {
                    Bitmap bitmap = params[0];
                    String uploadImage = getStringImage(bitmap);

                    HashMap<String, String> data = new HashMap<>();
                    data.put(UPLOAD_KEY, uploadImage);

                    String result = rh.sendPostRequest(UPLOAD_URL, data);

                    return result;
                }
            }

            UploadImage ui = new UploadImage();
            ui.execute(bitmap);

        }

    }
    */
