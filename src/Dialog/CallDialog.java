package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.example.AilatrieuphuVer2.R;

/**
 * Created by Sergio on 16/03/2016.
 */
public class CallDialog extends DialogFragment implements View.OnClickListener {
    private View view;
    private ImageView tvAxtanh, tvNgoChau, tvLeVanLan, tvKhongMinh;
    private TextView tvDapAnGoiDien;
    private int temp;
    private TextView btOk;
    private OnOkListener mOnOkListener;

    public void setOnOkListener(OnOkListener onOkListener) {
        mOnOkListener = onOkListener;
    }

    public CallDialog(int trueCase) {
        temp = trueCase;
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_goidien, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE,android.R.style.TextAppearance_Theme_Dialog);
        initView();
        return view;
    }

    private void initView() {
        tvAxtanh = (ImageView) view.findViewById(R.id.call_axtanh);
        tvNgoChau = (ImageView) view.findViewById(R.id.call_ngobaochau);
        tvLeVanLan = (ImageView) view.findViewById(R.id.call_levanlan);
        tvKhongMinh = (ImageView) view.findViewById(R.id.call_khongminh);
        tvDapAnGoiDien = (TextView) view.findViewById(R.id.dap_an_goi_dien);
        btOk = (TextView) view.findViewById(R.id.bt_Ok);

        tvDapAnGoiDien.setVisibility(View.INVISIBLE);
        btOk.setOnClickListener(this);
        tvAxtanh.setOnClickListener(this);
        tvNgoChau.setOnClickListener(this);
        tvLeVanLan.setOnClickListener(this);
        tvKhongMinh.setOnClickListener(this);
        switch (temp) {
            case 1:
                tvDapAnGoiDien.setText(R.string.dapanA);
                break;
            case 2:
                tvDapAnGoiDien.setText(R.string.dapanB);
                break;
            case 3:
                tvDapAnGoiDien.setText(R.string.dapanC);
                break;
            case 4:
                tvDapAnGoiDien.setText(R.string.dapanD);
                break;
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.call_axtanh:
                tvDapAnGoiDien.setVisibility(View.VISIBLE);
                tvAxtanh.setVisibility(View.INVISIBLE);
                tvLeVanLan.setVisibility(View.INVISIBLE);
                tvKhongMinh.setVisibility(View.INVISIBLE);
                tvNgoChau.setVisibility(View.INVISIBLE);
                break;
            case R.id.call_ngobaochau:
                tvDapAnGoiDien.setVisibility(View.VISIBLE);
                tvAxtanh.setVisibility(View.INVISIBLE);
                tvLeVanLan.setVisibility(View.INVISIBLE);
                tvKhongMinh.setVisibility(View.INVISIBLE);
                tvNgoChau.setVisibility(View.INVISIBLE);
                break;
            case R.id.call_levanlan:
                tvDapAnGoiDien.setVisibility(View.VISIBLE);
                tvAxtanh.setVisibility(View.INVISIBLE);
                tvLeVanLan.setVisibility(View.INVISIBLE);
                tvKhongMinh.setVisibility(View.INVISIBLE);
                tvNgoChau.setVisibility(View.INVISIBLE);
                break;
            case R.id.call_khongminh:
                tvDapAnGoiDien.setVisibility(View.VISIBLE);
                tvAxtanh.setVisibility(View.INVISIBLE);
                tvLeVanLan.setVisibility(View.INVISIBLE);
                tvKhongMinh.setVisibility(View.INVISIBLE);
                tvNgoChau.setVisibility(View.INVISIBLE);
                break;
            case R.id.bt_Ok:
                mOnOkListener.OnOk(true);
                dismiss();
                break;
        }
    }

    public interface OnOkListener {
        void OnOk(boolean b);
    }
}
