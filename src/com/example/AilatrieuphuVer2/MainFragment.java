package com.example.AilatrieuphuVer2;

import Database.DBManager;
import Database.HighScore;
import Database.QuestionItem;
import Dialog.AudienceDialog;
import Dialog.CallDialog;
import Dialog.DialogStartGame;
import Dialog.StopGameDialog;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.SystemClock;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.*;

import java.util.ArrayList;
import java.util.Random;

/**
 * Created by Giang Hoang on 18/01/2016.
 */
public class MainFragment extends BaseFragment implements View.OnClickListener, DialogStartGame.OnDialogReadyListener, AudienceDialog.OnContinueGame, CallDialog.OnOkListener, StopGameDialog.OnStopListener {
    private static final String MY_TAG = "Tag";
    public static final String MONEY = "MY_MONEY";
    private DBManager dbManager;
    private ProgressBar mProgressBar;
    private ArrayList<QuestionItem> arrayQuestion;
    private TextView tvCountDownTimer, tvAmountMoney, tvQuetionNumber, tvContentQuestion, tvCaseA, tvCaseB, tvCaseC, tvCaseD;
    private TextView tvMoney1, tvMoney2, tvMoney3, tvMoney4, tvMoney5, tvMoney6, tvMoney7, tvMoney8, tvMoney9, tvMoney10,
            tvMoney11, tvMoney12, tvMoney13, tvMoney14, tvMoney15;
    private ImageView btHelpStop, btHelpChangeQuestion, btHelp5050, btHelpAudience, btHelpCall;
    private int currentQuestion = 0, money, numberOfUserAnswer, numberOfTrueAnswer;
    private QuestionItem current;
    private String trueAnswer, answerUser;
    private boolean isRunning, runed = false, changeQuestion = false;
    private Random random = new Random();
    private LinearLayout lnPrice;
    private LinearLayout lnMenu;
    private Thread countTime;
    private Handler handler;
    private int timer = 30;
    private DialogStartGame mDialogStartGame;
    private StopGameDialog mStopGameDialog;
    private AudienceDialog mAudienceDialog;
    private CallDialog mCallDialog;
    private MediaPlayer mMediaPlayer;
    public static final String MyPREFERENCES = "MY_PREF";
    private HighScore mHighScore;

    public MainFragment(int idLayout) {
        super(idLayout);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = super.onCreateView(inflater, container, savedInstanceState);
        initView();
        countDownTimer();
        return rootView;
    }

