package com.tailorscom.fred.tailorscom.tailorGallery_package;

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
import com.tailorscom.fred.tailorscom.models.ServerRequest;
import com.tailorscom.fred.tailorscom.models.ServerResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by Home on 3/27/2017.
 */

public class TailorsGalleryAdapter extends RecyclerView.Adapter<TailorsGalleryAdapter.ViewHolder>{
    private List<TailorsGalleryListItem> listItems;
    private Context context;

    public TailorsGalleryAdapter(List<TailorsGalleryListItem> listItem, Context context) {
        this.context = context;
        this.listItems = listItem;
    }

    @Override
    public TailorsGalleryAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tailor_gallery_list_card, parent, false);
        return new TailorsGalleryAdapter.ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(final TailorsGalleryAdapter.ViewHolder holder, int position) {
        final TailorsGalleryListItem listItem = listItems.get(position);

        holder.dislike.setText(listItem.getDislikes());
        holder.like.setText(listItem.getLikes());
        holder.textViewDesc.setText(listItem.getDesc());
        holder.cost.setText(listItem.getStarting_rate());
        holder.the_date.setText(listItem.getCreated_at());

        Picasso.with(context)
                .load(listItem.getImageUrl())
                .into(holder.imageView);

        holder.delete.setOnClickListener(new View.OnClickListener() {
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

                final Delete action = new Delete();
                action.setImage_id(imageId);
                ServerRequest request = new ServerRequest();
                request.setOperation(Constants.DELETE_TAILOR_GALLERY);
                request.setDelete(action);
                Call<ServerResponse> response = requestInterface.operation(request);

                response.enqueue(new Callback<ServerResponse>() {
                    @Override
                    public void onResponse(Call<ServerResponse> call, retrofit2.Response<ServerResponse> response) {
                        try{
                            ServerResponse resp = response.body();
                            if(resp.getResult().equals(Constants.SUCCESS)){
                                holder.loading.setVisibility(View.GONE);
                                Toast.makeText(context, "You deleted this gallery", Toast.LENGTH_SHORT).show();
                                holder.my_card.setVisibility(View.GONE);
                                holder.img_deleted.setVisibility(View.VISIBLE);
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

        public TextView textViewDesc;
        public TextView cost, the_date;
        public ImageView imageView, img_deleted;
        public LinearLayout my_card;
        public TextView like, dislike;
        public Button delete;
        public ProgressBar loading;


        public ViewHolder(View itemView) {
            super(itemView);

            delete = (Button) itemView.findViewById(R.id.btn_delete);

            loading = (ProgressBar) itemView.findViewById(R.id.progress);

            like = (TextView) itemView.findViewById(R.id.like);
            dislike = (TextView) itemView.findViewById(R.id.dislike);
            textViewDesc = (TextView) itemView.findViewById(R.id.textViewDesc);
            cost = (TextView) itemView.findViewById(R.id.cost);
            the_date = (TextView) itemView.findViewById(R.id.the_date);

            my_card = (LinearLayout) itemView.findViewById(R.id.my_card);
            imageView = (ImageView) itemView.findViewById(R.id.imageViewItem);
            img_deleted = (ImageView) itemView.findViewById(R.id.img_deleted);
        }
    }
}
