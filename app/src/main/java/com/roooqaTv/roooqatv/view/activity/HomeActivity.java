package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.fivemin.chief.nonetworklibrary.networkBroadcast.NoNet;
import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.adapter.CategoriesAdapter;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategories;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategoriesDatum;
import com.roooqaTv.roooqatv.helpar.HelperMethod;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roooqaTv.roooqatv.data.api.RetrofitClient.getClient;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class HomeActivity extends AppCompatActivity {


    private NoNet mNoNet;
    private FragmentManager fm;

    RecyclerView rvCategories;

    private List<GetCategoriesDatum> getCategoriesData = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;
    GridLayoutManager gridLayoutManager;
    private TextView tv;
    private ImageView search,favorite;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        fm = getSupportFragmentManager();
        mNoNet = new NoNet();
        mNoNet.initNoNet(this, fm);


        tv=findViewById(R.id.tv_no);
        search=findViewById(R.id.home_activity_iv_search);
        favorite=findViewById(R.id.home_activity_iv_favorite);

        rvCategories=findViewById(R.id.home_activity_rv_cat);

        getCategory();

        if(getCategoriesData.size()==0)
        {
            tv.setVisibility(View.VISIBLE);
        }


        search.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,SearchActivity.class));
            }
        });


        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivity.this,FavoriteActivity.class));
            }
        });
    }


    private void getCategory()
    {
        gridLayoutManager=new GridLayoutManager(this,2);
        rvCategories.setLayoutManager(gridLayoutManager);

        showProgressDialog(this,"Pleas Wait");
        getClient().getCate().enqueue(new Callback<GetCategories>() {
            @Override
            public void onResponse(Call<GetCategories> call, Response<GetCategories> response)
            {
                dismissProgressDialog();
                try
                {
                    if (response.body().getCode()==200) {

                        getCategoriesData.addAll(response.body().getData());
                        categoriesAdapter=new CategoriesAdapter(HomeActivity.this,getCategoriesData);
                        rvCategories.setAdapter(categoriesAdapter);
                        categoriesAdapter.notifyDataSetChanged();

                        if(getCategoriesData.size()!=0)
                        {
                            tv.setVisibility(View.GONE);
                        }


                    }
                    else if (response.body().getCode()==401)
                    {
                        dismissProgressDialog();
                        Toast.makeText(HomeActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {

                }
            }

            @Override
            public void onFailure(Call<GetCategories> call, Throwable t) {
                dismissProgressDialog();

            }
        });


    }

    @Override
    protected void onResume() {
        mNoNet.RegisterNoNet();
        super.onResume();
    }

    @Override
    protected void onPause() {
        mNoNet.unRegisterNoNet();
        super.onPause();
    }
}