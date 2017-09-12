package com.example.android.bakingapp.network;


import android.support.v4.app.LoaderManager;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.content.Context;
import android.os.Bundle;

import com.example.android.bakingapp.data.DataFormatUtils;
import com.example.android.bakingapp.models.Recipe;

import java.net.URL;


public class FetchAllRecipesTask implements LoaderManager.LoaderCallbacks<Recipe[]> {

    private static final String TAG = FetchAllRecipesTask.class.getSimpleName();
    private FetchAllRecipesCallback mCallback;
    private Context mContext;

    public interface FetchAllRecipesCallback {
        void onFetchAllRecipesTaskCompleted(Recipe[] recipes);
    }

    public FetchAllRecipesTask(FetchAllRecipesCallback callback, Context context) {
        this.mCallback = callback;
        this.mContext = context;
    }

    @Override
    public Loader<Recipe[]> onCreateLoader(int i, Bundle bundle) {
        return new AsyncTaskLoader<Recipe[]>(mContext) {

            Recipe[] recipeData = null;

            @Override
            protected void onStartLoading() {
                if (recipeData != null) {
                    deliverResult(recipeData);
                } else {
                    forceLoad();
                }
            }

            @Override
            public Recipe[] loadInBackground() {
                URL recipeRequestUrl = NetworkUtils.buildRecipeCollectionUrl(mContext);

                try {
                    String jsonRecipeResponse = NetworkUtils.getResponseFromHttpUrl(recipeRequestUrl);
                    Recipe[] recipeData = DataFormatUtils.getRecipeObjectsFromJson(jsonRecipeResponse);
                    return recipeData;

                } catch (Exception e) {
                    e.printStackTrace();
                    return null;
                }
            }

            @Override
            public void deliverResult(Recipe[] data) {
                recipeData = data;
                super.deliverResult(recipeData);
            }
        };
    }

    @Override
    public void onLoadFinished(Loader<Recipe[]> loader, Recipe[] data) {
        mCallback.onFetchAllRecipesTaskCompleted(data);
    }

    @Override
    public void onLoaderReset(Loader<Recipe[]> loader) {

    }
}
