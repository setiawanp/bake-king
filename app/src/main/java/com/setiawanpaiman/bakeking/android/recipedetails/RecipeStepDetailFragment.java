package com.setiawanpaiman.bakeking.android.recipedetails;

import android.annotation.SuppressLint;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;
import com.setiawanpaiman.bakeking.android.R;
import com.setiawanpaiman.bakeking.android.data.viewmodel.Step;
import com.squareup.picasso.Picasso;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeStepDetailFragment extends Fragment {

    private static final String EXTRA_STEP = RecipeStepDetailFragment.class.getName() + ".EXTRA_STEP";
    private static final String STATE_PLAY_WHEN_READY = RecipeStepDetailFragment.class.getName() + ".STATE_PLAY_WHEN_READY";
    private static final String STATE_PLAYBACK_POSITION = RecipeStepDetailFragment.class.getName() + ".STATE_PLAYBACK_POSITION";
    private static final String STATE_CURRENT_WINDOW = RecipeStepDetailFragment.class.getName() + ".STATE_CURRENT_WINDOW";

    @BindView(R.id.player_view)
    SimpleExoPlayerView playerView;

    @BindView(R.id.thumbnail)
    ImageView thumbnailView;

    @BindView(R.id.description)
    TextView descriptionText;

    private SimpleExoPlayer player;
    private Step mStep;

    private boolean mPlayWhenReady;
    private long mPlaybackPosition;
    private int mCurrentWindow;

    public static RecipeStepDetailFragment newInstance(final Step step) {
        RecipeStepDetailFragment fragment = new RecipeStepDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(EXTRA_STEP, step);
        fragment.setArguments(args);
        return fragment;
    }

    public RecipeStepDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mStep = getArguments().getParcelable(EXTRA_STEP);
        if (savedInstanceState != null) {
            mPlayWhenReady = savedInstanceState.getBoolean(STATE_PLAY_WHEN_READY);
            mPlaybackPosition = savedInstanceState.getLong(STATE_PLAYBACK_POSITION);
            mCurrentWindow = savedInstanceState.getInt(STATE_CURRENT_WINDOW);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.recipestep_detail, container, false);
        ButterKnife.bind(this, rootView);
        descriptionText.setText(mStep.getDescription());
        if (!TextUtils.isEmpty(mStep.getThumbnailURL())) {
            Picasso.with(getActivity())
                    .load(mStep.getThumbnailURL())
                    .into(thumbnailView);
            thumbnailView.setVisibility(View.VISIBLE);
        } else {
            thumbnailView.setVisibility(View.GONE);
        }
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            setupPlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        if ((Util.SDK_INT <= 23 || player == null)) {
            setupPlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putBoolean(STATE_PLAY_WHEN_READY, mPlayWhenReady);
        outState.putLong(STATE_PLAYBACK_POSITION, mPlaybackPosition);
        outState.putInt(STATE_CURRENT_WINDOW, mCurrentWindow);
    }

    private void setupPlayer() {
        if (!TextUtils.isEmpty(mStep.getVideoURL())) {
            initializePlayer(mStep.getVideoURL());
            playerView.setVisibility(View.VISIBLE);
        } else {
            playerView.setVisibility(View.GONE);
        }
    }

    private void initializePlayer(final String url) {
        player = ExoPlayerFactory.newSimpleInstance(
                new DefaultRenderersFactory(getContext()),
                new DefaultTrackSelector(), new DefaultLoadControl());

        playerView.setPlayer(player);

        player.setPlayWhenReady(mPlayWhenReady);
        player.seekTo(mCurrentWindow, mPlaybackPosition);

        Uri uri = Uri.parse(url);
        MediaSource mediaSource = buildMediaSource(uri);
        player.prepare(mediaSource, false, false);
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource(uri,
                new DefaultHttpDataSourceFactory("ua"),
                new DefaultExtractorsFactory(), null, null);
    }

    private void releasePlayer() {
        if (player != null) {
            mPlaybackPosition = player.getCurrentPosition();
            mCurrentWindow = player.getCurrentWindowIndex();
            mPlayWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        playerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
}
