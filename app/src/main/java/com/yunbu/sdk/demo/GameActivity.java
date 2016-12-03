package com.yunbu.sdk.demo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.zeus.sdk.AresSDK;
import com.zeus.sdk.DataCallback;
import com.zeus.sdk.param.GameInfo;
import com.zeus.sdk.param.LevelInfo;

public class GameActivity extends AppCompatActivity {
    private static final String TAG = GameActivity.class.getName();
    private EditText mProgressEdit;
    private EditText mDiamondEdit;
    private EditText mGoldEdit;
    private EditText mScoreEdit;
    private EditText mPackEdit;
    private EditText mExtraEdit;
    private EditText mLevelEdit;
    private EditText mLevelScoreEdit;
    private EditText mLevelExtraEdit;

    private EditText mResultEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        mProgressEdit = (EditText) findViewById(R.id.progress_edit);
        mDiamondEdit = (EditText) findViewById(R.id.diamond_edit);
        mGoldEdit = (EditText) findViewById(R.id.gold_edit);
        mScoreEdit = (EditText) findViewById(R.id.score_edit);
        mPackEdit = (EditText) findViewById(R.id.package_edit);
        mExtraEdit = (EditText) findViewById(R.id.custom_edit);
        mLevelEdit = (EditText) findViewById(R.id.level_edit);
        mLevelScoreEdit = (EditText) findViewById(R.id.level_score_edit);
        mLevelExtraEdit = (EditText) findViewById(R.id.custom_level_edit);

        mResultEdit = (EditText) findViewById(R.id.result);
    }

    public void saveGame(View view) {
        String progress = mProgressEdit.getText().toString();
        String diamond = mDiamondEdit.getText().toString();
        String gold = mGoldEdit.getText().toString();
        String score = mScoreEdit.getText().toString();
        String myPack = mPackEdit.getText().toString();
        String extra = mExtraEdit.getText().toString();

        GameInfo gameInfo = new GameInfo();
        LevelInfo levelInfo = new LevelInfo();
        gameInfo.setLevel(Integer.parseInt(progress));
        gameInfo.setDiamond(Integer.parseInt(diamond));
        gameInfo.setGold(Integer.parseInt(gold));
        gameInfo.setScore(Integer.parseInt(score));
        gameInfo.setInventory(myPack);
        gameInfo.setExtra(extra);

        String level = mLevelEdit.getText().toString();
        String levelScore = mLevelScoreEdit.getText().toString();
        String levelExtra = mLevelExtraEdit.getText().toString();

        levelInfo.setScore(Integer.parseInt(levelScore));
        levelInfo.setLevel(Integer.parseInt(level));
        levelInfo.setOccurTime(System.currentTimeMillis());
        levelInfo.setExtra(levelExtra);

        AresSDK.saveGameInfo(this.getApplicationContext(), gameInfo, levelInfo, new DataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "Success:" + data);
                mResultEdit.setText(data, TextView.BufferType.EDITABLE);
            }

            @Override
            public void onFailed(int errCode, String message) {
                Log.d(TAG, "errCode:" + errCode + "  failed:" + message);
                mResultEdit.setText(message, TextView.BufferType.EDITABLE);
            }
        });
    }

    public void getGame(View view) {
        AresSDK.loadGameInfo(this.getApplicationContext(), new DataCallback<GameInfo>() {
            @Override
            public void onSuccess(GameInfo data) {
                Log.d(TAG, "data:" + (data == null ? null : JSON.toJSONString(data)));
                mResultEdit.setText(parseGame(data), TextView.BufferType.EDITABLE);
            }

            @Override
            public void onFailed(int errCode, String message) {
                mResultEdit.setText("errCode:" + errCode + "  failed:" + message, TextView.BufferType.EDITABLE);
            }
        });
    }

    public void toLevel(View view) {
        Intent intent = new Intent(this, LevelDetailActivity.class);
        startActivity(intent);
    }

    private String parseGame(GameInfo game) {
        if (game != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("进度：");
            sb.append(game.getLevel());
            sb.append("\n");
            sb.append("钻石：");
            sb.append(game.getDiamond());
            sb.append("\n");
            sb.append("金币：");
            sb.append(game.getGold());
            sb.append("\n");
            sb.append("分数：");
            sb.append(game.getScore());
            sb.append("\n");
            sb.append("道具：");
            sb.append(game.getInventory());
            sb.append("\n");
            sb.append("其它：");
            sb.append(game.getExtra());
            sb.append("\n");
            return sb.toString();
        } else {
            return "ERROR!";
        }
    }


}
