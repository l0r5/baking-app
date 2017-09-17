package com.example.android.bakingapp.widget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;

import java.util.ArrayList;


public class SyncWidgetService extends IntentService {

    public static final String ACTION_UPDATE_RECIPES = "com.example.android.bakingapp.action.update_recipes";
    public static final String ACTION_NEXT_RECIPE = "com.example.android.bakingapp.action.next_recipe";
    public static final String EXTRA_CURRENT_RECIPE_POSITION = "extra_current_recipe_position";
    public static final String EXTRA_RECIPES = "extra_recipes";

    public SyncWidgetService() {
        super("SyncWidgetService");
    }

    private ArrayList<Recipe> mRecipes;
    private int mCurrentRecipePosition;

    public static void startActionUpdateRecipe(Context context, ArrayList<Recipe> recipes) {
        Intent intent = new Intent(context, SyncWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPES);
        intent.putParcelableArrayListExtra(EXTRA_RECIPES, recipes);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPES.equals(action)) {
                mRecipes = intent.getExtras().getParcelableArrayList(EXTRA_RECIPES);
                mCurrentRecipePosition = 0;
                handleActionUpdateRecipe();

            } else if (ACTION_NEXT_RECIPE.equals(action)) {
                mRecipes = intent.getExtras().getParcelableArrayList(EXTRA_RECIPES);
                mCurrentRecipePosition = intent.getExtras().getInt(EXTRA_CURRENT_RECIPE_POSITION);
                mCurrentRecipePosition++;
                if (mCurrentRecipePosition >= mRecipes.size()) {
                    mCurrentRecipePosition = 0;
                }
                handleActionUpdateRecipe();
            }
        }
    }

    private void handleActionUpdateRecipe() {

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateBakingAppWidgets(this, appWidgetManager, mRecipes.get(mCurrentRecipePosition), mRecipes, mCurrentRecipePosition, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

}
