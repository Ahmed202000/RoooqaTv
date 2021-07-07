package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.adapter.FavoriteAdapter;
import com.roooqaTv.roooqatv.data.locale.room.RoomDao;
import com.roooqaTv.roooqatv.data.locale.room.RoomVideos;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;

import static com.roooqaTv.roooqatv.data.locale.room.RoomManger.getInistance;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class FavoriteActivity extends AppCompatActivity {


    RecyclerView rvFavorite;
    private List<RoomVideos> roomVideosData ;
    FavoriteAdapter favoriteAdapter;
    LinearLayoutManager linearLayoutManager;
    private RoomDao roomDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        rvFavorite=findViewById(R.id.activity_favorite_rv_cat);

        getFavorite();
    }

    private void getFavorite() {
        showProgressDialog(this,"Pleas Wait");

        roomDao = getInistance(this).roomDao();
        Executors.newSingleThreadExecutor().execute(new Runnable() {
            @Override
            public void run() {
                roomVideosData = roomDao.getAll();

                FavoriteActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        favoriteAdapter = new FavoriteAdapter(FavoriteActivity.this,FavoriteActivity.this, roomVideosData);
                        rvFavorite.setAdapter(favoriteAdapter);
                        favoriteAdapter.notifyDataSetChanged();

                        dismissProgressDialog();
                    }
                });
            }
        });


        try {
            linearLayoutManager = new LinearLayoutManager(this);
            rvFavorite.setLayoutManager(linearLayoutManager);

        }
        catch (Exception e)
        {

        }

    }
}