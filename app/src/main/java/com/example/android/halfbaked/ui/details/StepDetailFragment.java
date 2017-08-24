package com.example.android.halfbaked.ui.details;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Step;

import static com.example.android.halfbaked.ui.details.RecipeDetailActivity.STEP_BUNDLE;

public class StepDetailFragment extends Fragment {

    Step mStep;

    public StepDetailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        mStep = getArguments().getParcelable(STEP_BUNDLE);

        TextView shortDescription = rootView.findViewById(R.id.tv_recipe_step_short_description);
        TextView description = rootView.findViewById(R.id.tv_recipe_step_description);

        shortDescription.setText(mStep.getShortDescription());
        description.setText(mStep.getDescription());

        return rootView;
    }

}
