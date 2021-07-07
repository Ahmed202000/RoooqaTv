package com.roooqaTv.roooqatv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.data.locale.room.RoomDao;
import com.roooqaTv.roooqatv.data.locale.room.RoomVideos;
import com.roooqaTv.roooqatv.data.model.getAllVideos.GetAllVideosDatum;
import com.roooqaTv.roooqatv.view.activity.FavoriteActivity;
import com.roooqaTv.roooqatv.view.activity.OpenVideoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.ButterKnife;

import static com.roooqaTv.roooqatv.data.locale.room.RoomManger.getInistance;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class FavoriteAdapter extends RecyclerView.Adapter<FavoriteAdapter.ViewHolder> {
    private Context context;
    private FavoriteActivity activity;
    private List<RoomVideos> roomVideosData = new ArrayList<>();

    private ImageView logo,favorite;
    private TextView name;

    boolean isFavorite=false;
    private final RoomDao roomDao;

    public FavoriteAdapter(Context context, FavoriteActivity activity, List<RoomVideos> roomVideosData) {
        this.context = context;
        this.activity = activity;
        this.roomVideosData = roomVideosData;
        roomDao = getInistance(context).roomDao();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_video,
                parent, false);

        logo=view.findViewById(R.id.item_all_video_iv_logo);
        name=view.findViewById(R.id.item_all_video_tv_name);
        favorite=view.findViewById(R.id.item_all_video_iv_favorite);






        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {



            if (roomVideosData.get(position).isFavor() == true)
            {
                favorite.setImageResource(R.drawable.ic_baseline_favorite);
            }


        if (roomVideosData.get(position).getLink() !=null)
        {
            Glide.with(context).load(roomVideosData.get(position).getLink()).into(logo);

        }
        else
        {
            logo.setImageResource(R.drawable.ic_logo);

        }


        name.setText(roomVideosData.get(position).getNameItem());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, OpenVideoActivity.class);
                intent.putExtra("Link",roomVideosData.get(position).getLink());
                context.startActivity(intent);
            }
        });

        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                if (isFavorite==false)
                {
                    isFavorite=true;
                    favorite.setImageResource(R.drawable.ic_baseline_favorite_border);

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {

                            roomDao.deleteById(roomVideosData.get(position).getItemId());
                            roomVideosData.remove(position);

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Delete Favorite", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });
                }
            }
        });




    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return roomVideosData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private View view;




        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
           ButterKnife.bind(this, view);
        }
    }
}
