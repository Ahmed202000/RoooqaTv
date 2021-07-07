package com.roooqaTv.roooqatv.data.api;


import com.roooqaTv.roooqatv.data.model.getAllVideos.GetAllVideos;
import com.roooqaTv.roooqatv.data.model.getCategories.GetCategories;
import com.roooqaTv.roooqatv.data.model.getChannels.GetChannels;
import com.roooqaTv.roooqatv.data.model.getVideo.GetVideo;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface ApiServes {


    @POST("user/v1/categories")
    Call<GetCategories>getCate();

    @POST("user/v1/videos")
    @FormUrlEncoded
    Call<GetAllVideos>getAllVideos(@Field("channel_id")int channel_id);


    @POST("user/v1/channels")
    @FormUrlEncoded
    Call<GetChannels>getChannels(@Field("category_id")int category_id);

    @POST("user/v1/one/video")
    @FormUrlEncoded
    Call<GetVideo>getVideo(@Field("video_id")int video_id);


    @POST("user/v1/searchChannels")
    @FormUrlEncoded
    Call<GetCategories>searchChannels(@Field("text_search")String text_search);

}



