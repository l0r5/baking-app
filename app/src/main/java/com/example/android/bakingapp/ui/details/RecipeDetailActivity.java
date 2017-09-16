package com.example.android.bakingapp.ui.details;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.models.Step;

import static com.example.android.bakingapp.ui.collection.RecipeCollectionFragment.INTENT_RECIPE_DETAIL_KEY;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String STEP_BUNDLE = "step_bundle";
    public static final String RECIPE_BUNDLE = "recipe_bundle";
    public static final String DEVICE_CHECK = "device_check";
    private static final String TAG = RecipeDetailActivity.class.getSimpleName();
    private RecipeDetailFragment mRecipeDetailFragment;
    private StepDetailFragment mStepDetailFragment;

    private Recipe mRecipeDetails;
    private Step mSelectedStep;
    private boolean mTwoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        // Get Intent from previous activity
        Intent intentThatStartedThisActivity = getIntent();
        if (intentThatStartedThisActivity.getExtras() != null) {
            mRecipeDetails = intentThatStartedThisActivity.getParcelableExtra(INTENT_RECIPE_DETAIL_KEY);
            setTitle(mRecipeDetails.getName());
        }

        // if no steps selected yet, start with step 1
        if (savedInstanceState == null) {
            mSelectedStep = mRecipeDetails.getSteps().get(0);

            if (findViewById(R.id.recipe_detail_step_container) != null) {
                // Tablet mode
                mTwoPane = true;
                Log.i(TAG, "Client device is a Tablet.");

                // Recipe Detail Fragment (left side)
                mRecipeDetailFragment = RecipeDetailFragment.newInstance();
                Bundle bundleRecipeDetail = new Bundle();
                bundleRecipeDetail.putParcelable(RECIPE_BUNDLE, mRecipeDetails);
                bundleRecipeDetail.putBoolean(DEVICE_CHECK, mTwoPane);
                mRecipeDetailFragment.setArguments(bundleRecipeDetail);

                // Step Detail Fragment (right side)
                mStepDetailFragment = StepDetailFragment.newInstance();
                Bundle bundleStepDetail = new Bundle();
                bundleStepDetail.putParcelable(STEP_BUNDLE, mSelectedStep);
                bundleStepDetail.putBoolean(DEVICE_CHECK, mTwoPane);
                mStepDetailFragment.setArguments(bundleStepDetail);

                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_container, mRecipeDetailFragment)
                        .commit();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_step_container, mStepDetailFragment)
                        .commit();

            } else {
                // Smartphone mode
                mTwoPane = false;
                Log.i(TAG, "Client device is a Smartphone");

                // Recipe Detail Fragment
                mRecipeDetailFragment = RecipeDetailFragment.newInstance();
                Bundle bundleRecipeDetail = new Bundle();
                bundleRecipeDetail.putParcelable(RECIPE_BUNDLE, mRecipeDetails);
                bundleRecipeDetail.putBoolean(DEVICE_CHECK, mTwoPane);
                mRecipeDetailFragment.setArguments(bundleRecipeDetail);
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.recipe_detail_container, mRecipeDetailFragment)
                        .commit();

            }
        }
    }

    public void changeStepDetailFragment(Fragment fragment, @Nullable Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void changeStepDetailFragmentTwoPane(Fragment fragment, @Nullable Bundle bundle) {

        if (bundle != null) {
            fragment.setArguments(bundle);
        }

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_step_container, fragment)
                .addToBackStack(null)
                .commit();
    }

    public void setStepDetails(Step stepDetails) {
        mSelectedStep = stepDetails;
    }

    public int getNumberOfRecipeSteps() {
        return mRecipeDetails.getSteps().size();
    }

    public void getPreviousStepDetail(Step currentStep) {
        Bundle bundle = new Bundle();
        Step previousStep = mRecipeDetails.getSteps().get(currentStep.getId() - 1);
        bundle.putParcelable(STEP_BUNDLE, previousStep);
        changeStepDetailFragment(StepDetailFragment.newInstance(), bundle);
    }

    public void getNextStepDetail(Step currentStep) {
        Bundle bundle = new Bundle();
        Step nextStep = mRecipeDetails.getSteps().get(currentStep.getId() + 1);
        bundle.putParcelable(STEP_BUNDLE, nextStep);
        changeStepDetailFragment(StepDetailFragment.newInstance(), bundle);
    }
}
