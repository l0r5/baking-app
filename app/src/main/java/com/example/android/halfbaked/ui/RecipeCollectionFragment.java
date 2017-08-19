package com.example.android.halfbaked.ui;

import android.content.res.Configuration;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Recipe;


public class RecipeCollectionFragment extends Fragment implements RecipeCollectionAdapter.RecipeCollectionAdapterOnClickHandler {

    private Recipe[] mAllRecipes;
    RecipeCollectionAdapter mRecipeCollectionAdapter;

    public RecipeCollectionFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_collection, container, false);

        initializeRecyclerView(rootView);

        return rootView;
    }

    private void initializeRecyclerView(View view) {
        RecyclerView recyclerViewRecipeCollection = view.findViewById(R.id.rv_recipe_collection);
        recyclerViewRecipeCollection.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeCollectionAdapter = new RecipeCollectionAdapter(this);
        recyclerViewRecipeCollection.setAdapter(mRecipeCollectionAdapter);

    }

    public void setAllRecipes(Recipe[] allRecipes) {
        mAllRecipes = allRecipes;
        mRecipeCollectionAdapter.setRecipeData(mAllRecipes);
    }
}
