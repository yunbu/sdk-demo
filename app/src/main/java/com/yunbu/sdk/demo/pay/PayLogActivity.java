package com.yunbu.sdk.demo.pay;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.alibaba.fastjson.JSON;
import com.yunbu.sdk.demo.R;
import com.zeus.sdk.AresSDK;
import com.zeus.sdk.param.PayParam;

public class PayLogActivity extends AppCompatActivity {
    private static final String TAG = PayLogActivity.class.getName();
    private Context mContext;
    private EditText mChannelEdit;
    private EditText mOrderEdit;
    private EditText mProductIdEdit;
    private EditText mProductNameEdit;
    private EditText mProductDescEdit;
    private EditText mPriceEdit;
    private EditText mCountEdit;
    private EditText mRestCoinEdit;
    private EditText mExtraEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_log);
        mContext = getApplicationContext();
        initUI();
    }

    private void initUI() {
        mChannelEdit = (EditText) findViewById(R.id.channel_edit);
        mOrderEdit = (EditText) findViewById(R.id.order_edit);
        mProductIdEdit = (EditText) findViewById(R.id.product_id);
        mProductNameEdit = (EditText) findViewById(R.id.product_name);
        mProductDescEdit = (EditText) findViewById(R.id.product_desc);
        mPriceEdit = (EditText) findViewById(R.id.price_edit);
        mCountEdit = (EditText) findViewById(R.id.count_edit);
        mRestCoinEdit = (EditText) findViewById(R.id.rest_coin_edit);
        mExtraEdit = (EditText) findViewById(R.id.extra_edit);
    }

    public void saveData(View view) {

        String channel = mChannelEdit.getText().toString();
        String orderId = mOrderEdit.getText().toString();
        String productId = mProductIdEdit.getText().toString();
        String productName = mProductNameEdit.getText().toString();
        String productDesc = mProductDescEdit.getText().toString();
        String priceStr = mPriceEdit.getText().toString();
        String countStr = mCountEdit.getText().toString();
        String restCoinStr = mRestCoinEdit.getText().toString();
        String extra = mExtraEdit.getText().toString();
        int price = Integer.parseInt(priceStr);
        int count = Integer.parseInt(countStr);
        int restCoin = Integer.parseInt(restCoinStr);
        Log.d(TAG, "save pay log:" + price);
        PayParam payParam = new PayParam(channel, orderId);
        payParam.setProductId(productId);
        payParam.setProductName(productName);
        payParam.setProductDesc(productDesc);
        payParam.setPrice(price);
        payParam.setBuyNum(count);
        payParam.setCoinNum(restCoin);
        payParam.setExtra(extra);
        payParam.setOccurTime(System.currentTimeMillis());
        Log.d(TAG, "pay:" + JSON.toJSONString(payParam));
        AresSDK.logPay(mContext, payParam);
    }

}
