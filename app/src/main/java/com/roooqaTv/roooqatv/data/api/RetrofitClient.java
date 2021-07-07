package com.roooqaTv.roooqatv.data.api;

import android.util.Log;

import com.roooqaTv.roooqatv.data.api.ApiServes;

import java.util.concurrent.TimeUnit;

import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;


public class RetrofitClient {

  private static final String TAG = "ahmed";

  private static Retrofit retrofit=null;
  private static String BASE_URL="https://roooqatvsx.com/api/";
  private static okhttp3.OkHttpClient okHttpClient;

  public static ApiServes getClient(){


    //TODO: understanding HttpLoggingInterceptor and networkInterceptor and implemnt it here
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(message -> Log.d(TAG, message));
    httpLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

    okhttp3.OkHttpClient innerClient = new okhttp3.OkHttpClient.Builder()
            .connectTimeout(1, TimeUnit.MINUTES) // connect timeout
            .writeTimeout(1, TimeUnit.MINUTES) // write timeout
            .readTimeout(1, TimeUnit.MINUTES) // read timeout
            .addInterceptor(httpLoggingInterceptor)
            .build();

    if (retrofit == null){
      retrofit=new Retrofit.Builder()
              .baseUrl(BASE_URL)
              //.addConverterFactory(ScalarsConverterFactory.create())
              .addConverterFactory(GsonConverterFactory.create())
              .client(innerClient)
              .build();
    }
    return retrofit.create(ApiServes.class);
  }




}

