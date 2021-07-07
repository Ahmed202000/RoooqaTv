package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.adapter.AllVideosAdapter;
import com.roooqaTv.roooqatv.adapter.CategoriesAdapter;
import com.roooqaTv.roooqatv.adapter.FavoriteAdapter;
import com.roooqaTv.roooqatv.data.locale.MyCartAdapterCallback;
import com.roooqaTv.roooqatv.data.locale.room.RoomDao;
import com.roooqaTv.roooqatv.data.locale.room.RoomVideos;
import com.roooqaTv.roooqatv.data.model.getAllVideos.GetAllVideos;
import com.roooqaTv.roooqatv.data.model.getAllVideos.GetAllVideosDatum;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategories;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roooqaTv.roooqatv.data.api.RetrofitClient.getClient;
import static com.roooqaTv.roooqatv.data.locale.room.RoomManger.getInistance;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class AllVideosActivity extends AppCompatActivity implements MyCartAdapterCallback {

    RecyclerView rvAllVideos;
    private List<GetAllVideosDatum> getAllVideosData = new ArrayList<>();
    AllVideosAdapter allVideosAdapter;
    LinearLayoutManager linearLayoutManager;

    private List<RoomVideos> roomVideosData = new ArrayList<>();


    private int channels_id;
    private TextView tv;
    private RoomDao roomDao;
    private int position;
    private boolean isFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_videos);
        rvAllVideos=findViewById(R.id.all_videos_activity_rv_vid);

        roomDao = getInistance(this).roomDao();

        tv=findViewById(R.id.tv_no);

        channels_id=getIntent().getExtras().getInt("channels_id");

        gwtAllVideo();


        if(getAllVideosData.size()==0)
        {
            tv.setVisibility(View.VISIBLE);
        }


    }


    private void gwtAllVideo()
    {


        linearLayoutManager=new LinearLayoutManager(this);
        rvAllVideos.setLayoutManager(linearLayoutManager);

        showProgressDialog(this,"Pleas Wait");
        getClient().getAllVideos(channels_id).enqueue(new Callback<GetAllVideos>() {
            @Override
            public void onResponse(Call<GetAllVideos> call, Response<GetAllVideos> response)
            {
                dismissProgressDialog();
                try
                {
                    if (response.body().getCode()==200) {

                        Executors.newSingleThreadExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                roomVideosData = roomDao.getAll();

                                AllVideosActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {

                                        getAllVideosData.addAll(response.body().getData());

                                        for (int i = 0; i <getAllVideosData.size(); i++) {

                                            if (roomVideosData.contains(getAllVideosData.get(i))) {
                                                getAllVideosData.get(i).setFavorite(true);
                                            } else {
                                                getAllVideosData.get(i).setFavorite(false);

                                            }
                                        }

                                            allVideosAdapter=new AllVideosAdapter(AllVideosActivity.this,AllVideosActivity.this,getAllVideosData,roomVideosData);
                                        rvAllVideos.setAdapter(allVideosAdapter);
                                        allVideosAdapter.notifyDataSetChanged();

                                    }
                                });
                            }
                        });

                        getAllVideosData.addAll(response.body().getData());

                        for (int i = 0; i <getAllVideosData.size(); i++) {

                            if (roomVideosData.contains(getAllVideosData.get(i))) {
                                getAllVideosData.get(i).setFavorite(true);
                            }
                            else {
                                getAllVideosData.get(i).setFavorite(false);

                            }

                        }

                        allVideosAdapter=new AllVideosAdapter(AllVideosActivity.this,AllVideosActivity.this,getAllVideosData,roomVideosData);
                        rvAllVideos.setAdapter(allVideosAdapter);
                        allVideosAdapter.notifyDataSetChanged();
                        if(getAllVideosData.size()!=0)
                        {
                            tv.setVisibility(View.GONE);

                        }


                    }
                    else if (response.body().getCode()==401)
                    {
                        dismissProgressDialog();
                        Toast.makeText(AllVideosActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<GetAllVideos> call, Throwable t) {
                dismissProgressDialog();

            }
        });


    }

    @Override
    public void onMethodCallback(int allProductsTotalPrice) {


    }
}