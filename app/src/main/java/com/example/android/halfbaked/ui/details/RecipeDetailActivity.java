package com.example.android.halfbaked.ui.details;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Recipe;

import static com.example.android.halfbaked.ui.collection.RecipeCollectionFragment.INTENT_RECIPE_DETAIL_KEY;

public class RecipeDetailActivity extends AppCompatActivity{

    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private RecipeDetailFragment mRecipeDetailFragment;
    private Recipe mRecipeDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        mRecipeDetailFragment = new RecipeDetailFragment();
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.recipe_detail_container, mRecipeDetailFragment)
                .commit();

        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.getExtras() != null) {
            mRecipeDetails = intentThatStartedThisActivity.getParcelableExtra(INTENT_RECIPE_DETAIL_KEY);
            setTitle(mRecipeDetails.getName());
            mRecipeDetailFragment.setRecipeDetails(mRecipeDetails);
        }
    }
}
