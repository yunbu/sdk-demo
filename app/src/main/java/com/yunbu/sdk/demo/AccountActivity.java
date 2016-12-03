package com.yunbu.sdk.demo;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.yunbu.sdk.demo.baseview.BaseRecyclerAdapter;
import com.yunbu.sdk.demo.baseview.DividerItemDecoration;
import com.yunbu.sdk.demo.baseview.RecyclerViewHolder;
import com.zeus.sdk.AresSDK;
import com.zeus.sdk.param.AccountInfo;

import java.util.ArrayList;
import java.util.List;

public class AccountActivity extends AppCompatActivity {
    private static final String TAG = AccountActivity.class.getName();
    private ImageView mAvatarView;
    private TextView mAccountView;
    private TextView mNicknameView;
    private Context mContext;
    private RecyclerView mRecyclerView;
    private AssertsItemAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        mContext = getApplicationContext();
        initUI();
        fillData();
    }

    private void initUI() {
        mAvatarView = (ImageView) findViewById(R.id.avatar);
        mAccountView = (TextView) findViewById(R.id.account_id);
        mNicknameView = (TextView) findViewById(R.id.nick_name);
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new AssertsItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {

            }
        });

        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(AccountActivity.this, LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.addItemDecoration(new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)
        );
    }

    private void fillData() {
        AccountInfo accountInfo = AresSDK.getAccount(mContext);
        if (accountInfo != null) {
            mNicknameView.setText(accountInfo.getNickName());
            mAccountView.setText(accountInfo.getAccountId());
        } else {
            mNicknameView.setText("Unknown");
            mAccountView.setText("Unknown");
        }
        Bitmap avatar = AresSDK.getAvatar(mContext);
        if (avatar != null) {
            mAvatarView.setImageBitmap(avatar);
        }

        getAccountAsserts();
    }

    private class AssertsItemAdapter extends BaseRecyclerAdapter<AssertsItem> {
        public AssertsItemAdapter(Context context) {
            super(context, null);
        }

        public void refreshData(List<AssertsItem> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.asserts_item;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, AssertsItem item) {
            if (item != null) {
                holder.setText(R.id.game_name, item.gameName);
                holder.setText(R.id.gold_count, "" + item.gold);
            }
        }

    }

    private void getAccountAsserts() {
        List<AssertsItem> data = new ArrayList<>();
        data.add(new AssertsItem("云币", 5000));
        mAdapter.refreshData(data);
    }

    private class AssertsItem {
        public String gameName;
        public int gold;

        public AssertsItem(String gameName, int gold) {
            this.gameName = gameName;
            this.gold = gold;
        }
    }
}
