package com.roooqaTv.roooqatv.adapter;

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
import com.roooqaTv.roooqatv.view.activity.AllVideosActivity;
import com.roooqaTv.roooqatv.view.activity.OpenVideoActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import butterknife.BindView;
import butterknife.ButterKnife;

import static com.roooqaTv.roooqatv.data.locale.room.RoomManger.getInistance;

public class AllVideosAdapter extends RecyclerView.Adapter<AllVideosAdapter.ViewHolder> {

    private Context context;
    private AllVideosActivity activity;

    private List<GetAllVideosDatum> getAllVideosData = new ArrayList<>();

    private List<RoomVideos> roomVideosData;

    private final RoomDao roomDao;
    boolean first = true;



    public AllVideosAdapter(Context context, AllVideosActivity activity, List<GetAllVideosDatum> getAllVideosData, List<RoomVideos> roomVideosData) {
        this.context = context;
        this.activity = activity;
        this.getAllVideosData = getAllVideosData;
        this.roomVideosData = roomVideosData;


        roomDao = getInistance(context).roomDao();
        Toast.makeText(activity, roomVideosData.size()+"", Toast.LENGTH_SHORT).show();

    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_all_video,
                parent, false);

        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);


        if (getAllVideosData.get(position).isFavorite()==true) {
            holder.itemAllVideoIvFavorite.setImageResource(R.drawable.ic_baseline_favorite);
        }
        else    if (getAllVideosData.get(position).isFavorite()==false) {
            holder.itemAllVideoIvFavorite.setImageResource(R.drawable.ic_baseline_favorite_border);
        }

    }

    private void setData(ViewHolder holder, int position) {

        if (getAllVideosData.get(position).getLogo() != null) {
            Glide.with(context).load(getAllVideosData.get(position).getLogo()).into(holder.itemAllVideoIvLogo);

        } else {
            holder.itemAllVideoIvLogo.setImageResource(R.drawable.ic_logo);

        }


        holder.itemAllVideoTvName.setText(getAllVideosData.get(position).getNameAr());


        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, OpenVideoActivity.class);
                intent.putExtra("Link", getAllVideosData.get(position).getLink());
                context.startActivity(intent);
            }
        });

        holder.itemAllVideoIvFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (getAllVideosData.get(position).isFavorite()==false) {
                    getAllVideosData.get(position).setFavorite(true);

                    holder.itemAllVideoIvFavorite.setImageResource(R.drawable.ic_baseline_favorite);
                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run()
                        {
                            RoomVideos item = new RoomVideos(getAllVideosData.get(position).getId(),
                                    getAllVideosData.get(position).getNameAr(),
                                    getAllVideosData.get(position).getLink());

                            roomDao.addItem(item);
                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    Toast.makeText(context, "Add to Favorite", Toast.LENGTH_SHORT).show();

                                }
                            });
                        }
                    });

                }   else{
                    getAllVideosData.get(position).setFavorite(false);

                    holder.itemAllVideoIvFavorite.setImageResource(R.drawable.ic_baseline_favorite_border);

                    Executors.newSingleThreadExecutor().execute(new Runnable() {
                        @Override
                        public void run() {

                            roomDao.deleteById(getAllVideosData.get(position).getId());

                            activity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    notifyDataSetChanged();

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
        return getAllVideosData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        @BindView(R.id.item_all_video_iv_logo)
        ImageView itemAllVideoIvLogo;
        @BindView(R.id.item_all_video_tv_name)
        TextView itemAllVideoTvName;
        @BindView(R.id.item_all_video_iv_favorite)
        ImageView itemAllVideoIvFavorite;

        private View view;


        public ViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            ButterKnife.bind(this, view);


        }
    }
}
