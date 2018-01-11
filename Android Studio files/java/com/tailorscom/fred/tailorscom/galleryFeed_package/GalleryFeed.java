package com.tailorscom.fred.tailorscom.galleryFeed_package;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.tailorscom.fred.tailorscom.Constants;
import com.tailorscom.fred.tailorscom.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GalleryFeed extends AppCompatActivity {

    public int PAGE_NUM = 1;
    private String URL_DATA = Constants.BASE_URL+"/TailorsCom-login-register/feedAll.php?page=1";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private ImageView no_image;
    ProgressDialog progressDialog;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_feed);
        setTitle("TailorsCom : Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        no_image = (ImageView) findViewById(R.id.no_image);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();

        loadRecylerViewData();
    }

    public void loadRecylerViewData(){
        //progressDialog = new ProgressDialog(this);
        //progressDialog.setMessage("Loading data");
        //progressDialog.show();
        progressDialog = ProgressDialog.show(this,"Loading Data","please wait...",true,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                ListItem item = new ListItem(
                                        o.getString("likes"),
                                        o.getString("dislikes"),
                                        o.getString("name"),
                                        o.getString("publisher"),
                                        o.getString("image"),
                                        o.getString("cost"),
                                        o.getString("email"),
                                        o.getString("image_id")
                                );
                                listItems.add(item);
                                PAGE_NUM = array.length();
                                PAGE_NUM =(PAGE_NUM/3);
                            }

                            adapter = new MyAdapter(listItems, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong while fetching Gallery Images", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                            no_image.setVisibility(View.VISIBLE);
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError v_error) {
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), v_error.getMessage(),Toast.LENGTH_LONG).show();
                        no_image.setVisibility(View.VISIBLE);
                    }
                });
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
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
