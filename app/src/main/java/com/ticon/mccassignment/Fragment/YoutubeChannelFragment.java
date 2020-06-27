package com.ticon.mccassignment.Fragment;

import android.content.Context;
import android.content.pm.ActivityInfo;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.ticon.mccassignment.Interface.OnFullScreenChangeListener;
import com.ticon.mccassignment.MainActivity;
import com.ticon.mccassignment.R;
import com.ticon.mccassignment.Utils.Utils;


public class YoutubeChannelFragment extends Fragment implements
        YouTubePlayer.OnInitializedListener,
        YouTubePlayer.PlayerStateChangeListener,
        YouTubePlayer.PlaybackEventListener,
        YouTubePlayer.OnFullscreenListener,
        OnFullScreenChangeListener {
    String YOUTUBE_VIDEO_CODE = "";
    String YOUTUBE_VIDEO_CODE1 = "l6xhlouCKk4";

    private MainActivity homeActivity;
    private YouTubePlayerSupportFragment youTubePlayerFragment;
    private YouTubePlayer mPlayer;
    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        homeActivity = (MainActivity) context;
        //   homeActivity.onFullScreenChangeListener = this;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            YOUTUBE_VIDEO_CODE = getArguments().getString("id");

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_youtube_channel, container, false);
        getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        youTubePlayerFragment = YouTubePlayerSupportFragment.newInstance();
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(R.id.youtube_layout, youTubePlayerFragment).commit();
        youTubePlayerFragment.initialize(Utils.YOUTUBE_PLAYER_DEVELOPER_KEY, this);
        return view;
    }


    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer player, boolean wasRestored) {

        if (!wasRestored) {
            Log.d("MainActivity", "onInitializationSuccess");
            mPlayer = player;
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);

            startVideo(YOUTUBE_VIDEO_CODE);
        }


    }

    public void startVideo(String youtube_key) {
        if (mPlayer != null && !mPlayer.isPlaying()) {
            mPlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.DEFAULT);
            mPlayer.setOnFullscreenListener(this);
            mPlayer.setPlayerStateChangeListener(this);
            mPlayer.cueVideo(youtube_key);
        }
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult error) {
        // YouTube error
        String errorMessage = error.toString();
        Toast.makeText(getActivity(), errorMessage, Toast.LENGTH_LONG).show();
        Log.d("errorMessage:", errorMessage);
    }


    @Override
    public void onResume() {
        System.out.println("custom toutube activity");
        super.onResume();
        // startVideo();


    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        if (youTubePlayerFragment != null && mPlayer != null) {
            youTubePlayerFragment.onDestroy();
            mPlayer.release();
        }


    }


    @Override
    public void onFullscreen(boolean b) {
        Utils.youtubePlayerFullScreen = b;
        if(b){
            mPlayer.setFullscreen(true);
            Log.d("MainActivity", "onFullscreen enable " + b);

        }
        if(!b){
            getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            Log.d("MainActivity", "onFullscreen not enable---- " + b);

        }

        Log.d("MainActivity", "onFullscreen: " + b);
        // Constants.mExoPlayerFullscreen = b;
    }

    @Override
    public void onLoading() {
        Log.d("MainActivity", "onLoading: ");
    }


    @Override
    public void onLoaded(String s) {
        if (!TextUtils.isEmpty(s) && mPlayer != null) {
            mPlayer.play();
        }

        Log.d("MainActivity", "onLoaded: ");
    }

    @Override
    public void onAdStarted() {
        Log.d("MainActivity", "onAdStarted: ");
    }

    @Override
    public void onVideoStarted() {
        Log.d("MainActivity", "onVideoStarted: ");
    }

    @Override
    public void onVideoEnded() {
        Log.d("MainActivity", "onVideoEnded: ");
    }

    @Override
    public void onError(YouTubePlayer.ErrorReason errorReason) {

        if (errorReason == YouTubePlayer.ErrorReason.UNKNOWN || errorReason == YouTubePlayer.ErrorReason.UNAUTHORIZED_OVERLAY || errorReason == YouTubePlayer.ErrorReason.NETWORK_ERROR) {

        }
        Log.d("MainActivity", "onError: errorReason: " + errorReason.name());
    }

    @Override
    public void onPlaying() {
        Log.d("MainActivity", "onPlaying: ");
    }

    @Override
    public void onPaused() {
        Log.d("MainActivity", "onPaused: ");
    }

    @Override
    public void onStopped() {
        Log.d("MainActivity", "onStopped: ");
    }

    @Override
    public void onBuffering(boolean b) {
        Log.d("MainActivity", "onBuffering: ");
    }

    @Override
    public void onSeekTo(int i) {
        Log.d("MainActivity", "onSeekTo: " + i);
    }

    @Override
    public void onChangeListener() {
        Log.d("MainActivity ","onChangeListener method call in youtube fragment");
        ((AppCompatActivity) getActivity()).getSupportActionBar().show();
        getActivity().setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        Utils.youtubePlayerFullScreen = false;

    }
}