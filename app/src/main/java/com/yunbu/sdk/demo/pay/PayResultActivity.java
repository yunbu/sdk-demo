package com.yunbu.sdk.demo.pay;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import com.yunbu.sdk.demo.R;
import com.zeus.sdk.AresSDK;
import com.zeus.sdk.param.PayResult;

public class PayResultActivity extends AppCompatActivity {
    private static final String TAG = PayResultActivity.class.getName();
    private EditText mChannelEdit;
    private EditText mOrderIdEdit;
    private EditText mMessageEdit;
    private RadioGroup mResultGroup;
    private boolean mResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result);
        initUI();
    }

    private void initUI() {
        mChannelEdit = (EditText) findViewById(R.id.channel_edit);
        mOrderIdEdit = (EditText) findViewById(R.id.order_edit);
        mMessageEdit = (EditText) findViewById(R.id.message_edit);
        mResultGroup = (RadioGroup) findViewById(R.id.result_group);
    }

    public void saveData(View view) {
        Log.d(TAG, "save pay result");
        String channel = mChannelEdit.getText().toString();
        String orderId = mOrderIdEdit.getText().toString();
        String message = mMessageEdit.getText().toString();
        mResult = (mResultGroup.getCheckedRadioButtonId() == R.id.success);
        PayResult payResult = new PayResult(channel, orderId, mResult, message);
        AresSDK.logPayResult(getApplicationContext(), payResult);
    }
}
