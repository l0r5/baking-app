package com.example.android.bakingapp.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.RemoteViews;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Recipe;
import com.example.android.bakingapp.ui.collection.RecipeCollectionActivity;


public class BakingAppWidgetProvider extends AppWidgetProvider {

    public static final String BUNDLE_RECIPE = "bundle_recipe";
    public static final String EXTRA_RECIPE_BUNDLE = "extra_recipe_bundle";

    static void updateAppWidget(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int appWidgetId) {
        RemoteViews views = getIngredientsListRemoteView(context, recipe);
        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        super.onUpdate(context, appWidgetManager, appWidgetIds);
    }

    public static void updateBakingAppWidgets(Context context, AppWidgetManager appWidgetManager, Recipe recipe, int[] appWidgetIds) {
        for (int appWidgetId : appWidgetIds) {
            updateAppWidget(context, appWidgetManager, recipe, appWidgetId);
        }
    }

    private static RemoteViews getIngredientsListRemoteView(Context context, Recipe recipe) {
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);

        // Set widget content
        views.setTextViewText(R.id.tv_recipe_name, recipe.getName());

        // Set ListViewWidgetService intent to act as the adapter for the ListView
        Intent intent = new Intent(context, ListViewWidgetService.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(BUNDLE_RECIPE, recipe);
        intent.putExtra(EXTRA_RECIPE_BUNDLE, bundle);
        views.setRemoteAdapter(R.id.widget_list_view, intent);

        // Starts the app when clicked
        Intent appIntent = new Intent(context, RecipeCollectionActivity.class);
        PendingIntent appPendingIntent = PendingIntent.getActivity(context, 0, appIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        views.setPendingIntentTemplate(R.id.widgetLayoutMain, appPendingIntent);

        // Handle when no recipe is selected
        views.setEmptyView(R.id.widget_list_view, R.id.empty_view);

        return views;
    }

}

