package com.yunbu.sdk.demo;

import android.Manifest;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.yunbu.sdk.demo.pay.PayLogActivity;
import com.yunbu.sdk.demo.pay.PayResultActivity;
import com.zeus.sdk.AresSDK;

import permissions.dispatcher.NeedsPermission;
import permissions.dispatcher.OnNeverAskAgain;
import permissions.dispatcher.OnPermissionDenied;
import permissions.dispatcher.OnShowRationale;
import permissions.dispatcher.PermissionRequest;
import permissions.dispatcher.RuntimePermissions;

@RuntimePermissions
public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MainActivityPermissionsDispatcher.checkPermissionWithCheck(this);
//        RequestManager.getInstance().init(getApplicationContext());
    }

    public void init(View view) {
        AresSDK.init(this.getApplicationContext());
    }


    public void devApp(View view) {
    }

    public void gameInfo(View view) {
        startActivity(new Intent(this, GameActivity.class));
    }

    public void showAccount(View view) {
        startActivity(new Intent(this, AccountActivity.class));
    }

    public void payLog(View view) {
        startActivity(new Intent(this, PayLogActivity.class));
    }

    public void payResult(View view) {
        startActivity(new Intent(this, PayResultActivity.class));
    }


    @NeedsPermission({Manifest.permission.READ_PHONE_STATE, Manifest.permission.WRITE_EXTERNAL_STORAGE})
    public void checkPermission() {
        Log.d(TAG, "checkPermission");
        AresSDK.init(this.getApplicationContext());
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

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        MainActivityPermissionsDispatcher.onRequestPermissionsResult(this, requestCode, grantResults);
        Log.d(TAG, "onRequestPermissionsResult:" + requestCode);
    }


}
