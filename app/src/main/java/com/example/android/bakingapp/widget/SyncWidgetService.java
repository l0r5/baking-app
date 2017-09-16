package com.example.android.bakingapp.widget;


import android.app.IntentService;
import android.content.Context;
import android.content.Intent;

import com.example.android.bakingapp.models.Ingredient;

import java.util.ArrayList;
import java.util.List;

public class SyncWidgetService extends IntentService {

    public static final String GET_INGREDIENTS_LIST = "GET_INGREDIENTS_LIST";
    public static final String ACTION_UPDATE_INGREDIENTS = "com.example.android.bakingapp.action.update_ingredients";

    public SyncWidgetService() {
        super("SyncWidgetService");
    }

    public static void startSyncWidgetService(Context context, List<Ingredient> ingredientsList) {
        Intent intent = new Intent(context, SyncWidgetService.class);
        intent.putParcelableArrayListExtra(GET_INGREDIENTS_LIST, (ArrayList<Ingredient>) ingredientsList);
        context.startService(intent);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        if (intent != null) {
            List<Ingredient> ingredientsList = intent.getExtras().getParcelableArrayList(GET_INGREDIENTS_LIST);
            handleActionUpdateBakingWidgets(ingredientsList);
        }
    }

    private void handleActionUpdateBakingWidgets(List<Ingredient> ingredientsList) {
        Intent intent = new Intent();
        intent.setAction(ACTION_UPDATE_INGREDIENTS);
        intent.putParcelableArrayListExtra(GET_INGREDIENTS_LIST, (ArrayList<Ingredient>) ingredientsList);
        sendBroadcast(intent);
    }
}
