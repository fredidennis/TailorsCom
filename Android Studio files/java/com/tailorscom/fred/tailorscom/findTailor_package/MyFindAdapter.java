package com.tailorscom.fred.tailorscom.findTailor_package;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.tailorscom.fred.tailorscom.Constants;
import com.tailorscom.fred.tailorscom.R;
import com.tailorscom.fred.tailorscom.RequestInterface;
import com.tailorscom.fred.tailorscom.galleryFeed_package.Like;
import com.tailorscom.fred.tailorscom.galleryFeed_package.ListItem;
import com.tailorscom.fred.tailorscom.galleryFeed_package.MyAdapter;
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 3/21/2017.
 */

public class MyFindAdapter extends RecyclerView.Adapter<MyFindAdapter.ViewHolder>{
    private List<FindFeedListItem> listItems;
    private Context context;

    public MyFindAdapter(List<FindFeedListItem> listItem, Context context) {
        this.context = context;
        this.listItems = listItem;
    }

    @Override
    public MyFindAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.results_list_card_item, parent, false);
        return new MyFindAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final MyFindAdapter.ViewHolder holder, int position) {
        final FindFeedListItem listItem = listItems.get(position);

        holder.dislike.setText(listItem.getDislikes());
        holder.like.setText(listItem.getLikes());
        holder.textViewName.setText(listItem.getName());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.the_gender.setText(listItem.getGender());
        holder.rate.setText(listItem.getStarting_rate());
        holder.the_email.setText(listItem.getEmail());
        holder.the_speciality.setText(listItem.getSpeciality());
        holder.the_phone_number.setText(listItem.getContact());
        holder.the_location.setText(listItem.getLocation());
        holder.the_date.setText(listItem.getCreated_at());

        Picasso.with(context)
                .load(listItem.getImageUrl())
                .into(holder.imageView);
        holder.my_card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(context, "You clicked on "+listItem.getName()+" profile", Toast.LENGTH_SHORT).show();
            }
        });

        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.setVisibility(View.VISIBLE);
                String imageId = listItem.getProfile_id();
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
                request.setOperation(Constants.UPLOAD_PROFILE_LIKES);
                request.setLike(like);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                        try{
                            ServerResponse resp = response.body();
                            if(resp.getResult().equals(Constants.SUCCESS)){
                                holder.loading.setVisibility(View.GONE);
                                Toast.makeText(context, resp.getMessage()+"\""+listItem.getName()+"\'s\" profile", Toast.LENGTH_SHORT).show();
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
        });

        holder.dislike.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.loading.setVisibility(View.VISIBLE);
                String imageId = listItem.getProfile_id();
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
                request.setOperation(Constants.UPLOAD_PROFILE_DISLIKES);
                request.setDislike(dislike);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                        try{
                            ServerResponse resp = response.body();
                            if(resp.getResult().equals(Constants.SUCCESS)){
                                holder.loading.setVisibility(View.GONE);
                                Toast.makeText(context, resp.getMessage()+"\""+listItem.getName()+"\'s\" profile", Toast.LENGTH_SHORT).show();
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
        });


    }

    @Override
    public int getItemCount() {
        return listItems.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        public TextView textViewName;
        public TextView textViewDesc;
        public TextView rate, the_email, the_phone_number, the_location, the_gender, the_speciality, the_date;
        public ImageView imageView;
        public LinearLayout my_card, cost_layout, email_layout;
        public Button like, dislike;
        public ProgressBar loading;


        public ViewHolder(View itemView) {
            super(itemView);

            like = (Button) itemView.findViewById(R.id.like);
            dislike = (Button) itemView.findViewById(R.id.dislike);

            loading = (ProgressBar) itemView.findViewById(R.id.progress);

            textViewName = (TextView) itemView.findViewById(R.id.textViewName);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            rate = (TextView) itemView.findViewById(R.id.rate);
            the_email = (TextView) itemView.findViewById(R.id.the_email);
            the_phone_number = (TextView) itemView.findViewById(R.id.the_phone_number);
            the_location = (TextView) itemView.findViewById(R.id.the_location);
            the_gender = (TextView) itemView.findViewById(R.id.the_gender);
            the_speciality = (TextView) itemView.findViewById(R.id.the_speciality);
            the_date = (TextView) itemView.findViewById(R.id.the_date);

            imageView = (ImageView) itemView.findViewById(R.id.imageViewItem);

            my_card = (LinearLayout) itemView.findViewById(R.id.my_card);
            cost_layout = (LinearLayout) itemView.findViewById(R.id.cost_layout);
            email_layout = (LinearLayout) itemView.findViewById(R.id.email_layout);
        }
    }
}
