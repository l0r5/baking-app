package com.example.android.halfbaked.ui.details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Recipe;

public class RecipeDetailFragment extends Fragment {

    private Recipe mRecipeDetails;
    RecipeIngredientsAdapter mRecipeIngredientsAdapter;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        initializeIngredientRecyclerView(rootView);
        mRecipeIngredientsAdapter.setIngredientData(mRecipeDetails.getIngredients());

        return rootView;
    }

    private void initializeIngredientRecyclerView(View view) {
        RecyclerView recyclerViewIngredients = view.findViewById(R.id.rv_recipe_ingredients);
        recyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        mRecipeIngredientsAdapter = new RecipeIngredientsAdapter();
        recyclerViewIngredients.setAdapter(mRecipeIngredientsAdapter);
    }

    public void setRecipeDetails(Recipe recipeDetails) {
        mRecipeDetails = recipeDetails;
    }

}
