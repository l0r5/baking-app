package com.example.android.bakingapp.widget;

import android.content.Context;
import android.content.Intent;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;

import java.util.List;

import static com.example.android.bakingapp.widget.BakingAppWidgetProvider.mIngredientsList;


public class GridWidgetService extends RemoteViewsService {

    List<Ingredient> mRemoteViewIngredientsList;

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        return new GridRemoteViewsFactory(this.getApplicationContext());
    }


    class GridRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

        Context mContext;

        public GridRemoteViewsFactory(Context applicationContext) {
            mContext = applicationContext;
        }


        @Override
        public void onCreate() {

        }

        @Override
        public void onDataSetChanged() {
            mRemoteViewIngredientsList = mIngredientsList;
        }

        @Override
        public void onDestroy() {

        }

        @Override
        public int getCount() {
            return mRemoteViewIngredientsList.size();
        }

        @Override
        public RemoteViews getViewAt(int position) {
            RemoteViews views = new RemoteViews(mContext.getPackageName(), R.layout.widget_grid_view_item);

            views.setTextViewText(R.id.tv_ingredient_name, mRemoteViewIngredientsList.get(position).getIngredient());

            Intent fillInIntent = new Intent();
            //fillInIntent.putExtras(extras);
            views.setOnClickFillInIntent(R.id.tv_ingredient_name, fillInIntent);

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
        public long getItemId(int i) {
            return i;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }
    }
}
