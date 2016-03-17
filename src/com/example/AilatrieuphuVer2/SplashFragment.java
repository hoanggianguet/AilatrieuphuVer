package com.example.AilatrieuphuVer2;

import android.os.Handler;

/**
 * Created by Rock on 04/03/2016.
 */
public class SplashFragment extends BaseFragment {
    public SplashFragment(int idLayout) {
        super(idLayout);
        new Handler().postDelayed(runnable, 3000);
    }

    private Runnable runnable = new Runnable() {
        @Override
        public void run() {
            ((MyActivity) getActivity()).showHelloFragment();
        }
    };


}
