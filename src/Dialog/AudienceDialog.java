package Dialog;

import android.app.DialogFragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.example.AilatrieuphuVer2.R;

import java.util.Random;

/**
 * Created by Sergio on 16/03/2016.
 */
public class AudienceDialog extends DialogFragment {
    private int tempCase = 0;
    private Random rd = new Random();
    private TextView progessA, progessB, progessC, progessD;
    private RelativeLayout caseA, caseB, caseC, caseD;
    private Button btnBack;
    private int tempI, tempII, tempIII, tempIIII;
    private View view;
    private OnContinueGame mOnContinueGame;

    public AudienceDialog(int caseTrue) {
        tempCase = caseTrue;
    }

    public void setOnContinueGame(OnContinueGame onContinueGame) {
        mOnContinueGame = onContinueGame;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.dialog_khangia, container, false);
        setStyle(DialogFragment.STYLE_NO_TITLE, android.R.style.TextAppearance_Theme_Dialog);
        initView();
        return view;
    }

    private void initView() {
        progessA = (TextView) view.findViewById(R.id.progress_caseA);
        progessB = (TextView) view.findViewById(R.id.progress_caseB);
        progessC = (TextView) view.findViewById(R.id.progress_caseC);
        progessD = (TextView) view.findViewById(R.id.progress_caseD);
        btnBack = (Button) view.findViewById(R.id.btn_back);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mOnContinueGame.continueGame(true);
                dismiss();
            }
        });

        caseA = (RelativeLayout) view.findViewById(R.id.height_caseA);
        caseB = (RelativeLayout) view.findViewById(R.id.height_caseB);
        caseC = (RelativeLayout) view.findViewById(R.id.height_caseC);
        caseD = (RelativeLayout) view.findViewById(R.id.height_caseD);

        askAudience();
    }

    public void askAudience() {

        tempI = rd.nextInt(60 - 50) + 50;
        tempII = rd.nextInt(40);
        tempIII = rd.nextInt(40);
        tempIIII = 100 - tempI - tempII - tempIII;
        while (tempI < 0 || tempII < 0 || tempIII < 0 || tempIIII < 0) {
            tempI = rd.nextInt(60) + 50;
            tempII = rd.nextInt(30);
            tempIII = rd.nextInt(30);
            tempIIII = 100 - tempI - tempII - tempIII;
        }
        Log.i("1", tempI + "");
        Log.i("2", tempII + "");
        Log.i("3", tempIII + "");
        Log.i("4", tempIIII + "");

        switch (tempCase) {
            case 1:
                progessA.setText(tempI + "%");
                progessB.setText(tempII + "%");
                progessC.setText(tempIII + "%");
                progessD.setText(tempIIII + "%");
                caseA.getLayoutParams().width = 200;
                caseA.getLayoutParams().height = tempI * 3;
                caseB.getLayoutParams().height = tempII * 3;
                caseC.getLayoutParams().height = tempIII * 3;
                caseD.getLayoutParams().height = tempIIII * 3;
                break;
            case 2:
                progessA.setText(tempII + "%");
                progessB.setText(tempI + "%");
                progessC.setText(tempIII + "%");
                progessD.setText(tempIIII + "%");
                caseA.getLayoutParams().height = tempII * 3;
                caseB.getLayoutParams().height = tempI * 3;
                caseC.getLayoutParams().height = tempIII * 3;
                caseD.getLayoutParams().height = tempIIII * 3;
                break;
            case 3:
                progessA.setText(tempIII + "%");
                progessB.setText(tempII + "%");
                progessC.setText(tempI + "%");
                progessD.setText(tempIIII + "%");

                caseA.getLayoutParams().height = tempIII * 3;
                caseB.getLayoutParams().height = tempII * 3;
                caseC.getLayoutParams().height = tempI * 3;
                caseD.getLayoutParams().height = tempIIII * 3;
                break;

            case 4:
                progessA.setText(tempII + "%");
                progessB.setText(tempIII + "%");
                progessC.setText(tempIIII + "%");
                progessD.setText(tempI + "%");

                caseA.getLayoutParams().height = tempII * 3;
                caseB.getLayoutParams().height = tempIII * 3;
                caseC.getLayoutParams().height = tempIIII * 3;
                caseD.getLayoutParams().height = tempI * 3;
                break;

            default:
                break;
        }

    }

    public interface OnContinueGame {
        void continueGame(Boolean aBoolean);
    }

}
