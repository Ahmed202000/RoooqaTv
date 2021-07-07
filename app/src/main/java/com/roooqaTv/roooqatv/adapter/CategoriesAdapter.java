package com.roooqaTv.roooqatv.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.roooqaTv.roooqatv.R;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategoriesDatum;
import com.roooqaTv.roooqatv.view.activity.AllVideosActivity;
import com.roooqaTv.roooqatv.view.activity.ChannelsActivity;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder> {
    private Context context;
    private Activity activity;
    private List<GetCategoriesDatum> getCategoriesData = new ArrayList<>();

    private ImageView logo;
    private TextView name;

    public CategoriesAdapter(Context context, List<GetCategoriesDatum> getCategoriesData) {
        this.context = context;
        this.getCategoriesData = getCategoriesData;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_categories,
                parent, false);

        logo=view.findViewById(R.id.item_categories_iv_logo);
        name=view.findViewById(R.id.item_categories_tv_name);


        return new ViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        setData(holder, position);
        setAction(holder, position);
    }

    private void setData(ViewHolder holder, int position) {

        if (getCategoriesData.get(position).getLogo() !=null)
        {
            Glide.with(context).load(getCategoriesData.get(position).getLogo()).into(logo);

        }
        else
        {
            logo.setImageResource(R.drawable.ic_logo);

        }

        name.setText(getCategoriesData.get(position).getNameAr());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, ChannelsActivity.class);
                intent.putExtra("category_id",getCategoriesData.get(position).getId());
                context.startActivity(intent);
            }
        });
    }

    private void setAction(ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return getCategoriesData.size();
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
