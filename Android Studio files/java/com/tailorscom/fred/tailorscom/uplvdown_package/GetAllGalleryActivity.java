package com.tailorscom.fred.tailorscom.uplvdown_package;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.profilePicUpload_package.UploadProfilePicMainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class GetAllGalleryActivity extends AppCompatActivity implements View.OnClickListener{

    private String imagesJSON;

    private static final String JSON_ARRAY ="result";
    private static final String IMAGE_URL = "url";

    private JSONArray arrayImages= null;

    private int TRACK = 0;

    //private static final String SERVER_ADDRESS = ""+UploadProfilePicMainActivity.SERVER_ADRESS+"";
    private static final String IMAGES_URL = ""+ UploadProfilePicMainActivity.SERVER_ADRESS+"getAllImages.php";

    private Button buttonFetchImages;
    private Button buttonMoveNext;
    private Button buttonMovePrevious;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_all_gallery);
        setTitle("TailorsCom Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        imageView = (ImageView) findViewById(R.id.imageView);
        buttonFetchImages = (Button) findViewById(R.id.buttonFetchImages);
        buttonMoveNext = (Button) findViewById(R.id.buttonNext);
        buttonMovePrevious = (Button) findViewById(R.id.buttonPrev);
        buttonFetchImages.setOnClickListener(this);
        buttonMoveNext.setOnClickListener(this);
        buttonMovePrevious.setOnClickListener(this);
    }

    private void extractJSON(){
        try {
            JSONObject jsonObject = new JSONObject(imagesJSON);
            arrayImages = jsonObject.getJSONArray(JSON_ARRAY);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void showImage(){
        try {
            JSONObject jsonObject = arrayImages.getJSONObject(TRACK);
            getImage(jsonObject.getString(IMAGE_URL));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void moveNext(){
        if(TRACK < arrayImages.length()){
            TRACK++;
            showImage();
        }
    }

    private void movePrevious(){
        if(TRACK>0){
            TRACK--;
            showImage();
        }
    }

    private void getAllImages() {
        class GetAllImages extends AsyncTask<String,Void,String>{
            ProgressDialog loading;
            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GetAllGalleryActivity.this, "Fetching Data...","Please Wait...",true,true);
            }

            @Override
            protected void onPostExecute(String s) {
                super.onPostExecute(s);
                loading.dismiss();
                imagesJSON = s;
                extractJSON();
                showImage();
            }

            @Override
            protected String doInBackground(String... params) {
                String uri = params[0];
                BufferedReader bufferedReader = null;
                try {
                    URL url = new URL(uri);
                    HttpURLConnection con = (HttpURLConnection) url.openConnection();
                    StringBuilder sb = new StringBuilder();

                    bufferedReader = new BufferedReader(new InputStreamReader(con.getInputStream()));

                    String json;
                    while((json = bufferedReader.readLine())!= null){
                        sb.append(json+"\n");
                    }

                    return sb.toString().trim();

                }catch(Exception e){
                    return null;
                }
            }
        }
        GetAllImages gai = new GetAllImages();
        gai.execute(IMAGES_URL);
    }

    private void getImage(String urlToImage){
        class GetImage extends AsyncTask<String,Void,Bitmap>{
            ProgressDialog loading;
            @Override
            protected Bitmap doInBackground(String... params) {
                URL url = null;
                Bitmap image = null;

                String urlToImage = params[0];
                try {
                    url = new URL(urlToImage);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(GetAllGalleryActivity.this,"Downloading Image...","Please wait...",true,true);
            }

            @Override
            protected void onPostExecute(Bitmap bitmap) {
                super.onPostExecute(bitmap);
                loading.dismiss();
                imageView.setImageBitmap(bitmap);
            }
        }
        GetImage gi = new GetImage();
        gi.execute(urlToImage);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonFetchImages) {
            getAllImages();
        }
        if(v == buttonMoveNext){
            moveNext();
        }
        if(v== buttonMovePrevious){
            movePrevious();
        }
    }

    @Override //for showing the back arrow in the activity
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public void onBackPressed() //for handling the action for going back
    {
        if (getFragmentManager().getBackStackEntryCount() == 0) {
            this.finish();
        } else {
            super.onBackPressed(); //replaced
        }
    }
}
