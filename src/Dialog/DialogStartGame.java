package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.AilatrieuphuVer2.MyActivity;
import com.example.AilatrieuphuVer2.R;

/**
 * Created by Sergio on 15/03/2016.
 */
public class DialogStartGame extends DialogFragment implements View.OnClickListener {
    private Button btReady;
    private Button btSkip;
    private View rootView;
    private OnDialogReadyListener mListener;

    public DialogStartGame() {

    }

    public void setOnReadyDialogListener(OnDialogReadyListener onReadyDialogListener) {
        mListener = onReadyDialogListener;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.dialog_sansang, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.TextAppearance_Theme_Dialog);
        initView();
        return rootView;
    }

    private void initView() {
        btReady = (Button) rootView.findViewById(R.id.btn_sanSang);
        btSkip = (Button) rootView.findViewById(R.id.btn_chuaSanSang);
        btReady.setOnClickListener(this);
        btSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_sanSang:
                mListener.startGame(true);
                dismiss();
                break;
            case R.id.btn_chuaSanSang:
                ((MyActivity) getActivity()).showHelloFragment();
                dismiss();
                break;
        }
    }

    public interface OnDialogReadyListener {
        void startGame(Boolean aBoolean);
    }
}
