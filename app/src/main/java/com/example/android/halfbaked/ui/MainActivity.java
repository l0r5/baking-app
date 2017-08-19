package com.example.android.halfbaked.ui;

import android.support.v4.app.LoaderManager;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Recipe;
import com.example.android.halfbaked.network.FetchAllRecipesTask;

public class MainActivity extends AppCompatActivity implements FetchAllRecipesTask.FetchAllRecipesCallback {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final int FETCH_ALL_RECIPES_LOADER_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        loadRecipeData();

        RecipeCollectionFragment recipeCollectionFragment = new RecipeCollectionFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_collection_container, recipeCollectionFragment)
                .commit();
    }

    private void loadRecipeData() {
        LoaderManager.LoaderCallbacks<Recipe[]> recipesCallback = new FetchAllRecipesTask(this, this);
        getSupportLoaderManager().initLoader(FETCH_ALL_RECIPES_LOADER_ID, null, recipesCallback);
    }

    @Override
    public void onFetchAllRecipesTaskCompleted(Recipe[] recipes) {
        Log.i(TAG, "Successful fetched!");
    }
}
