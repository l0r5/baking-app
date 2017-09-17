package com.example.android.bakingapp.ui.collection;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.ui.details.RecipeDetailActivity;
import com.example.android.bakingapp.widget.SyncWidgetService;

import java.util.ArrayList;


public class RecipeCollectionFragment extends Fragment implements RecipeCollectionAdapter.RecipeCollectionAdapterOnClickHandler {

    public static final String INTENT_RECIPE_DETAIL_KEY = "recipeDetailKey";
    private ArrayList<Recipe> mAllRecipes;
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

    public void setAllRecipes(ArrayList<Recipe> allRecipes) {
        mAllRecipes = allRecipes;
        mRecipeCollectionAdapter.setRecipeData(mAllRecipes);

        //update widget
        SyncWidgetService.startActionUpdateRecipe(getContext(), mAllRecipes);
    }

    @Override
    public void onClick(Recipe recipeDetails) {
        Intent intentToStartActivity = new Intent(getActivity(), RecipeDetailActivity.class);
        intentToStartActivity.putExtra(INTENT_RECIPE_DETAIL_KEY, recipeDetails);
        startActivity(intentToStartActivity);
    }
}
