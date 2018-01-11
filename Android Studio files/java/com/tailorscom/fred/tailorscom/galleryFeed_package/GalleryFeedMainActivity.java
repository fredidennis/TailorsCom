package com.tailorscom.fred.tailorscom.galleryFeed_package;
/*
import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
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


public class GalleryFeedMainActivity extends AppCompatActivity {

    public int PAGE_NUM = 1;
    private String URL_DATA = Constants.BASE_URL+"/TailorsCom-login-register/feedAll.php?page=1";
    private RecyclerView recyclerView;
    private RecyclerView.Adapter adapter;

    private List<ListItem> listItems;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_main);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        listItems = new ArrayList<>();



        /*for(int i = 0; i<=10; i++){
            ListItem listItem = new ListItem(
                    "heading " + (i+1),
                    "Hello World. What a wounderfull day"
            );
            listItems.add(listItem);
        }

        adapter = new MyAdapter(listItems, this);

        recyclerView.setAdapter(adapter);
        loadRecylerViewData();
    }

    void loadRecylerViewData(){
        final ProgressDialog progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Loading data");
        progressDialog.show();

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
                                        o.getString("name"),
                                        o.getString("publisher"),
                                        o.getString("image")
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
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError v_error) {
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
*/

/*private void fetchData(String imageId) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Constants.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        RequestInterface requestInterface = retrofit.create(RequestInterface.class);

        final Like like = new Like();
        like.setImage_id(imageId);
        ServerRequest request = new ServerRequest();
        request.setOperation(Constants.UPLOAD_LIKES);
        request.setLike(like);
        Call<ServerResponse> response = requestInterface.operation(request);

        response.enqueue(new Callback<ServerResponse>() {
            @Override
            public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                try{
                    ServerResponse resp = response.body();
                    Toast.makeText(context, resp.getMessage(), Toast.LENGTH_SHORT).show();
                    if(resp.getResult().equals(Constants.SUCCESS)){
                        onBindViewHolder(like);
                    }
                }
                catch (NullPointerException e){

                }

            }

            @Override
            public void onFailure(Call<ServerResponse> call, Throwable t) {

                Log.d(Constants.TAG,"failed");
                Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }*/


    /*public String sendGetRequest(String uri) {
        try {
            URL url = new URL(uri);
            HttpURLConnection con = (HttpURLConnection) url.openConnection();
            con.setConnectTimeout(1000 * 30);
            con.setReadTimeout(1000 * 30);
            con.setRequestMethod("GET");
            con.connect();
            String code = con.getResponseMessage();
            int code2 = con.getResponseCode();
            //BufferedReader bufferedReader = new BufferedReader(new InputStreamReader((Stringcode.toString()));
            //Toast.makeText(context, ""+code.toString(), Toast.LENGTH_SHORT).show();
            //String result;
            //StringBuilder sb = new StringBuilder();
            //while((result = bufferedReader.readLine())!=null){
            //    sb.append(result);
            //}
            return code.toString();
        } catch (Exception e) {
            return null;
        }
    }*/



    /*public static void main (String args[]) throws Exception
    {

        URL url = new URL("http://google.com");
        HttpURLConnection connection = (HttpURLConnection)url.openConnection();
        connection.setRequestMethod("GET");
        connection.connect();

        int code = connection.getResponseCode();
        System.out.println("Response code of the object is "+code);
        if (code==200)
        {
            System.out.println("OK");
        }
    }*/

/*holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.setVisibility(View.VISIBLE);
                String imageId = listItem.getImageId();
                fetchData(imageId);
            }

            public void fetchData(String imageId) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);

                final Like like = new Like();
                like.setImage_id(imageId);
                ServerRequest request = new ServerRequest();
                request.setOperation(Constants.UPLOAD_LIKES);
                request.setLike(like);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                        try{
                            ServerResponse resp = response.body();
                            if(resp.getResult().equals(Constants.SUCCESS)){
                                holder.loading.setVisibility(View.GONE);
                                Toast.makeText(context, resp.getMessage()+"\""+listItem.getHead()+"\'s\" gallery image", Toast.LENGTH_SHORT).show();
                                holder.like.setText(resp.getLike().getLike());
                            }
                        }
                        catch (NullPointerException e){

                        }

                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        holder.loading.setVisibility(View.GONE);
                        Log.d(Constants.TAG,"failed");
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

        /*holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.setVisibility(View.VISIBLE);
                String imageId = listItem.getImageId();
                fetchData(imageId);
            }

            private void fetchData(String imageId) {
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Constants.BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build();

                RequestInterface requestInterface = retrofit.create(RequestInterface.class);

                final Like dislike = new Like();
                dislike.setImage_id(imageId);
                ServerRequest request = new ServerRequest();
                request.setOperation(Constants.UPLOAD_DISLIKES);
                request.setDislike(dislike);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                        try{
                            ServerResponse resp = response.body();
                            if(resp.getResult().equals(Constants.SUCCESS)){
                                holder.loading.setVisibility(View.GONE);
                                Toast.makeText(context, resp.getMessage()+"\""+listItem.getHead()+"\'s\" gallery image", Toast.LENGTH_SHORT).show();
                                holder.dislike.setText(resp.getDislike().getDislike());
                            }
                        }
                        catch (NullPointerException e){

                        }

                    }

                    @Override
                    public void onFailure(Call<ServerResponse> call, Throwable t) {
                        holder.loading.setVisibility(View.GONE);
                        Log.d(Constants.TAG,"failed");
                        Toast.makeText(context, t.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });*/

