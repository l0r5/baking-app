package com.example.android.halfbaked.ui.details;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Ingredient;
import com.example.android.halfbaked.models.Recipe;
import com.example.android.halfbaked.models.Step;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;

public class RecipeDetailFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsAdapterOnClickHandler {

    private Recipe mRecipeDetails;
    RecyclerView mRecyclerViewIngredients;
    private boolean mIngredientsExpanded = false;

    public RecipeDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        if(mRecipeDetails != null) {
            initializeIngredientsRecyclerView(rootView, mRecipeDetails.getIngredients());
            initializeStepsRecyclerView(rootView, mRecipeDetails.getSteps());
        }

        return rootView;
    }

    private void initializeIngredientsRecyclerView(View view, Ingredient[] ingredients) {
        // setting up recycler view for the ingredients
        mRecyclerViewIngredients = view.findViewById(R.id.rv_recipe_ingredients);
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter(ingredients);
        mRecyclerViewIngredients.setAdapter(recipeIngredientsAdapter);
        // make the card view collapsing (due to space reasons)
        initializeCollapsingIngredients(view);
    }

    private void initializeStepsRecyclerView(View view, Step[] steps) {
        // setting up recycler view for the steps
        RecyclerView recyclerViewSteps = view.findViewById(R.id.rv_recipe_steps);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(steps, this);
        recyclerViewSteps.setAdapter(recipeStepsAdapter);
    }

    private void initializeCollapsingIngredients(View view) {
        RelativeLayout ingredientsCollapsingLayout = view.findViewById(R.id.rl_recipe_ingredients_collapse);
        final ImageView collapseArrow = view.findViewById(R.id.iv_collapse_arrow);
        ingredientsCollapsingLayout.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View view) {
                if(mIngredientsExpanded) {
                    mIngredientsExpanded = false;
                    collapseArrow.setImageResource(R.drawable.ic_keyboard_arrow_down);
                    mRecyclerViewIngredients.setVisibility(GONE);
                } else {
                    mIngredientsExpanded = true;
                    collapseArrow.setImageResource(R.drawable.ic_keyboard_arrow_up);
                    mRecyclerViewIngredients.setVisibility(VISIBLE);
                }
            }
        });
    }

    public void setRecipeDetails(Recipe recipeDetails) {
        mRecipeDetails = recipeDetails;
    }

    @Override
    public void onClick(Step stepDetails) {

    }
}