    private void initView() {
        handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                tvCountDownTimer.setText(msg.arg1 + "");
                mProgressBar.setProgress(msg.arg1);
            }
        };

        lnPrice = (LinearLayout) rootView.findViewById(R.id.lv_Price);
        lnMenu = (LinearLayout) rootView.findViewById(R.id.right_to_left);
        money = 0;

        //set money = 0
        dbManager = new DBManager(getActivity());
        arrayQuestion = dbManager.getQuestions();
        for (int i = 0; i < arrayQuestion.size(); i++) {
            Log.i(MY_TAG, arrayQuestion.get(i).getQuestion() + " : " + arrayQuestion.get(i).getTrueCase());
        }
        mProgressBar = (ProgressBar) rootView.findViewById(R.id.progressBar);
        mProgressBar.setProgress(0);
        tvCountDownTimer = (TextView) rootView.findViewById(R.id.tv_CountDownTimer);
        tvAmountMoney = (TextView) rootView.findViewById(R.id.tv_MoneyAmount);
        tvQuetionNumber = (TextView) rootView.findViewById(R.id.tv_QuestionNumber);
        tvContentQuestion = (TextView) rootView.findViewById(R.id.tv_ContentQuestion);
        tvCaseA = (TextView) rootView.findViewById(R.id.bt_CaseA);
        tvCaseB = (TextView) rootView.findViewById(R.id.bt_CaseB);
        tvCaseC = (TextView) rootView.findViewById(R.id.bt_CaseC);
        tvCaseD = (TextView) rootView.findViewById(R.id.bt_CaseD);
        btHelpStop = (ImageView) rootView.findViewById(R.id.bt_HelpStop);
        btHelpChangeQuestion = (ImageView) rootView.findViewById(R.id.bt_HelpChangeQuestion);
        btHelp5050 = (ImageView) rootView.findViewById(R.id.bt_Help5050);
        btHelpAudience = (ImageView) rootView.findViewById(R.id.bt_HelpAudience);
        btHelpCall = (ImageView) rootView.findViewById(R.id.bt_HelpCall);

        tvMoney1 = (TextView) rootView.findViewById(R.id.tv_money_question_1);
        tvMoney2 = (TextView) rootView.findViewById(R.id.tv_money_question_2);
        tvMoney3 = (TextView) rootView.findViewById(R.id.tv_money_question_3);
        tvMoney4 = (TextView) rootView.findViewById(R.id.tv_money_question_4);
        tvMoney5 = (TextView) rootView.findViewById(R.id.tv_money_question_5);
        tvMoney6 = (TextView) rootView.findViewById(R.id.tv_money_question_6);
        tvMoney7 = (TextView) rootView.findViewById(R.id.tv_money_question_7);
        tvMoney8 = (TextView) rootView.findViewById(R.id.tv_money_question_8);
        tvMoney9 = (TextView) rootView.findViewById(R.id.tv_money_question_9);
        tvMoney10 = (TextView) rootView.findViewById(R.id.tv_money_question_10);
        tvMoney11 = (TextView) rootView.findViewById(R.id.tv_money_question_11);
        tvMoney12 = (TextView) rootView.findViewById(R.id.tv_money_question_12);
        tvMoney13 = (TextView) rootView.findViewById(R.id.tv_money_question_13);
        tvMoney14 = (TextView) rootView.findViewById(R.id.tv_money_question_14);
        tvMoney15 = (TextView) rootView.findViewById(R.id.tv_money_question_15);

        btHelpStop.setOnClickListener(this);
        btHelpChangeQuestion.setOnClickListener(this);
        btHelp5050.setOnClickListener(this);
        btHelpAudience.setOnClickListener(this);
        btHelpCall.setOnClickListener(this);
        tvCaseA.setOnClickListener(this);
        tvCaseB.setOnClickListener(this);
        tvCaseC.setOnClickListener(this);
        tvCaseD.setOnClickListener(this);
        disableChooseClick();


        mDialogStartGame = new DialogStartGame();
        mDialogStartGame.setOnReadyDialogListener(this);
        mStopGameDialog = new StopGameDialog();
        mStopGameDialog.setOnStopListener(this);
        mHighScore = new HighScore(getActivity());
        introduce();
        hideMenu();
    }

    private void disableChooseClick() {
        tvCaseA.setClickable(false);
        tvCaseB.setClickable(false);
        tvCaseC.setClickable(false);
        tvCaseD.setClickable(false);
    }

    private void introduce() {
        introduceSound();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                tvMoney5.setBackgroundResource(R.drawable.ic_question_normal);
                setTextViewMoney10();
            }
}, 2000);
        }

    private void introduceSound() {
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.luatchoi_b);
        mMediaPlayer.start();
    }

    private void setTextViewMoney10() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeBackground(tvMoney5);
                tvMoney10.setBackgroundResource(R.drawable.ic_question_normal);
                setTextViewMoney15();
            }
        }, 2000);
    }

    private void setTextViewMoney15() {

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeBackground(tvMoney10);
                tvMoney15.setBackgroundResource(R.drawable.ic_question_normal);
                removeBackground15();
            }
        }, 2000);
    }

    private void removeBackground15() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                removeBackground(tvMoney15);
                hideListViewPrice();

            }
        }, 2000);
    }

    private void showDialogStartGame() {
        mDialogStartGame.show(getFragmentManager(), MY_TAG);
    }

    public void removeBackground(View view) {
        view.setBackgroundResource(0);
    }


    private void hideMenu() {
        Animation hideMenu = AnimationUtils.loadAnimation(getActivity(), R.anim.left_to_right);
        lnMenu.startAnimation(hideMenu);
    }

    private void showMain() {
        Animation showMenu = AnimationUtils.loadAnimation(getActivity(), R.anim.showmenu);
        lnMenu.startAnimation(showMenu);
        continueTime();
    }

    private void hideListViewPrice() {
        Animation right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left);
        lnPrice.startAnimation(right_to_left);
        showDialogStartGame();
    }

    private void showListViewPrice() {
        Animation leftToRight = AnimationUtils.loadAnimation(getActivity(), R.anim.showmenu);
        lnPrice.startAnimation(leftToRight);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                if (changeQuestion == true) {
                    switch (currentQuestion) {
                        case 0:
                        case 1:
                            tvMoney1.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 2:
                            tvMoney2.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 3:
                            tvMoney3.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 4:
                            tvMoney4.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 5:
                            tvMoney5.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 6:
                            tvMoney6.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 7:
                            tvMoney7.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 8:
                            tvMoney8.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 9:
                            tvMoney9.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 10:
                            tvMoney10.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 11:
                            tvMoney11.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 12:
                            tvMoney12.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 13:
                            tvMoney13.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 14:
                            tvMoney14.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 15:
                            tvMoney15.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                    }
                } else {
                    switch (currentQuestion) {
                        case 0:
                            tvMoney1.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 1:
                            tvMoney2.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 2:
                            tvMoney3.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 3:
                            tvMoney4.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 4:
                            tvMoney5.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 5:
                            tvMoney6.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 6:
                            tvMoney7.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 7:
                            tvMoney8.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 8:
                            tvMoney9.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 9:
                            tvMoney10.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 10:
                            tvMoney11.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 11:
                            tvMoney12.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 12:
                            tvMoney13.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 13:
                            tvMoney14.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                        case 14:
                            tvMoney15.setBackgroundResource(R.drawable.ic_question_normal);
                            break;
                    }
                }

                ready();

            }
        };
        new Handler().postDelayed(runnable, 1000);
    }

    private void ready() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                hideListViewPriceAfter();
            }
        }, 1000);
    }

    private void hideListViewPriceAfter() {
        Animation right_to_left = AnimationUtils.loadAnimation(getActivity(), R.anim.right_to_left);
        lnPrice.startAnimation(right_to_left);
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                showQuestion(currentQuestion);
                showMain();
            }
        };
        new Handler().postDelayed(runnable, 2000);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_CaseA:
                soundChooseA();
                stopTime();
                userChooseA();
                selectAnswer(tvCaseA);
                break;
            case R.id.bt_CaseB:
                soundChooseB();
                stopTime();
                userChooseB();
                selectAnswer(tvCaseB);
                break;
            case R.id.bt_CaseC:
                soundChooseC();
                stopTime();
                userChooseC();
                selectAnswer(tvCaseC);
                break;
            case R.id.bt_CaseD:
                soundChooseD();
                stopTime();
                selectAnswer(tvCaseD);
                userChooseD();
                break;
            case R.id.bt_HelpStop:
                stopTime();
                showStopGameDialog();
                break;
            case R.id.bt_HelpChangeQuestion:
                stopTime();
                changeQuestion();
                break;
            case R.id.bt_Help5050:
                stopTime();
                disableTwoWrongAnswer();
                disableHelp5050();
                break;
            case R.id.bt_HelpAudience:
                stopTime();
                helpAudience();
                disableHelpAudience();
                break;
            case R.id.bt_HelpCall:
                stopTime();
                helpCall();
                disableHelpCall();
                break;
        }

    }

    private void showStopGameDialog() {
        mStopGameDialog.show(getFragmentManager(), MY_TAG);
    }

    private void changeQuestion() {
        changeQuestion = true;
        currentQuestion++;
        showQuestionChange(currentQuestion);
    }

    private void playHelpChangeQuestionSound() {

    }

    private void soundChooseD() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ans_d);
        mMediaPlayer.start();
    }

    private void soundChooseC() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ans_c);
        mMediaPlayer.start();
    }

    private void soundChooseB() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ans_b);
        mMediaPlayer.start();
    }

    private void soundChooseA() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ans_a);
        mMediaPlayer.start();
    }

    public void selectAnswer(View view) {
        view.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_selected);

        Animation chooseAnswer = AnimationUtils.loadAnimation(getActivity(), R.anim.atp__answer_choose);
        view.startAnimation(chooseAnswer);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                clearAnim(view);
                checkAnswer();
            }
        }, 3000);
    }

    public void clearAnim(View view) {
        view.clearAnimation();
    }


    public void stopTime() {
        runed = false;
    }

    public void continueTime() {
        runed = true;
    }

    private void showQuestion(int number) {
        Log.i("Question", number + "");
        if (changeQuestion == true) {
            switch (number) {
                case 1:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques1);
                    mMediaPlayer.start();
                    break;
                case 2:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques2);
                    mMediaPlayer.start();
                    break;
                case 3:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques3);
                    mMediaPlayer.start();
                    break;
                case 4:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques4);
                    mMediaPlayer.start();
                    break;
                case 5:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques5);
                    mMediaPlayer.start();
                    break;
                case 6:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques6);
                    mMediaPlayer.start();
                    break;
                case 7:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques7);
                    mMediaPlayer.start();
                    break;
                case 8:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques8);
                    mMediaPlayer.start();
                    break;
                case 9:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques9);
                    mMediaPlayer.start();
                    break;
                case 10:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques10);
                    mMediaPlayer.start();
                    break;
                case 11:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques11);
                    mMediaPlayer.start();
                    break;
                case 12:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques12);
                    mMediaPlayer.start();
                    break;
                case 13:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques13);
                    mMediaPlayer.start();
                    break;
                case 14:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques14);
                    mMediaPlayer.start();
                    break;
                case 15:
                    mMediaPlayer.release();
                    mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques15);
                    mMediaPlayer.start();
                    break;
            }

        }
        switch (number) {
            case 0:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques1);
                mMediaPlayer.start();
                break;
            case 1:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques2);
                mMediaPlayer.start();
                break;
            case 2:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques3);
                mMediaPlayer.start();
                break;
            case 3:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques4);
                mMediaPlayer.start();
                break;
            case 4:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques5);
                mMediaPlayer.start();
                break;
            case 5:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques6);
                mMediaPlayer.start();
                break;
            case 6:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques7);
                mMediaPlayer.start();
                break;
            case 7:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques8);
                mMediaPlayer.start();
                break;
            case 8:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques9);
                mMediaPlayer.start();
                break;
            case 9:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques10);
                mMediaPlayer.start();
                break;
            case 10:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques11);
                mMediaPlayer.start();
                break;
            case 11:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques12);
                mMediaPlayer.start();
                break;
            case 12:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques13);
                mMediaPlayer.start();
                break;
            case 13:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques14);
                mMediaPlayer.start();
                break;
            case 14:
                mMediaPlayer.release();
                mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.ques15);
                mMediaPlayer.start();
                break;
        }


        tvCaseA.setClickable(true);
        tvCaseB.setClickable(true);
        tvCaseC.setClickable(true);
        tvCaseD.setClickable(true);


        //set image normal for 4 button
        tvCaseA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);

        //set visible for 4 button
        tvCaseA.setVisibility(View.VISIBLE);
        tvCaseB.setVisibility(View.VISIBLE);
        tvCaseC.setVisibility(View.VISIBLE);
        tvCaseD.setVisibility(View.VISIBLE);

        //set isRunnint is true to count time

        tvAmountMoney.setText("" + money);
        //reset string trueAnswer and answer user
        trueAnswer = null;
        answerUser = null;

        current = arrayQuestion.get(number);
        if (changeQuestion == true) {
            tvQuetionNumber.setText("Câu " + number);
        } else {
            tvQuetionNumber.setText("Câu " + ++number);
        }

        tvContentQuestion.setText(current.getQuestion());
        tvCaseA.setText("A: " + current.getCaseA());
        tvCaseB.setText("B: " + current.getCaseB());
        tvCaseC.setText("C: " + current.getCaseC());
        tvCaseD.setText("D: " + current.getCaseD());
        getQuestionTrue(current);

        //set number of true answer to show when user answer wrong
        numberOfTrueAnswer = current.getTrueCase();

    }

    private void showQuestionChange(int number) {

        tvCaseA.setClickable(true);
        tvCaseB.setClickable(true);
        tvCaseC.setClickable(true);
        tvCaseD.setClickable(true);


        //set image normal for 4 button
        tvCaseA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);
        tvCaseD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_normal);

        //set visible for 4 button
        tvCaseA.setVisibility(View.VISIBLE);
        tvCaseB.setVisibility(View.VISIBLE);
        tvCaseC.setVisibility(View.VISIBLE);
        tvCaseD.setVisibility(View.VISIBLE);


        tvAmountMoney.setText("" + money);
        //reset string trueAnswer and answer user
        trueAnswer = null;
        answerUser = null;

        current = arrayQuestion.get(number);
        tvQuetionNumber.setText("Câu " + number);
        tvContentQuestion.setText(current.getQuestion());
        tvCaseA.setText("A: " + current.getCaseA());
        tvCaseB.setText("B: " + current.getCaseB());
        tvCaseC.setText("C: " + current.getCaseC());
        tvCaseD.setText("D: " + current.getCaseD());
        getQuestionTrue(current);

        //set number of true answer to show when user answer wrong
        numberOfTrueAnswer = current.getTrueCase();
        resetTime();
        continueTime();
        disableHelpChangeQuestion();
    }

    private void disableHelpChangeQuestion() {
        btHelpChangeQuestion.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
        btHelpChangeQuestion.setClickable(false);
    }

    private void countDownTimer() {
        isRunning = true;
        countTime = new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunning) {
                    if (runed) {

                        Message message = handler.obtainMessage();
                        message.arg1 = timer;
                        handler.sendMessage(message);
                        SystemClock.sleep(1000);
                        timer--;
                        if (timer == 0) {
                            runed = false;
                            finishGame();
                        }
                    }
                }
            }
        });
        countTime.start();
    }

    private void checkAnswer() {
        if (tvCountDownTimer.getText().toString() == "0" && answerUser == null) {

        }
        if (trueAnswer.equals(answerUser)) {
            stopTime();
            showCorrectAnswer();
            removeBackgoundPrice();

//            handler.postDelayed(new Runnable() {
//                @Override
//                public void run() {
//                    showListViewPrice();
//                }
//            }, 3100);
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    setMoney();
                    currentQuestion++;
                    showListViewPrice();
                    resetTime();
                    hideMenu();
                }
            }, 3100);
        } else {
            isRunning = false;
            showWrongAnswer();
        }
        Log.i("TrueAnswer", "True answer:" + trueAnswer);
        Log.i("AnswerUser", "AnswerUser:" + answerUser);
    }

    private void removeBackgoundPrice() {
        removeBackground(tvMoney1);
        removeBackground(tvMoney2);
        removeBackground(tvMoney3);
        removeBackground(tvMoney4);
        removeBackground(tvMoney5);
        removeBackground(tvMoney6);
        removeBackground(tvMoney7);
        removeBackground(tvMoney8);
        removeBackground(tvMoney9);
        removeBackground(tvMoney10);
        removeBackground(tvMoney11);
        removeBackground(tvMoney12);
        removeBackground(tvMoney13);
        removeBackground(tvMoney14);
        removeBackground(tvMoney15);
    }


    private void showWrongAnswer() {
        setClickAbleAll();
        Animation animTrue = AnimationUtils.loadAnimation(getActivity(), R.anim.atp__answer_true);
        Toast.makeText(getActivity(), "Wrong", Toast.LENGTH_SHORT).show();
        switch (numberOfUserAnswer) {
            case 1:
                tvCaseA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                break;
            case 2:
                tvCaseB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                break;
            case 3:
                tvCaseC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                break;
            case 4:
                tvCaseD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_wrong);
                break;
        }
        switch (numberOfTrueAnswer) {
            case 1:
                tvCaseA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseA.startAnimation(animTrue);
                wrongAnswerSoundA();
                break;
            case 2:
                tvCaseB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseB.startAnimation(animTrue);
                wrongAnswerSoundB();
                break;
            case 3:
                tvCaseC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseC.startAnimation(animTrue);
                wrongAnswerSoundC();
                break;
            case 4:
                tvCaseD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseD.startAnimation(animTrue);
                wrongAnswerSoundD();
                break;
        }
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (numberOfTrueAnswer) {
                    case 1:
                        clearAnim(tvCaseA);
                        break;
                    case 2:
                        clearAnim(tvCaseB);
                        break;
                    case 3:
                        clearAnim(tvCaseC);
                        break;
                    case 4:
                        clearAnim(tvCaseD);
                        break;


                }
                finishGame();
            }
        }, 3000);
    }

    private void setClickAbleAll() {
        btHelpChangeQuestion.setClickable(false);
        btHelp5050.setClickable(false);
        btHelpAudience.setClickable(false);
        btHelpCall.setClickable(false);
        btHelpStop.setClickable(false);
    }

    private void wrongAnswerSoundD() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lose_d);
        mMediaPlayer.start();
    }

    private void wrongAnswerSoundC() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lose_c);
        mMediaPlayer.start();
    }

    private void wrongAnswerSoundB() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lose_b);
        mMediaPlayer.start();
    }

    private void wrongAnswerSoundA() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lose_a);
        mMediaPlayer.start();
    }

    private void showCorrectAnswer() {
        trueAnswerSound();
        Animation trueAnswer = AnimationUtils.loadAnimation(getActivity(), R.anim.atp__answer_choose);
        switch (numberOfTrueAnswer) {
            case 1:
                tvCaseA.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseA.startAnimation(trueAnswer);
                break;
            case 2:
                tvCaseB.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseB.startAnimation(trueAnswer);
                break;
            case 3:
                tvCaseC.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseC.startAnimation(trueAnswer);
                break;
            case 4:
                tvCaseD.setBackgroundResource(R.drawable.atp__activity_player_layout_play_answer_background_true);
                tvCaseD.startAnimation(trueAnswer);
                break;
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                switch (numberOfTrueAnswer) {
                    case 1:
                        clearAnim(tvCaseA);
                        break;
                    case 2:
                        clearAnim(tvCaseB);
                        break;
                    case 3:
                        clearAnim(tvCaseC);
                        break;
                    case 4:
                        clearAnim(tvCaseD);
                        break;
                }

            }
        }, 3000);
    }

    private void trueAnswerSound() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.true_a2);
        mMediaPlayer.start();
    }

    private void finishGame() {
        if (money > 0) {
            mHighScore.addScore(money);
        }
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                finishGameSound();
                goToAchieveFragmet();

            }
        }, 2000);
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onDestroy();
                onDetach();
            }
        }, 3000);
    }

    private void goToAchieveFragmet() {
        saveUserMoney();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {

                ((MyActivity) getActivity()).goToAchieveFrament();
            }
        }, 3000);

    }

    private void saveUserMoney() {
        SharedPreferences sharedpreferences = getActivity().getSharedPreferences(MyPREFERENCES, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();
        editor.putInt(MONEY, money);
        editor.apply();
    }

    private void finishGameSound() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.lose2);
        mMediaPlayer.start();

    }


    public void resetTime() {
        timer = 30;
    }

    private void setMoney() {
        if (changeQuestion == true) {
            switch (currentQuestion) {
                case 1:
                    money = 400000;
                    break;
                case 3:
                    money = 600000;
                    break;
                case 4:
                    money = 1000000;
                    break;
                case 5:
                    money = 2000000;
                    break;
                case 6:
                    money = 3000000;
                    break;
                case 7:
                    money = 6000000;
                    break;
                case 8:
                    money = 10000000;
                    break;
                case 9:
                    money = 14000000;
                    break;
                case 10:
                    money = 22000000;
                    break;
                case 11:
                    money = 30000000;
                    break;
                case 12:
                    money = 40000000;
                    break;
                case 13:
                    money = 60000000;
                    break;
                case 14:
                    money = 85000000;
                    break;
                case 15:
                    money = 150000000;
                    break;
            }

        } else {
            switch (currentQuestion) {
                case 0:
                    money = 200000;
                    break;
                case 1:
                    money = 400000;
                    break;
                case 2:
                    money = 600000;
                    break;
                case 3:
                    money = 1000000;
                    break;
                case 4:
                    money = 2000000;
                    break;
                case 5:
                    money = 3000000;
                    break;
                case 6:
                    money = 6000000;
                    break;
                case 7:
                    money = 10000000;
                    break;
                case 8:
                    money = 14000000;
                    break;
                case 9:
                    money = 22000000;
                    break;
                case 10:
                    money = 30000000;
                    break;
                case 11:
                    money = 40000000;
                    break;
                case 12:
                    money = 60000000;
                    break;
                case 13:
                    money = 85000000;
                    break;
                case 14:
                    money = 150000000;
                    break;
            }
        }
        //sort by currentQuestion to set money

    }

    private void disableHelpCall() {
        btHelpCall.setImageResource(R.drawable.atp__activity_player_button_image_help_call_x);
        btHelpCall.setClickable(false);
    }

    private void helpCall() {
        playHelpCallSound();
        mCallDialog = new CallDialog(numberOfTrueAnswer);
        mCallDialog.setOnOkListener(this);
        mCallDialog.show(getFragmentManager(), MY_TAG);
    }

    private void playHelpCallSound() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.hoi_y_kien_chuyen_gia_01b);
        mMediaPlayer.start();
    }

    private void helpAudience() {
        playHelpAudienceSound();
        mAudienceDialog = new AudienceDialog(numberOfTrueAnswer);
        mAudienceDialog.setOnContinueGame(this);
        mAudienceDialog.show(getFragmentManager(), MY_TAG);
//        String audience = null;
//        int percentA = 0, percentB = 0, percentC = 0, percentD = 0;
//        switch (numberOfTrueAnswer) {
//            case 1:
//                percentA = random.nextInt(50) + 50;
//                percentB = random.nextInt(100 - percentA) + 1;
//                percentC = random.nextInt(100 - percentA - percentB);
//                percentD = 100 - percentA - percentB - percentC;
//                break;
//            case 2:
//                percentB = random.nextInt(50) + 50;
//                percentA = random.nextInt(100 - percentB) + 1;
//                percentC = random.nextInt(100 - percentA - percentB);
//                percentD = 100 - percentA - percentB - percentC;
//                break;
//            case 3:
//                percentC = random.nextInt(50) + 50;
//                percentA = random.nextInt(100 - percentC) + 1;
//                percentB = random.nextInt(100 - percentA - percentC);
//                percentD = 100 - percentA - percentB - percentC;
//                break;
//            case 4:
//                percentD = random.nextInt(50) + 50;
//                percentA = random.nextInt(100 - percentD) + 1;
//                percentC = random.nextInt(100 - percentA - percentD);
//                percentB = 100 - percentA - percentD - percentC;
//                break;
//        }
//        audience = "A: " + percentA + "\n" + "B: " + percentB + "\n" + "C: " + percentC + "\n" + "D: " + percentD;
//        showMessage(audience);
    }

    private void playHelpAudienceSound() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.khan_gia);
        mMediaPlayer.start();
    }

    private void disableHelpAudience() {
        btHelpAudience.setImageResource(R.drawable.atp__activity_player_button_image_help_audience_x);
        btHelpAudience.setClickable(false);
    }

    private void showMessage(String msg) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(getActivity());
        alertDialogBuilder.setMessage(msg);

        alertDialogBuilder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface arg0, int arg1) {

            }

        });

