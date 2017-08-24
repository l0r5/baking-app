package com.example.android.halfbaked.ui.details;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Step;


public class RecipeStepsAdapter extends RecyclerView.Adapter<RecipeStepsAdapter.RecipeStepsAdapterViewHolder> {

    private static final String TAG = RecipeStepsAdapter.class.getSimpleName();
    private Step[] mSteps;
    private RecipeStepsAdapterOnClickHandler mClickHandler;

    public interface RecipeStepsAdapterOnClickHandler {
        void onClick(Step stepDetails);
    }

    public RecipeStepsAdapter(Step[] steps, RecipeStepsAdapterOnClickHandler clickHandler) {
        mSteps = steps;
        mClickHandler = clickHandler;
    }

    public class RecipeStepsAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView mStepShortDescription;

        public RecipeStepsAdapterViewHolder(View view) {
            super(view);
            mStepShortDescription = view.findViewById(R.id.tv_recipe_step_short_description);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int adapterPosition = getAdapterPosition();
            Step step = mSteps[adapterPosition];
            Log.i(TAG, step.getShortDescription() + " clicked.");
            mClickHandler.onClick(step);
        }
    }

    @Override
    public RecipeStepsAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_step_item;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipeStepsAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeStepsAdapterViewHolder holder, int position) {
        String completeStepString = mSteps[position].getId() + ". " + mSteps[position].getShortDescription();
        holder.mStepShortDescription.setText(completeStepString);
    }

    @Override
    public int getItemCount() {
        if (null == mSteps)
            return 0;
        return mSteps.length;
    }

}
