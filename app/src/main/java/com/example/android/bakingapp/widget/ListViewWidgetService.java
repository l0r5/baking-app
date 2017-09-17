package com.example.android.bakingapp.widget;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;
import com.example.android.bakingapp.models.Recipe;

import static com.example.android.bakingapp.widget.BakingAppWidgetProvider.BUNDLE_RECIPE;
import static com.example.android.bakingapp.widget.BakingAppWidgetProvider.EXTRA_RECIPE_BUNDLE;


public class ListViewWidgetService extends RemoteViewsService {


    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        if (intent != null && intent.hasExtra(EXTRA_RECIPE_BUNDLE)) {
            Bundle bundle = intent.getExtras().getBundle(EXTRA_RECIPE_BUNDLE);
            if (bundle != null) {
                Recipe recipe = bundle.getParcelable(BUNDLE_RECIPE);
                return new ListViewRemoteViewsFactory(this.getApplicationContext(), recipe);
            } else {
                return new ListViewRemoteViewsFactory(this.getApplicationContext());
            }
        } else {
            return new ListViewRemoteViewsFactory(this.getApplicationContext());
        }
    }

    class ListViewRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;
        Recipe mRecipe;

        public ListViewRemoteViewsFactory(Context context) {
            mContext = context;
        }

        public ListViewRemoteViewsFactory(Context context, Recipe recipe) {
            mContext = context;
            mRecipe = recipe;
        }

        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {

        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            if (mRecipe == null)
                return 0;
            return mRecipe.getIngredients().size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            if (mRecipe == null) return null;

            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_list_view_item);
            Ingredient ingredient = mRecipe.getIngredients().get(position);
            String ingredientString = String.valueOf(ingredient.getQuantity()) + ingredient.getMeasure() + " " + ingredient.getIngredient();
            views.setTextViewText(R.id.tv_widget_list_view_item, ingredientString);

            return views;
        }

        @Override
        public RemoteViews getLoadingView() {
            return null;
        }

        @Override
        public int getViewTypeCount() {
            return 1;
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public boolean hasStableIds() {
            return true;
        }
    }

}