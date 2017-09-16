package com.example.android.bakingapp.network;


import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public final class NetworkUtils {

    private static final String TAG = NetworkUtils.class.getSimpleName();
    private static final String BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/";
    static RecipeAPI mRecipeApi;

   public static RecipeAPI retrieveRecipes() {

       Gson gson = new GsonBuilder().create();
       OkHttpClient.Builder httpClientBuilder = new OkHttpClient.Builder();

       mRecipeApi = new Retrofit.Builder()
               .baseUrl(BASE_URL)
               .addConverterFactory(GsonConverterFactory.create(gson))
               .callFactory(httpClientBuilder.build())
               .build().create(RecipeAPI.class);

       Log.v(TAG, "Retrieving recipes from Url: " + BASE_URL + "baking.json");

       return mRecipeApi;
   }

}
