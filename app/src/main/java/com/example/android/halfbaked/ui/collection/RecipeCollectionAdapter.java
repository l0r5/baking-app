package com.example.android.halfbaked.ui.collection;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Recipe;
import com.squareup.picasso.Picasso;

public class RecipeCollectionAdapter extends RecyclerView.Adapter<RecipeCollectionAdapter.RecipeCollectionAdapterViewHolder> {

    private static final String TAG = RecipeCollectionAdapter.class.getSimpleName();
    private Recipe[] mAllRecipes;
    private RecipeCollectionAdapterOnClickHandler mClickHandler;

    public interface RecipeCollectionAdapterOnClickHandler {
        void onClick(Recipe recipeDetails);
    }

    public RecipeCollectionAdapter(RecipeCollectionAdapterOnClickHandler clickHandler) {
        mClickHandler = clickHandler;
    }

    public class RecipeCollectionAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mRecipeName;
        private final TextView mRecipeServings;
        private final ImageView mRecipeImage;

        public RecipeCollectionAdapterViewHolder(View view) {
            super(view);
            mRecipeName = view.findViewById(R.id.tv_recipe_name);
            mRecipeServings = view.findViewById(R.id.tv_recipe_servings_number);
            mRecipeImage = view.findViewById(R.id.iv_recipe_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mAllRecipes[adapterPosition];
            Log.i(TAG, recipe.getName() + " clicked.");
            mClickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipeCollectionAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_collection_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeCollectionAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeCollectionAdapterViewHolder holder, int position) {
        String recipeName = mAllRecipes[position].getName();
        int recipeServings = mAllRecipes[position].getServings();
        String recipeImageUrl = mAllRecipes[position].getImageUrl();
        if(recipeImageUrl == null || recipeImageUrl.equals("")) {
            Picasso.with(holder.mRecipeImage.getContext()).load(R.drawable.default_recipe_image).into(holder.mRecipeImage);
        } else {
            Picasso.with(holder.mRecipeImage.getContext()).load(recipeImageUrl).error(holder.mRecipeImage.getContext().getResources().getDrawable(R.drawable.default_recipe_image)).into(holder.mRecipeImage);
        }
        holder.mRecipeName.setText(recipeName);
        holder.mRecipeServings.setText(String.valueOf(recipeServings));
    }

    @Override
    public int getItemCount() {
        if (null == mAllRecipes)
            return 0;
        return mAllRecipes.length;
    }

    public void setRecipeData(Recipe[] recipeData) {
        mAllRecipes = recipeData;
        notifyDataSetChanged();
    }
}
