package com.example.android.halfbaked.ui.details;

import android.net.Uri;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.halfbaked.R;
import com.example.android.halfbaked.models.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import static android.view.View.GONE;
import static com.example.android.halfbaked.ui.details.RecipeDetailActivity.DEVICE_CHECK;
import static com.example.android.halfbaked.ui.details.RecipeDetailActivity.STEP_BUNDLE;

public class StepDetailFragment extends Fragment {

    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private Step mStep;
    private boolean mTwoPane;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Button mPreviousStepBtn;
    private Button mNextStepBtn;
    private Bundle mSavedState = null;

    public static StepDetailFragment newInstance() {

        Bundle args = new Bundle();

        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_detail, container, false);

        if(savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_BUNDLE);
            mTwoPane = savedInstanceState.getBoolean(DEVICE_CHECK);
        } else {
            mStep = getArguments().getParcelable(STEP_BUNDLE);
            mTwoPane = getArguments().getBoolean(DEVICE_CHECK);
        }

        TextView shortDescription = rootView.findViewById(R.id.tv_recipe_step_short_description);
        TextView description = rootView.findViewById(R.id.tv_recipe_step_description);
        mPlayerView = rootView.findViewById(R.id.pv_recipe_step_video);

        initializeButtons(rootView);
        shortDescription.setText(mStep.getShortDescription());
        description.setText(mStep.getDescription());
        initializePlayer(Uri.parse(mStep.mVideoUrl()));


        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_BUNDLE, mStep);
        outState.putBoolean(DEVICE_CHECK, mTwoPane);
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayer.stop();
    }

    private void initializePlayer(Uri mediaUri) {
        if (mExoPlayer == null) {
            // Create an instance of the ExoPlayer.
            TrackSelector trackSelector = new DefaultTrackSelector();
            LoadControl loadControl = new DefaultLoadControl();
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(getActivity(), trackSelector, loadControl);
            mPlayerView.setPlayer(mExoPlayer);

            // Prepare the MediaSource.
            String userAgent = Util.getUserAgent(getActivity(), "HalfBaked");
            MediaSource mediaSource = new ExtractorMediaSource(mediaUri, new DefaultDataSourceFactory(
                    getActivity(), userAgent), new DefaultExtractorsFactory(), null, null);
            mExoPlayer.prepare(mediaSource);
            mExoPlayer.setPlayWhenReady(true);
        }
    }

    private void initializeButtons(View view) {

        mPreviousStepBtn = view.findViewById(R.id.btn_previous_step);
        mNextStepBtn = view.findViewById(R.id.btn_next_step);

        mPreviousStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickPrevious(mStep);
            }
        });

        mNextStepBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onClickNext(mStep);
            }
        });

        if (mTwoPane) {
            mPreviousStepBtn.setVisibility(GONE);
            mNextStepBtn.setVisibility(GONE);
        } else {
            if (mStep.getId() == 0) {
                mPreviousStepBtn.setVisibility(GONE);
            }
            if (mStep.getId() == ((RecipeDetailActivity) getActivity()).getNumberOfRecipeSteps() - 1) {
                mNextStepBtn.setVisibility(GONE);
            }
        }
    }

    private void onClickPrevious(Step step) {
        Log.i(TAG, "Previous Button clicked.");
        ((RecipeDetailActivity) getActivity()).getPreviousStepDetail(step);
    }

    private void onClickNext(Step step) {
        Log.i(TAG, "Next Button clicked.");
        ((RecipeDetailActivity) getActivity()).getNextStepDetail(step);
    }

}
