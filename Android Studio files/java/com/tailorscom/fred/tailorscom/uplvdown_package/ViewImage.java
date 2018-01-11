package com.tailorscom.fred.tailorscom.uplvdown_package;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.profilePicUpload_package.UploadProfilePicMainActivity;

public class ViewImage extends AppCompatActivity implements View.OnClickListener {

    private static final String SERVER_ADDRESS = UploadProfilePicMainActivity.SERVER_ADRESS;
    private EditText editTextId;
    private Button buttonGetImage, buttonViewAll;
    private ImageView imageView;

    private RequestHandler requestHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_image);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        editTextId = (EditText) findViewById(R.id.editTextId);
        buttonGetImage = (Button) findViewById(R.id.buttonGetImage);
        buttonViewAll = (Button) findViewById(R.id.buttonViewAll);
        imageView = (ImageView) findViewById(R.id.imageViewShow);

        requestHandler = new RequestHandler();

        buttonGetImage.setOnClickListener(this);
        buttonViewAll.setOnClickListener(this);
    }

    private void getImage() {
        String id = editTextId.getText().toString().trim();
        class GetImage extends AsyncTask<String, Void, Bitmap> {
            ProgressDialog loading;

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                loading = ProgressDialog.show(ViewImage.this, "Downloading image...","Please wait...", true, true);
            }

            @Override
            protected void onPostExecute(Bitmap b) {
                super.onPostExecute(b);
                loading.dismiss();
                imageView.setImageBitmap(b);
            }

            @Override
            protected Bitmap doInBackground(String... params) {
                String id = params[0];
                String add = ""+SERVER_ADDRESS+"getImage.php?id=" + id;
                URL url = null;
                Bitmap image = null;
                try {
                    url = new URL(add);
                    image = BitmapFactory.decodeStream(url.openConnection().getInputStream());
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return image;
            }
        }

        GetImage gi = new GetImage();
        gi.execute(id);
    }

    public void goToViewAll(){
        Intent i = new Intent(this, GetAllGalleryActivity.class);
        startActivity(i);
    }

    @Override
    public void onClick(View v) {
        if(v == buttonGetImage){
            getImage();
        }
        if(v == buttonViewAll){
            goToViewAll();
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