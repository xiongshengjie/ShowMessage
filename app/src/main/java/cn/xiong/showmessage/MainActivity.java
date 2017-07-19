package cn.xiong.showmessage;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.xiong.showmessage.adpter.GeneralAdapter;
import cn.xiong.showmessage.adpter.NewAdapter;
import cn.xiong.showmessage.constant.ServerUrl;
import cn.xiong.showmessage.entity.BaseEntity;
import cn.xiong.showmessage.entity.General;
import cn.xiong.showmessage.entity.New;
import cn.xiong.showmessage.http.HttpCallBack;
import cn.xiong.showmessage.http.HttpGet;

public class MainActivity extends Activity {

    private RecyclerView leftView,newFollowView,newScanView,newPayView;
    private Gson gson = new Gson();
    private List<General> generalList = new ArrayList<General>();
    private List<New> newFollowList = new ArrayList<New>();
    private List<New> newScanList = new ArrayList<New>();
    private List<New> newPayList = new ArrayList<New>();
    private GeneralAdapter generalAdapter;
    private NewAdapter newFollowAdapter,newScanAdapter,newPayAdapter;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        initData();

    }

    public void initView(){
        leftView = (RecyclerView) findViewById(R.id.left_view);
        newFollowView = (RecyclerView) findViewById(R.id.new_follow_view);
        newScanView = (RecyclerView) findViewById(R.id.new_scan_view);
        newPayView = (RecyclerView) findViewById(R.id.new_pay_view);

        LinearLayoutManager leftLinearLayoutManager = new LinearLayoutManager(this);
        leftView.setLayoutManager(leftLinearLayoutManager);
        generalAdapter = new GeneralAdapter(MainActivity.this,generalList);
        leftView.setAdapter(generalAdapter);

        LinearLayoutManager newLinearLayoutManager = new LinearLayoutManager(this);
        newLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newFollowView.setLayoutManager(newLinearLayoutManager);
        newFollowAdapter = new NewAdapter(newFollowList,MainActivity.this);
        newFollowView.setAdapter(newFollowAdapter);

        LinearLayoutManager newScanLinearLayoutManager = new LinearLayoutManager(this);
        newScanLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newScanView.setLayoutManager(newScanLinearLayoutManager);
        newScanAdapter = new NewAdapter(newScanList,MainActivity.this);
        newScanView.setAdapter(newScanAdapter);

        LinearLayoutManager newPayLinearLayoutManager = new LinearLayoutManager(this);
        newPayLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newPayView.setLayoutManager(newPayLinearLayoutManager);
        newPayAdapter = new NewAdapter(newPayList,MainActivity.this);
        newPayView.setAdapter(newPayAdapter);

    }

    public void initData(){
        new HttpGet().getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());
        new HttpGet().getData(ServerUrl.GET_NEW_USER,new NewCallBack());
        new HttpGet().getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());
        new HttpGet().getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());
    }

    class GeneralCallBack implements HttpCallBack{

        @Override
        public void onSuccess(String json) {
            final BaseEntity<General> generalBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<General>>(){}.getType());

            generalList = generalBaseEntity.getData();
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    generalAdapter.setGeneralList(generalList);
                }
            });

            new HttpGet().getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());

        }

        @Override
        public void onFail(String message) {
            Log.d(TAG, "onFail: general"+message);
            new HttpGet().getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());
        }
    }

    class NewCallBack implements HttpCallBack{

        @Override
        public void onSuccess(String json) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newFollowList = newBaseEntity.getData();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    newFollowAdapter.setNewList(newFollowList);

                }
            });

            new HttpGet().getData(ServerUrl.GET_NEW_USER,new NewCallBack());

        }
        @Override
        public void onFail(String message) {
            Log.d(TAG, "onFail: follow"+message);
            new HttpGet().getData(ServerUrl.GET_NEW_USER,new NewCallBack());
        }
    }

    class NewScanCallBack implements HttpCallBack{

        @Override
        public void onSuccess(String json) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newScanList = newBaseEntity.getData();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    newScanAdapter.setNewList(newScanList);

                }
            });

            new HttpGet().getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());

        }
        @Override
        public void onFail(String message) {
            Log.d(TAG, "onFail: scan"+message);
            new HttpGet().getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());
        }
    }

    class NewPayCallBack implements HttpCallBack{

        @Override
        public void onSuccess(String json) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newPayList = newBaseEntity.getData();

            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    newPayAdapter.setNewList(newPayList);

                }
            });

            new HttpGet().getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());

        }
        @Override
        public void onFail(String message) {
            Log.d(TAG, "onFail: pay"+message);
            new HttpGet().getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());
        }
    }

}
