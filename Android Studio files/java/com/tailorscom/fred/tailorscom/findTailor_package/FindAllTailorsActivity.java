package com.tailorscom.fred.tailorscom.findTailor_package;

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

public class FindAllTailorsActivity extends AppCompatActivity {

    private String URL_DATA = Constants.BASE_URL+"/TailorsCom-login-register/findAllTailors.php?page=1";
    private String putOut = "\"Displaying All Tailors in TailorsCom Database\"";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;
    ProgressDialog progressDialog;
    private TextView search_criteria;

    private List<FindFeedListItem> findTailorListItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_all_tailors);
        setTitle("Find A Tailor");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        search_criteria = (TextView) findViewById(R.id.search_criteria);
        recyclerView = (RecyclerView) findViewById(R.id.recyclerViewFindTailor);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findTailorListItems = new ArrayList<>();


        loadRecylerViewData();
    }

    public void loadRecylerViewData(){
        progressDialog = ProgressDialog.show(this,"Fetching Records Data","please wait...",true,true);

        StringRequest stringRequest = new StringRequest(Request.Method.GET,
                URL_DATA,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String resp) {
                        try {
                            search_criteria.setText(putOut);
                        }catch (NullPointerException e){
                            Toast.makeText(FindAllTailorsActivity.this, "error passing search criteria", Toast.LENGTH_SHORT).show();
                        }

                        progressDialog.dismiss();
                        try {
                            JSONObject jsonObject = new JSONObject(resp);
                            JSONArray array = jsonObject.getJSONArray("result");

                            for(int i = 0; i<array.length(); i++){
                                JSONObject o = array.getJSONObject(i);
                                FindFeedListItem item = new FindFeedListItem(
                                        o.getString("name"),
                                        o.getString("email"),
                                        o.getString("desc"),
                                        o.getString("gender"),
                                        o.getString("speciality"),
                                        o.getString("location"),
                                        o.getString("contact"),
                                        o.getString("imageUrl"),
                                        o.getString("created_at"),
                                        o.getString("starting_rate"),
                                        o.getString("profile_id"),
                                        o.getString("likes"),
                                        o.getString("dislikes")
                                );
                                findTailorListItems.add(item);

                            }

                            adapter = new MyFindAdapter(findTailorListItems, getApplicationContext());
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
                        String errorOutput = "\"Something went wrong fetching the data of all tailors\"";
                        search_criteria.setText(errorOutput);
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
