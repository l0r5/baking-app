package com.example.android.bakingapp.ui.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Ingredient;

import java.util.List;


public class RecipeIngredientsAdapter extends RecyclerView.Adapter<RecipeIngredientsAdapter.RecipeIngredientsAdapterViewHolder> {

    private static final String TAG = RecipeIngredientsAdapter.class.getSimpleName();
    private List<Ingredient> mIngredients;

    public RecipeIngredientsAdapter(List<Ingredient> ingredients) {
        mIngredients = ingredients;
    }

    public class RecipeIngredientsAdapterViewHolder extends RecyclerView.ViewHolder {

        private final TextView mIngredientText;

        public RecipeIngredientsAdapterViewHolder(View view) {
            super(view);
            mIngredientText = view.findViewById(R.id.tv_recipe_ingredients_text);
        }
    }

    @Override
    public RecipeIngredientsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_ingredients_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeIngredientsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeIngredientsAdapterViewHolder holder, int position) {
        double quantity = mIngredients.get(position).getQuantity();
        String measure = mIngredients.get(position).getMeasure();
        String ingredient = mIngredients.get(position).getIngredient();
        String completeIngredientString = "- " + ingredient + " (" + String.valueOf(quantity) + " " + measure + ")";
        holder.mIngredientText.setText(completeIngredientString);
    }

    @Override
    public int getItemCount() {
        if (null == mIngredients)
            return 0;
        return mIngredients.size();
    }

}
