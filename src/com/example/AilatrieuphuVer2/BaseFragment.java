package com.example.AilatrieuphuVer2;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Giang Hoang on 26/12/2015.
 */
public class BaseFragment extends Fragment {
    protected View rootView;
    protected int idLayout;

    public BaseFragment(int idLayout) {
        this.idLayout = idLayout;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(idLayout, container, false);
        return rootView;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}
