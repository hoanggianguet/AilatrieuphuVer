package com.example.AilatrieuphuVer2;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Sergio on 16/03/2016.
 */
public class AchieveFragmet extends BaseFragment {
    private TextView tvAchieve;
    private ImageView btReturn;

    public AchieveFragmet(int idLayout) {
        super(idLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        return rootView;
    }

    private void initView() {
        btReturn = (ImageView) rootView.findViewById(R.id.bt_Return);
        btReturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ((MyActivity) getActivity()).showHelloFragment();
            }
        });
        tvAchieve = (TextView) rootView.findViewById(R.id.tv_Achieve);
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainFragment.MyPREFERENCES, Context.MODE_PRIVATE);
        int money = sharedPreferences.getInt(MainFragment.MONEY, 0);
        tvAchieve.setText(money + " VNĐ");
    }
}
