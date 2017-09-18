package com.example.android.bakingapp.ui.details;

import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Parcelable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.models.Step;
import com.google.android.exoplayer2.C;
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
import com.squareup.picasso.Picasso;

import static android.view.View.GONE;
import static com.example.android.bakingapp.ui.details.RecipeDetailActivity.DEVICE_CHECK;
import static com.example.android.bakingapp.ui.details.RecipeDetailActivity.STEP_BUNDLE;

public class StepDetailFragment extends Fragment {

    private static final String TAG = StepDetailFragment.class.getSimpleName();
    private static final String BUNDLE_EXOPLAYER_POSITION = "bundle_exoplayer_position";
    private Step mStep;
    private boolean mTwoPane;
    private SimpleExoPlayer mExoPlayer;
    private SimpleExoPlayerView mPlayerView;
    private Button mPreviousStepBtn;
    private Button mNextStepBtn;
    private long mExoPlayerPosition = C.TIME_UNSET;
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

        if (savedInstanceState != null) {
            mStep = savedInstanceState.getParcelable(STEP_BUNDLE);
            mTwoPane = savedInstanceState.getBoolean(DEVICE_CHECK);
            mExoPlayerPosition = savedInstanceState.getLong(BUNDLE_EXOPLAYER_POSITION);
        } else {
            mStep = getArguments().getParcelable(STEP_BUNDLE);
            mTwoPane = getArguments().getBoolean(DEVICE_CHECK);
        }

        TextView shortDescription = rootView.findViewById(R.id.tv_recipe_step_short_description);
        TextView description = rootView.findViewById(R.id.tv_recipe_step_description);
        ImageView thumbnail = rootView.findViewById(R.id.iv_step_thumbnail);
        mPlayerView = rootView.findViewById(R.id.pv_recipe_step_video);

        if (!mStep.getThumbnailUrl().equals("")) {
            try {
                Bitmap thumbnailPicture = mStep.getPictureFromThumbnailUrl();
                thumbnail.setImageBitmap(thumbnailPicture);
            } catch (Throwable e) {
                e.printStackTrace();
            }
        }

        initializeButtons(rootView);
        shortDescription.setText(mStep.getShortDescription());
        description.setText(mStep.getDescription());
        initializePlayer(Uri.parse(mStep.getVideoUrl()));

        return rootView;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(STEP_BUNDLE, mStep);
        outState.putBoolean(DEVICE_CHECK, mTwoPane);
        outState.putLong(BUNDLE_EXOPLAYER_POSITION, mExoPlayerPosition);
    }

    @Override
    public void onPause() {
        super.onPause();
        mExoPlayerPosition = mExoPlayer.getCurrentPosition();
        mExoPlayer.stop();
        mExoPlayer.release();
        mExoPlayer = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (mStep.getVideoUrl() != null)
            initializePlayer(Uri.parse(mStep.getVideoUrl()));
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

            // If the player was paused, restore it on the same position
            if (mExoPlayerPosition != C.TIME_UNSET)
                mExoPlayer.seekTo(mExoPlayerPosition);

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
