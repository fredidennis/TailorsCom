package com.tailorscom.fred.tailorscom.tailorGallery_package;

import android.app.ProgressDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;
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

public class TailorGalleryActivity extends AppCompatActivity {

    private String trueusername, message;
    private String URL_DATA;
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    private TextView users_gallery_text;
    ProgressDialog progressDialog;
    Constants sharedNameGet = new Constants();

    private List<TailorsGalleryListItem> tailorsGalleryListItem;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tailor_gallery);

        setTitle(sharedNameGet.getSHAREDNAME()+"\' Gallery");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerTailorsGallery);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        users_gallery_text = (TextView) findViewById(R.id.users_gallery_text);

        trueusername = sharedNameGet.getSHAREDNAME();

        URL_DATA = Constants.BASE_URL+"/TailorsCom-login-register/findMyGallery.php?page=1&user="+trueusername;

        message = "Gallery designs for \""+trueusername+"\" profile";

        tailorsGalleryListItem = new ArrayList<>();

        loadRecylerViewData();
    }

    private void loadRecylerViewData() {
        progressDialog = ProgressDialog.show(this,"Fetching Your Records","please wait...",true,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        users_gallery_text.setText(message);
                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                TailorsGalleryListItem item = new TailorsGalleryListItem(
                                        o.getString("desc"),
                                        o.getString("imageUrl"),
                                        o.getString("created_at"),
                                        o.getString("profile_id"),
                                        o.getString("likes"),
                                        o.getString("dislikes"),
                                        o.getString("starting_rate")
                                );
                                tailorsGalleryListItem.add(item);

                            }

                            adapter = new TailorsGalleryAdapter(tailorsGalleryListItem, getApplicationContext());
                            recyclerView.setAdapter(adapter);

                        } catch (JSONException e) {
                            Toast.makeText(getApplicationContext(),
                                    "Something went wrong while fetching Gallery Images", Toast.LENGTH_LONG).show();
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError v_error) {
                        String errorOutput = "\"Something went wrong while fetching your data\"";
                        users_gallery_text.setText(errorOutput);
                        progressDialog.dismiss();
                        Toast.makeText(getApplicationContext(), v_error.getMessage(),Toast.LENGTH_LONG).show();
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