//        alertDialogBuilder.setNegativeButton("No", new DialogInterface.OnClickListener() {
//            @Override
//            public void onClick(DialogInterface dialog, int which) {
//                finish();
//            }
//        });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    private void disableHelp5050() {
        btHelp5050.setImageResource(R.drawable.atp__activity_player_button_image_help_5050_x);
        btHelp5050.setClickable(false);
    }

    private void disableTwoWrongAnswer() {
        playHelp5050Sound();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                int lucky = random.nextInt(3);
                switch (numberOfTrueAnswer) {
                    case 1:
                        switch (lucky) {
                            case 0:
                                tvCaseB.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                tvCaseB.setVisibility(View.INVISIBLE);
                                tvCaseD.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                tvCaseD.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                        }
                        break;
                    case 2:
                        switch (lucky) {
                            case 0:
                                tvCaseA.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                tvCaseA.setVisibility(View.INVISIBLE);
                                tvCaseD.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                tvCaseD.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                        }
                        break;
                    case 3:
                        switch (lucky) {
                            case 0:
                                tvCaseB.setVisibility(View.INVISIBLE);
                                tvCaseA.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                tvCaseA.setVisibility(View.INVISIBLE);
                                tvCaseD.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                tvCaseD.setVisibility(View.INVISIBLE);
                                tvCaseB.setVisibility(View.INVISIBLE);
                                break;
                        }
                        break;
                    case 4:
                        switch (lucky) {
                            case 0:
                                tvCaseB.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                            case 1:
                                tvCaseB.setVisibility(View.INVISIBLE);
                                tvCaseA.setVisibility(View.INVISIBLE);
                                break;
                            case 2:
                                tvCaseA.setVisibility(View.INVISIBLE);
                                tvCaseC.setVisibility(View.INVISIBLE);
                                break;
                        }
                        break;
                }
                continueTime();
            }
        }, 5000);
    }

    private void playHelp5050Sound() {
        mMediaPlayer.release();
        mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.sound5050);
        mMediaPlayer.start();
    }

    private void userChooseD() {
        tvCaseA.setClickable(false);
        tvCaseB.setClickable(false);
        tvCaseC.setClickable(false);
        tvCaseD.setClickable(false);
        answerUser = tvCaseD.getText().toString().substring(tvCaseD.getText().toString().indexOf(" ") + 1, tvCaseD.getText().toString().length());
        numberOfUserAnswer = 4;
    }

    private void userChooseC() {
        tvCaseA.setClickable(false);
        tvCaseB.setClickable(false);
        tvCaseC.setClickable(false);
        tvCaseD.setClickable(false);
        answerUser = tvCaseC.getText().toString().substring(tvCaseC.getText().toString().indexOf(" ") + 1, tvCaseC.getText().toString().length());
        numberOfUserAnswer = 3;
    }

    private void userChooseB() {
        tvCaseA.setClickable(false);
        tvCaseB.setClickable(false);
        tvCaseC.setClickable(false);
        tvCaseD.setClickable(false);
        answerUser = tvCaseB.getText().toString().substring(tvCaseB.getText().toString().indexOf(" ") + 1, tvCaseB.getText().toString().length());
        numberOfUserAnswer = 2;
    }


    private void userChooseA() {
        tvCaseA.setClickable(false);
        tvCaseB.setClickable(false);
        tvCaseC.setClickable(false);
        tvCaseD.setClickable(false);
        answerUser = tvCaseA.getText().toString().substring(tvCaseA.getText().toString().indexOf(" ") + 1, tvCaseA.getText().toString().length());
        numberOfUserAnswer = 1;
    }

    public String getQuestionTrue(QuestionItem question) {
        int caseTrue = question.getTrueCase();
        switch (caseTrue) {
            case 1:
                trueAnswer = question.getCaseA();
                break;
            case 2:
                trueAnswer = question.getCaseB();
                break;
            case 3:
                trueAnswer = question.getCaseC();
                break;
            case 4:
                trueAnswer = question.getCaseD();
                break;
        }
        return trueAnswer;
    }


    @Override
    public void startGame(Boolean aBoolean) {
        if (aBoolean == true) {
            mDialogStartGame.dismiss();
            mMediaPlayer.release();
            mMediaPlayer = MediaPlayer.create(getActivity(), R.raw.gofind);
            mMediaPlayer.start();
            showListViewPrice();
        }
    }

    @Override
    public void continueGame(Boolean aBoolean) {
        if (aBoolean == true) {
            continueTime();
        }
    }

    @Override
    public void OnOk(boolean b) {
        if (b == true) {
            continueTime();
        }
    }

    @Override
    public void onStopGame(boolean bool) {
        if (bool == true) {
            finishGame();

        } else {
            continueTime();
        }
    }
}
