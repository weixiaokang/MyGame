package com.weixiaokang.threepointchess;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import java.util.LinkedList;


public class GameActivity extends Activity implements View.OnClickListener{

    private final static boolean DEBUG = true;
    private final static String TAG = "debug";
    private final static String FIRST_FLAG = "O";
    private final static String SECOND_FLAG = "X";
    private final static String START_GAME = "000000000";
    private final static String TEXT = "hello";

    private int level, turn, music;
    private boolean canClick, firstStep;
    private Button btn[] = new Button[9];
    private Button resetButton, settingButton, menuButton;
    private SharedPreferences sharedPreferences;
    private StringBuilder game;
    private LinkedList<Integer> arrayList;

    public final static String CONTINUE = "continue";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        initView();
        setDate();

        boolean isContinue = getIntent().getExtras().getBoolean(CONTINUE);
        if (isContinue) {
            sharedPreferences = getSharedPreferences("game", MODE_PRIVATE);
            game.replace(0, 9, sharedPreferences.getString(CONTINUE, START_GAME));
            drawView(game);
        }
            setOnClickListener();
    }

    /**
     * reset the view when game over and click the reset button;
     */
    private void setDate() {
        firstStep = true;
        arrayList = new LinkedList<Integer>();
        for (int i = 0; i < 9; i++) {
            arrayList.add(i);
        }
        if (game == null) {
            game = new StringBuilder(START_GAME);
        } else {
            game.replace(0, 9, START_GAME);
        }
        drawView(game);
        loadSettingInfo();
        if (turn == 0) {
            canClick = true;
        } else {
            canClick = false;
        }
        if (turn == 1) {
            aiMakeChoice();
        }
    }
    /**
     * Initialize the module
     */
    private void initView() {
        btn[0] = (Button) findViewById(R.id.button0);
        btn[1] = (Button) findViewById(R.id.button1);
        btn[2] = (Button) findViewById(R.id.button2);
        btn[3] = (Button) findViewById(R.id.button3);
        btn[4] = (Button) findViewById(R.id.button4);
        btn[5] = (Button) findViewById(R.id.button5);
        btn[6] = (Button) findViewById(R.id.button6);
        btn[7] = (Button) findViewById(R.id.button7);
        btn[8] = (Button) findViewById(R.id.button8);
        resetButton = (Button) findViewById(R.id.restart);
        settingButton = (Button) findViewById(R.id.botton_setting);
        menuButton = (Button) findViewById(R.id.main_menu);
    }

    /**
     * all buttons set OnClickListener
     */
    private void setOnClickListener() {
        for (int i = 0; i < 9; i++) {
            btn[i].setOnClickListener(this);
        }
        resetButton.setOnClickListener(this);
        settingButton.setOnClickListener(this);
        menuButton.setOnClickListener(this);
    }

    /**
     * when click the one of nine game button,
     * the view feedback;
     * @param button the button you click
     * @param i the number of button you click
     */
    private void setOnClick(Button button, int i) {
        if (canClick) {
            firstStep = false;
            if (!hasSelected(button)) {
                if (turn == 0) {
                    button.setText(FIRST_FLAG);
                    game.setCharAt(i, '1');
                    canClick = false;
                } else {
                    button.setText(SECOND_FLAG);
                    game.setCharAt(i, '2');
                    canClick = false;
                }

                checkIfSuccess();
                if (!firstStep) {
                    aiMakeChoice();
                }
                checkIfSuccess();
            }
        }
    }

    /**
     * check if the game should be over
     */
    private void checkIfSuccess() {
        if (DEBUG) { Log.i(TAG, "-->check:"+game); }
        char me, ai;
        if (turn == 0) {
            me = '1';
            ai = '2';
        } else {
            ai = '1';
            me = '2';
        }
        int success = 0;
        for (int i = 0; i < 3; i++) {
            if (game.charAt(i) == me
                    &&game.charAt(i + 3) == me
                    &&game.charAt(i + 6) == me) {
                success = 1;
            }
            if (game.charAt(i) == ai
                    &&game.charAt(i + 3) == ai
                    &&game.charAt(i + 6) == ai) {
                success = 2;
            }
        }
        for (int i = 0; i <= 6; i = i + 3) {
            if (game.charAt(i) == me
                    &&game.charAt(i + 1) == me
                    &&game.charAt(i + 2) == me) {
                success = 1;
            }
            if (game.charAt(i) == ai
                    &&game.charAt(i + 1) == ai
                    &&game.charAt(i + 2) == ai) {
                success = 2;
            }
        }
        if (game.charAt(0) == me
                &&game.charAt(4) == me
                &&game.charAt(8) ==me) {
            success = 1;
        }
        if (game.charAt(0) == ai
                &&game.charAt(4) == ai
                &&game.charAt(8) == ai) {
            success = 2;
        }
        if (game.charAt(2) == me
                &&game.charAt(4) == me
                &&game.charAt(6) == me) {
            success = 1;
        }
        if (game.charAt(2) == ai
                &&game.charAt(4) == ai
                &&game.charAt(6) == ai) {
            success = 2;
        }
        int noUsedCount = 0;
        for (int i = 0; i < 9; i++) {
            if (game.charAt(i) == '0') {
                noUsedCount++;
            }
        }
        if (success == 1) {
            new GameEndDialog(this, R.string.success).showDialog();
            setDate();
        } else if (success == 2){
            new GameEndDialog(this, R.string.lose).showDialog();
            setDate();
        } else if (noUsedCount == 0) {
            new GameEndDialog(this, R.string.dogfall).showDialog();
            setDate();
        }
    }

    /**
     * draw the button according to last information;
     * @param game the last game;
     */
    private void drawView(StringBuilder game) {
        for (int i = 0; i < 9; i++) {
            if (game.charAt(i) == '0') {
                btn[i].setText("");
            } else if (game.charAt(i) == '1') {
                btn[i].setText(FIRST_FLAG);
            } else if (game.charAt(i) == '2') {
                btn[i].setText(SECOND_FLAG);
            }
        }
    }

    /**
     * get the setting information;
     */
    private void loadSettingInfo() {
        sharedPreferences = getSharedPreferences(SettingDialog.SETTING, MODE_PRIVATE);
        level = sharedPreferences.getInt(SettingDialog.LEVEL_SETTING, SettingDialog.FIRST_CHOICE);
        turn = sharedPreferences.getInt(SettingDialog.TURN_SETTING, SettingDialog.FIRST_CHOICE);
        music = sharedPreferences.getInt(SettingDialog.MUSIC_SETTING, SettingDialog.FIRST_CHOICE);
    }

    /**
     * judge whether the button has been selected;
     * @param button the button you choose;
     * @return selected return false, not selected return ture;
     */
    private boolean hasSelected(Button button) {
        if (button.getText().toString().equals("")) {
           return false;
        } else {
            return true;
        }
    }

    /**
     * random make a choice for ai
     * @param a the list of button ai can choice
     */
    private void randomSelect(LinkedList<Integer> a) {
        int i = (int)(Math.random()*a.size());
        if (DEBUG) { Log.i(TAG, "-->"+a.size()+":"+i); }
        if (hasSelected(btn[a.get(i)]) && a.size() != 0) {
            a.remove(i);
            randomSelect(a);
        } else if (a.size() != 0) {
            if (turn == 0) {
                btn[a.get(i)].setText(SECOND_FLAG);
                game.setCharAt(a.get(i), '2');
                canClick = true;
                a.remove(i);
            } else {
                btn[a.get(i)].setText(FIRST_FLAG);
                game.setCharAt(a.get(i), '1');
                canClick = true;
                a.remove(i);
            }
            if (DEBUG) { Log.i(TAG, ""+game.charAt(i)); }
        }
    }

    private void aiMakeChoice() {
        android.os.Handler handler = new android.os.Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                randomSelect(arrayList);
            }
        }, 1000);
    }
    @Override
    protected void onPause() {
        super.onPause();
        sharedPreferences = getSharedPreferences("game", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(CONTINUE, game.toString());
        editor.apply();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Button button = (Button) v;
        switch (button.getId()) {
            case R.id.button0:
                setOnClick(button, 0);
                break;
            case R.id.button1:
                setOnClick(button, 1);
                break;
            case R.id.button2:
                setOnClick(button, 2);
                break;
            case R.id.button3:
                setOnClick(button, 3);
                break;
            case R.id.button4:
                setOnClick(button, 4);
                break;
            case R.id.button5:
                setOnClick(button, 5);
                break;
            case R.id.button6:
                setOnClick(button, 6);
                break;
            case R.id.button7:
                setOnClick(button, 7);
                break;
            case R.id.button8:
                setOnClick(button, 8);
                break;
            case R.id.restart:
                setDate();
                break;
            case R.id.botton_setting:
                new SettingDialog().show(getFragmentManager(), "setting");
                setDate();
                break;
            case R.id.main_menu:
                finish();
                break;
        }
    }
}