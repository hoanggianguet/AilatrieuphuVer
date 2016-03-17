package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.AilatrieuphuVer2.R;

/**
 * Created by Sergio on 15/03/2016.
 */
public class DialogFinish extends DialogFragment {
    private View view;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_stop, container, false);
        initView();
        return view;
    }

    private void initView() {
    }
}
