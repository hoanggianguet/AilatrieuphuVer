package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.example.AilatrieuphuVer2.R;

/**
 * Created by Sergio on 16/03/2016.
 */
public class StopGameDialog extends DialogFragment implements View.OnClickListener {
    private View view;
    private Button btStop;
    private Button btReturn;
    private OnStopListener mOnStopListener;

    public void setOnStopListener(OnStopListener onStopListener) {
        mOnStopListener = onStopListener;

    }

    public StopGameDialog() {

    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_stop, container, false);
        initView();
        return view;
    }

    private void initView() {
        btReturn = (Button) view.findViewById(R.id.btn_Return);
        btStop = (Button) view.findViewById(R.id.bt_Stop);
        btStop.setOnClickListener(this);
        btReturn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_Return:
                mOnStopListener.onStopGame(false);
                dismiss();
                break;
            case R.id.bt_Stop:
                mOnStopListener.onStopGame(true);
                dismiss();
                break;
        }

    }

    public interface OnStopListener {
        void onStopGame(boolean bool);
    }
}
