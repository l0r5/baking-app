package com.example.android.bakingapp.widget;


import android.app.IntentService;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.Nullable;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;


public class SyncWidgetService extends IntentService {

    public static final String ACTION_UPDATE_RECIPE = "com.example.android.bakingapp.action.update_recipe";
    public static final String EXTRA_RECIPE = "extra_recipe";

    public SyncWidgetService() {
        super("SyncWidgetService");
    }

    public static void startActionUpdateRecipe(Context context, Recipe recipe) {
        Intent intent = new Intent(context, SyncWidgetService.class);
        intent.setAction(ACTION_UPDATE_RECIPE);
        intent.putExtra(EXTRA_RECIPE, recipe);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        if (intent != null) {
            final String action = intent.getAction();
            if (ACTION_UPDATE_RECIPE.equals(action)) {
                Recipe recipe = intent.getExtras().getParcelable(EXTRA_RECIPE);
                handleActionUpdateRecipe(recipe);
            }
        }
    }

    private void handleActionUpdateRecipe(Recipe recipe) {
        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
        int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidgetProvider.class));
        BakingAppWidgetProvider.updateBakingAppWidgets(this, appWidgetManager, recipe, appWidgetIds);
        appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_list_view);
    }

}
