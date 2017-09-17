package com.example.android.bakingapp.ui.collection;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.network.NetworkUtils;
import com.example.android.bakingapp.network.RecipeAPI;
import com.example.android.bakingapp.widget.SyncWidgetService;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RecipeCollectionActivity extends AppCompatActivity {

    private static final String TAG = RecipeCollectionActivity.class.getSimpleName();
    private RecipeCollectionFragment mRecipeCollectionFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_collection);

        loadRecipeData();
        mRecipeCollectionFragment = new RecipeCollectionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_collection_container, mRecipeCollectionFragment)
                .commit();
    }

    private void loadRecipeData() {

        RecipeAPI recipeAPI = NetworkUtils.retrieveRecipes();
        Call<ArrayList<Recipe>> call = recipeAPI.getRecipes();

        call.enqueue(new Callback<ArrayList<Recipe>>() {
            @Override
            public void onResponse(Call<ArrayList<Recipe>> call, Response<ArrayList<Recipe>> response) {
                Integer statusCode = response.code();
                Log.v("Http status code: ", statusCode.toString());

                ArrayList<Recipe> retrievedRecipeData = response.body();
                if (retrievedRecipeData != null) {
                    Log.i(TAG, retrievedRecipeData.toString());
                    mRecipeCollectionFragment.setAllRecipes(retrievedRecipeData);
                    //update widget
                    SyncWidgetService.startActionUpdateRecipe(getApplicationContext(), retrievedRecipeData);
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Recipe>> call, Throwable t) {
                Log.v("Http fail: ", t.getMessage());
            }
        });
    }
}
