package com.yunbu.sdk.demo;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.yunbu.sdk.demo.baseview.BaseRecyclerAdapter;
import com.yunbu.sdk.demo.baseview.DividerItemDecoration;
import com.yunbu.sdk.demo.baseview.RecyclerViewHolder;
import com.zeus.sdk.AresSDK;
import com.zeus.sdk.DataCallback;
import com.zeus.sdk.param.LevelInfo;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class LevelDetailActivity extends AppCompatActivity {
    private static final String TAG = LevelDetailActivity.class.getName();
    private SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    private Context mContext;
    private RecyclerView mRecyclerView;
    private LevelItemAdapter mAdapter;
    private LayoutInflater mInflater;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_level_detail);
        mContext = getApplicationContext();
        mRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        mAdapter = new LevelItemAdapter(this);
        mRecyclerView.setAdapter(mAdapter);
        mAdapter.setOnItemClickListener(new BaseRecyclerAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View itemView, int pos) {

            }
        });

        mRecyclerView.setLayoutManager(new
                LinearLayoutManager(LevelDetailActivity.this, LinearLayoutManager.VERTICAL, false)
        );
        mRecyclerView.addItemDecoration(new
                DividerItemDecoration(this, DividerItemDecoration.VERTICAL_LIST)
        );

    }

    @Override
    protected void onResume() {
        super.onResume();
        getLevelAsc();
    }

    private class LevelItemAdapter extends BaseRecyclerAdapter<LevelInfo> {
        public LevelItemAdapter(Context context) {
            super(context, null);
        }

        public void refreshData(List<LevelInfo> data) {
            mData.clear();
            mData.addAll(data);
            notifyDataSetChanged();
        }

        @Override
        public int getItemLayoutId(int viewType) {
            return R.layout.level_item;
        }

        @Override
        public void bindData(RecyclerViewHolder holder, int position, LevelInfo item) {
            if (item != null) {
                holder.setText(R.id.level, "" + item.getLevel());
                holder.setText(R.id.score, "" + item.getScore());
                String dateStr = format.format(new Date(item.getOccurTime()));
                holder.setText(R.id.time, dateStr);
                holder.setText(R.id.extra, item.getExtra());
            }
        }

    }

    private void getLevelAsc() {
//        AresSDK.getLevelInfo(this.getApplicationContext(), true, 1, 20, new DataCallback<List<LevelInfo>>() {
//            @Override
//            public void onSuccess(List<LevelInfo> data) {
//                if (data != null) {
//                    mAdapter.refreshData(data);
//                }
//            }
//
//            @Override
//            public void onFailed(int errCode, String message) {
//                Toast.makeText(mContext, "errCode:" + errCode + "  failed:" + message, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

    private void getLevelDesc() {
//        AresSDK.getLevelInfo(this.getApplicationContext(), false, 1, 20, new DataCallback<List<LevelInfo>>() {
//            @Override
//            public void onSuccess(List<LevelInfo> data) {
//                if (data != null) {
//                    mAdapter.refreshData(data);
//                }
//            }
//
//            @Override
//            public void onFailed(int errCode, String message) {
//                Toast.makeText(mContext, "errCode:" + errCode + "  failed:" + message, Toast.LENGTH_SHORT).show();
//            }
//        });
    }

}
