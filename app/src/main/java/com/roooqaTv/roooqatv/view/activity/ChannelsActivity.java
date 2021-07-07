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
import com.roooqaTv.roooqatv.adapter.CategoriesAdapter;
import com.roooqaTv.roooqatv.adapter.ChannelsAdapter;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategories;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategoriesDatum;
import com.roooqaTv.roooqatv.data.model.getChannels.GetChannels;
import com.roooqaTv.roooqatv.data.model.getChannels.GetChannelsDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roooqaTv.roooqatv.data.api.RetrofitClient.getClient;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class ChannelsActivity extends AppCompatActivity {

    RecyclerView rvChannels;

    private int category_id;

    LinearLayoutManager linearLayoutManager;
    ChannelsAdapter channelsAdapter;
    private List<GetChannelsDatum> getChannelsData = new ArrayList<>();
    private TextView tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_channels);

        tv=findViewById(R.id.tv_no);

        category_id=getIntent().getExtras().getInt("category_id");
        rvChannels=findViewById(R.id.channels_activity_rv_cat);

        getChannels();

        if(getChannelsData.size()==0)
        {
            tv.setVisibility(View.VISIBLE);
        }
    }


    private void getChannels()
    {
        linearLayoutManager=new LinearLayoutManager(this);
        rvChannels.setLayoutManager(linearLayoutManager);

        showProgressDialog(this,"Pleas Wait");
        getClient().getChannels(category_id).enqueue(new Callback<GetChannels>() {
            @Override
            public void onResponse(Call<GetChannels> call, Response<GetChannels> response)
            {
                dismissProgressDialog();
                try
                {
                    if (response.body().getCode()==200) {

                        getChannelsData.addAll(response.body().getData());
                        channelsAdapter=new ChannelsAdapter(ChannelsActivity.this,getChannelsData);
                        rvChannels.setAdapter(channelsAdapter);
                        channelsAdapter.notifyDataSetChanged();

                        if(getChannelsData.size()!=0)
                        {
                            tv.setVisibility(View.GONE);
                        }


                    }
                    else if (response.body().getCode()==401)
                    {
                        dismissProgressDialog();
                        Toast.makeText(ChannelsActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<GetChannels> call, Throwable t) {
                dismissProgressDialog();

            }
        });


    }
}