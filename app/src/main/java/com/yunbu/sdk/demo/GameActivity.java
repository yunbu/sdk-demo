package com.yunbu.sdk.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.yunbu.game.sdk.GameSDK;
import com.yunbu.game.sdk.data.DataCallback;
import com.yunbu.game.sdk.data.GameInfo;
import com.yunbu.game.sdk.data.LevelInfo;

import org.json.JSONObject;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
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
        GameActivityPermissionsDispatcher.checkPermissionWithCheck(this);
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
        try {
            gameInfo.setLevel(Integer.parseInt(progress));
            gameInfo.setDiamond(Integer.parseInt(diamond));
            gameInfo.setGold(Integer.parseInt(gold));
            gameInfo.setScore(Integer.parseInt(score));
            if (!TextUtils.isEmpty(myPack)) {
                gameInfo.setInventory(new JSONObject(myPack));
            }
            if (!TextUtils.isEmpty(extra)) {
                gameInfo.setExtra(new JSONObject(extra));
            }

            String level = mLevelEdit.getText().toString();
            String levelScore = mLevelScoreEdit.getText().toString();
            String levelExtra = mLevelExtraEdit.getText().toString();

            levelInfo.setScore(Integer.parseInt(levelScore));
            levelInfo.setLevel(Integer.parseInt(level));
            levelInfo.setOccurTime(System.currentTimeMillis());
            if (!TextUtils.isEmpty(levelExtra)) {
                levelInfo.setExtra(new JSONObject(levelExtra));
            }
        } catch (Exception e) {
            Log.e(TAG, "Exception", e);
            gameInfo = null;
        }

        if (gameInfo == null) {
            return;
        }

        GameSDK.saveGameInfo(this.getApplicationContext(), gameInfo, levelInfo, new DataCallback<String>() {
            @Override
            public void onSuccess(String data) {
                Log.d(TAG, "Success:" + data);
                mResultEdit.setText(data, TextView.BufferType.EDITABLE);
            }

            @Override
            public void onFailed(String message) {
                Log.d(TAG, "failed:" + message);
                mResultEdit.setText(message, TextView.BufferType.EDITABLE);
            }
        });
    }

    public void getGame(View view) {
        GameSDK.loadGameInfo(this.getApplicationContext(), new DataCallback<GameInfo>() {
            @Override
            public void onSuccess(GameInfo data) {
                Log.d(TAG, "data:" + (data == null ? null : data.toJSON().toString()));
                mResultEdit.setText(parseGame(data), TextView.BufferType.EDITABLE);
            }

            @Override
            public void onFailed(String message) {
                mResultEdit.setText(message, TextView.BufferType.EDITABLE);
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


    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void checkPermission() {

    }

    @OnShowRationale({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void ACacheShowRationale(PermissionRequest request) {
        request.proceed();
    }

    @OnNeverAskAgain({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void ACacheOnNeverAskAgain() {
    }

    @OnPermissionDenied({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    void ACacheOnPermissionDenied() {
    }

    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        GameActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        Log.d(TAG, "onRequestPermissionsResult:" + requestCode);
        GameSDK.init(getApplicationContext());
    }
}
