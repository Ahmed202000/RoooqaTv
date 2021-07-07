package com.roooqaTv.roooqatv.view.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.adapter.CategoriesAdapter;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategories;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategoriesDatum;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.roooqaTv.roooqatv.data.api.RetrofitClient.getClient;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.dismissProgressDialog;
import static com.roooqaTv.roooqatv.helpar.HelperMethod.showProgressDialog;

public class SearchActivity extends AppCompatActivity {


    RecyclerView rvSearch;
    EditText tvSearch;
    private List<GetCategoriesDatum> getCategoriesData = new ArrayList<>();
    CategoriesAdapter categoriesAdapter;
    GridLayoutManager gridLayoutManager;
    private TextView tv;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        rvSearch=findViewById(R.id.activity_search_rv_search);
        tvSearch=findViewById(R.id.activity_search_tv_search);

        TextWatcher search=new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count)
            {

             search();
            }


            @Override
            public void afterTextChanged(Editable s) {

            }
        };
        tvSearch.addTextChangedListener(search);



    }


    private void search()
    {
        String Search=tvSearch.getText().toString();

        gridLayoutManager=new GridLayoutManager(this,2);
        rvSearch.setLayoutManager(gridLayoutManager);

        showProgressDialog(this,"Pleas Wait");
        getClient().searchChannels(Search).enqueue(new Callback<GetCategories>() {
            @Override
            public void onResponse(Call<GetCategories> call, Response<GetCategories> response)
            {
                getCategoriesData.clear();

                dismissProgressDialog();
                try
                {
                    if (response.body().getCode()==200) {

                        getCategoriesData.addAll(response.body().getData());
                        categoriesAdapter=new CategoriesAdapter(SearchActivity.this,getCategoriesData);
                        rvSearch.setAdapter(categoriesAdapter);
                        categoriesAdapter.notifyDataSetChanged();

                        if(getCategoriesData.size()==0)
                        {
                            tv.setVisibility(View.VISIBLE);
                        }
                        else
                        if(getCategoriesData.size()!=0)
                        {
                            tv.setVisibility(View.GONE);
                        }


                    }
                    else if (response.body().getCode()==401)
                    {
                        dismissProgressDialog();
                        Toast.makeText(SearchActivity.this, response.body().getStatus(), Toast.LENGTH_SHORT).show();
                    }
                }
                catch (Exception e)
                {
                    dismissProgressDialog();

                }
            }

            @Override
            public void onFailure(Call<GetCategories> call, Throwable t) {
                dismissProgressDialog();

            }
        });


    }

}