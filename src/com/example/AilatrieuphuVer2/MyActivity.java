package com.example.AilatrieuphuVer2;

import android.app.Activity;
import android.os.Bundle;

public class MyActivity extends Activity {
    private SplashFragment splashFragment = new SplashFragment(R.layout.splash_frag);
    private HelloFragment helloFragment = new HelloFragment(R.layout.frag_hello2);
    private MainFragment playFragment;
    private AchieveFragmet achieveFragmet = new AchieveFragmet(R.layout.result_fragment);
    private HighScoreFragment highScoreFragment = new HighScoreFragment(R.layout.score);

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initView();
    }

    private void initView() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, splashFragment).commit();
    }

    public void showHelloFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, helloFragment).commit();
    }

    public void showPlayFragment() {
        playFragment = new MainFragment(R.layout.main);
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, playFragment).commit();
    }

    public void goToAchieveFrament() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, achieveFragmet).commit();
    }

    public void showHighScoreFragment() {
        getFragmentManager().beginTransaction()
                .replace(android.R.id.content, highScoreFragment).commit();
    }
}
