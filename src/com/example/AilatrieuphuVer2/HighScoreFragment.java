package com.example.AilatrieuphuVer2;

import Adapter.HighScoreAdapter;
import Database.HighScore;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;

/**
 * Created by Sergio on 16/03/2016.
 */
public class HighScoreFragment extends BaseFragment {
    private ListView lvHighScore;
    private HighScoreAdapter scoreAdapter;
    private HighScore highScore;
    private ImageView btBack;

    public HighScoreFragment(int idLayout) {
        super(idLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.score, container, false);
        initView();
        return rootView;
    }

    private void initView() {
        highScore = new HighScore(getActivity());
        lvHighScore = (ListView) rootView.findViewById(R.id.lv_Score);
        scoreAdapter = new HighScoreAdapter(highScore.getAllScore(), getActivity());
        lvHighScore.setAdapter(scoreAdapter);
        btBack = (ImageView) rootView.findViewById(R.id.bt_Back);
        btBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyActivity) getActivity()).showHelloFragment();
            }
        });
    }
}
