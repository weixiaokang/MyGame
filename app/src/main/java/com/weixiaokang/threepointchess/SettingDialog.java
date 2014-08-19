package com.weixiaokang.threepointchess;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;

public class SettingDialog extends DialogFragment {

    public final static String SETTING = "setting";
    public final static String LEVEL_SETTING = "level setting";
    public final static String TURN_SETTING = "turn setting";
    public final static String MUSIC_SETTING = "music setting";
    public final static int FIRST_CHOICE = 0;
    public final static int SECOND_CHOICE = 1;

    private View dialogLayout;
    private RadioButton easyButton;
    private RadioButton hardButton;
    private RadioButton firstButton;
    private RadioButton lastButton;
    private RadioButton openButton;
    private RadioButton closeButton;
    private RadioGroup levelRadioGroup;
    private RadioGroup turnRadioGroup;
    private RadioGroup musicRadioGroup;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;

    private final static boolean DEBUG = true;
    private final static String TAG = "debug";

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {

        if (DEBUG) {
            Log.i(TAG, "-->onCreateDialog()");
        }

        initView();

        sharedPreferences = getActivity().getSharedPreferences(SETTING, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();

        checkPreferences();

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle(R.string.setting);
        builder.setPositiveButton(R.string.sure, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (levelRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.easy_button:
                        editor.putInt(LEVEL_SETTING, FIRST_CHOICE);
                        break;
                    case R.id.hard_button:
                        editor.putInt(LEVEL_SETTING, SECOND_CHOICE);
                        break;
                }
                switch (turnRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.first_button:
                        editor.putInt(TURN_SETTING, FIRST_CHOICE);
                        break;
                    case R.id.last_button:
                        editor.putInt(TURN_SETTING, SECOND_CHOICE);
                        break;
                }
                switch (musicRadioGroup.getCheckedRadioButtonId()) {
                    case R.id.open_button:
                        editor.putInt(MUSIC_SETTING, FIRST_CHOICE);
                        break;
                    case R.id.close_button:
                        editor.putInt(MUSIC_SETTING, SECOND_CHOICE);
                        break;
                }
                editor.commit();
            }
        });
        builder.setNegativeButton(R.string.cancle, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dismiss();
            }
        });
        builder.setView(dialogLayout);
        AlertDialog alertDialog = builder.create();
        return alertDialog;
    }

    /**
     * Initialize the view module;
     */
    private void initView() {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        dialogLayout = inflater.inflate(R.layout.settings_dialog, null);
        easyButton = (RadioButton) dialogLayout.findViewById(R.id.easy_button);
        hardButton = (RadioButton) dialogLayout.findViewById(R.id.hard_button);
        firstButton = (RadioButton) dialogLayout.findViewById(R.id.first_button);
        lastButton = (RadioButton) dialogLayout.findViewById(R.id.last_button);
        openButton = (RadioButton) dialogLayout.findViewById(R.id.open_button);
        closeButton = (RadioButton) dialogLayout.findViewById(R.id.close_button);
        levelRadioGroup = (RadioGroup) dialogLayout.findViewById(R.id.level_radio);
        turnRadioGroup = (RadioGroup) dialogLayout.findViewById(R.id.turn_radio);
        musicRadioGroup = (RadioGroup) dialogLayout.findViewById(R.id.music_radio);
    }

    /**
     * When create dialog every time,
     * the dialog will be initialized by information of xml;
     * So we should use checkPreferences() method
     * to load information that we put into SharePeferences;
     */
    private void checkPreferences(){
        int level = sharedPreferences.getInt(LEVEL_SETTING, FIRST_CHOICE);
        int turn = sharedPreferences.getInt(TURN_SETTING, FIRST_CHOICE);
        int music = sharedPreferences.getInt(MUSIC_SETTING, FIRST_CHOICE);
        switch (level) {
            case FIRST_CHOICE:
                easyButton.setChecked(true);
                break;
            case SECOND_CHOICE:
                hardButton.setChecked(true);
                break;
        }
        switch (turn) {
            case FIRST_CHOICE:
                firstButton.setChecked(true);
                break;
            case SECOND_CHOICE:
                 lastButton.setChecked(true);
                break;
        }
        switch (music) {
            case FIRST_CHOICE:
                openButton.setChecked(true);
                break;
            case SECOND_CHOICE:
                closeButton.setChecked(true);
                break;
        }
    }
}