package com.francesco.allmovies.API;

import android.util.Log;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static com.francesco.allmovies.Activity.MainActivity.TAG;

public class Client {

    public static final String BASE_URL = "http://api.themoviedb.org/3/";
    // OMDb public static final String BASE_URL = "http://www.omdbapi.com/?apikey=[f1765675]&";

    public static Retrofit retrofit = null;

    public static Retrofit getClient(){
        if (retrofit == null){
            retrofit = new Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        Log.d(TAG, "getClient: " + retrofit.toString());
        return retrofit;
    }

}
