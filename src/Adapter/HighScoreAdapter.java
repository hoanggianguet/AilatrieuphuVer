package Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.example.AilatrieuphuVer2.R;

import java.util.ArrayList;

/**
 * Created by Sergio on 16/03/2016.
 */
public class HighScoreAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private Context mContext;
    private ArrayList<Integer> mArrScore;

    public HighScoreAdapter(ArrayList<Integer> arrScore, Context context) {
        mArrScore = arrScore;
        mContext = context;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mArrScore.size();
    }

    @Override
    public Integer getItem(int position) {
        return mArrScore.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_score, parent, false);
        }
        TextView textView = (TextView) convertView.findViewById(R.id.tv_Score);
        textView.setText(mArrScore.get(position) + " VNĐ");
        return convertView;
    }
}
