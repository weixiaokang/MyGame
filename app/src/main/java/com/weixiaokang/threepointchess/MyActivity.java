package com.weixiaokang.threepointchess;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;


public class MyActivity extends Activity implements View.OnClickListener {

    private final static boolean DEBUG = true;
    private final static String TAG = "debug";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);

        if (DEBUG) { Log.i(TAG, "-->onCreate()"); }

        SharedPreferences sharedPreferences = getSharedPreferences(SettingDialog.SETTING, MODE_PRIVATE);
        int level = sharedPreferences.getInt(SettingDialog.LEVEL_SETTING, SettingDialog.FIRST_CHOICE);
        int turn = sharedPreferences.getInt(SettingDialog.TURN_SETTING, SettingDialog.FIRST_CHOICE);
        int music = sharedPreferences.getInt(SettingDialog.MUSIC_SETTING, SettingDialog.FIRST_CHOICE);
        if (DEBUG) {
            Log.i(TAG, String.valueOf(level)+" "+String.valueOf(turn)+" "+String.valueOf(music));
        }

        Button ctnButton = (Button) findViewById(R.id.ctn_button);
        ctnButton.setOnClickListener(this);
        Button ngButton = (Button) findViewById(R.id.new_game_button);
        ngButton.setOnClickListener(this);
        Button exitButton = (Button) findViewById(R.id.exit_button);
        exitButton.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.my, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            new SettingDialog().show(getFragmentManager(), "setting");
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onClick(View v) {
        Intent intent = new Intent(MyActivity.this, GameActivity.class);
        Bundle bundle = new Bundle();
        switch (v.getId()) {
            case R.id.ctn_button:
                bundle.putBoolean(GameActivity.CONTINUE, true);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.new_game_button:
                bundle.putBoolean(GameActivity.CONTINUE, false);
                intent.putExtras(bundle);
                startActivity(intent);
                break;
            case R.id.exit_button:
                finish();
                break;
        }
    }
}