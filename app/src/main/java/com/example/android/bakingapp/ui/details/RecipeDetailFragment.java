package com.example.android.bakingapp.ui.details;


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

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;
import com.example.android.bakingapp.widget.SyncWidgetService;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.example.android.bakingapp.ui.details.RecipeDetailActivity.DEVICE_CHECK;
import static com.example.android.bakingapp.ui.details.RecipeDetailActivity.RECIPE_BUNDLE;
import static com.example.android.bakingapp.ui.details.RecipeDetailActivity.STEP_BUNDLE;

public class RecipeDetailFragment extends Fragment implements RecipeStepsAdapter.RecipeStepsAdapterOnClickHandler {

    private Recipe mRecipeDetails;
    private RecyclerView mRecyclerViewIngredients;
    private boolean mIngredientsExpanded = false;
    private boolean mTwoPane;

    public static RecipeDetailFragment newInstance() {

        Bundle args = new Bundle();

        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        mRecipeDetails = getArguments().getParcelable(RECIPE_BUNDLE);
        mTwoPane = getArguments().getBoolean(DEVICE_CHECK);

        initializeIngredientsRecyclerView(rootView, mRecipeDetails.getIngredients());
        initializeStepsRecyclerView(rootView, mRecipeDetails.getSteps());

        return rootView;
    }

    private void initializeIngredientsRecyclerView(View view, List<Ingredient> ingredients) {
        // setting up recycler view for the ingredients
        mRecyclerViewIngredients = view.findViewById(R.id.rv_recipe_ingredients);
        mRecyclerViewIngredients.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeIngredientsAdapter recipeIngredientsAdapter = new RecipeIngredientsAdapter(ingredients);
        mRecyclerViewIngredients.setAdapter(recipeIngredientsAdapter);
        // make the card view collapsing (due to space reasons)
        initializeCollapsingIngredients(view);
    }

    private void initializeStepsRecyclerView(View view, List<Step> steps) {
        // setting up recycler view for the steps
        RecyclerView recyclerViewSteps = view.findViewById(R.id.rv_recipe_steps);
        recyclerViewSteps.setLayoutManager(new LinearLayoutManager(getContext()));
        RecipeStepsAdapter recipeStepsAdapter = new RecipeStepsAdapter(getContext(), steps, this);
        recyclerViewSteps.setAdapter(recipeStepsAdapter);
    }

    private void initializeCollapsingIngredients(View view) {
        RelativeLayout ingredientsCollapsingLayout = view.findViewById(R.id.rl_recipe_ingredients_collapse);
        final ImageView collapseArrow = view.findViewById(R.id.iv_collapse_arrow);
        ingredientsCollapsingLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mIngredientsExpanded) {
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

    @Override
    public void onClick(Step stepDetails) {

        Bundle bundle = new Bundle();

        if (mTwoPane) {
            bundle.putParcelable(STEP_BUNDLE, stepDetails);
            bundle.putBoolean(DEVICE_CHECK, mTwoPane);
            ((RecipeDetailActivity) getActivity()).changeStepDetailFragmentTwoPane(StepDetailFragment.newInstance(), bundle);
        } else {
            bundle.putParcelable(STEP_BUNDLE, stepDetails);
            ((RecipeDetailActivity) getActivity()).changeStepDetailFragment(StepDetailFragment.newInstance(), bundle);
        }
    }
}
