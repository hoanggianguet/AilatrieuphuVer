package com.example.AilatrieuphuVer2;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Giang Hoang on 18/01/2016.
 */
public class HelloFragment extends BaseFragment implements View.OnClickListener {
    private TextView btPlayGame;
    private ImageView ivCircle;
    private TextView btHighSroce;
    private MediaPlayer mMediaPlayer;

    public HelloFragment(int idLayout) {
        super(idLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        return rootView;
    }

    private void initView() {
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.bgmusic);
        mMediaPlayer.start();
        mMediaPlayer.setLooping(true);

        btPlayGame = (TextView) rootView.findViewById(R.id.bt_PlayGame);
        ivCircle = (ImageView) rootView.findViewById(R.id.circle_anim);
        btHighSroce = (TextView) rootView.findViewById(R.id.bt_HighScore);
        btHighSroce.setOnClickListener(this);
        btPlayGame.setOnClickListener(this);
        rotateInfinite();
    }

    private void rotateInfinite() {
        Animation showMenu = AnimationUtils.loadAnimation(getActivity(), R.anim.rotate);
        ivCircle.startAnimation(showMenu);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_PlayGame:
                ((MyActivity) getActivity()).showPlayFragment();
                mMediaPlayer.release();
                break;
            case R.id.bt_HighScore:
                ((MyActivity) getActivity()).showHighScoreFragment();
                break;
        }
    }


}
