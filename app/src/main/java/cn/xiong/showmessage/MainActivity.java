package cn.xiong.showmessage;

import android.app.Activity;
import android.os.Handler;
import android.os.Message;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
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
import cn.xiong.showmessage.http.NetThead;

public class MainActivity extends Activity {

    private RecyclerView leftView,newFollowView,newScanView,newPayView;
    private Gson gson = new Gson();
    private List<General> generalList = new ArrayList<General>();
    private List<New> newFollowList = new ArrayList<New>();
    private List<New> newScanList = new ArrayList<New>();
    private List<New> newPayList = new ArrayList<New>();
    private GeneralAdapter generalAdapter;
    private NewAdapter newFollowAdapter,newScanAdapter,newPayAdapter;

    private Handler handler;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);

                int what = msg.what;
                int arg1 = msg.arg1;
                if(arg1 == 0){
                    if(what == 0){
                        generalAdapter.setGeneralList(generalList);
                        HttpGet.getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());
                    }else if(what == 1){
                        newFollowAdapter.setNewList(newFollowList);
                        HttpGet.getData(ServerUrl.GET_NEW_USER,new NewCallBack());
                    }else if(what == 2 ){
                        newScanAdapter.setNewList(newScanList);
                        HttpGet.getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());
                    }else if(what == 3){
                        newPayAdapter.setNewList(newPayList);
                        HttpGet.getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());
                    }
                }else{
                    if(what == 0){
                        HttpGet.getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());
                    }else if(what == 1){
                        HttpGet.getData(ServerUrl.GET_NEW_USER,new NewCallBack());
                    }else if(what == 2 ){
                        HttpGet.getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());
                    }else if(what == 3){
                        HttpGet.getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());
                    }
                }

            }
        };


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
        ((SimpleItemAnimator)leftView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newLinearLayoutManager = new LinearLayoutManager(this);
        newLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newFollowView.setLayoutManager(newLinearLayoutManager);
        newFollowAdapter = new NewAdapter(newFollowList,MainActivity.this);
        newFollowView.setAdapter(newFollowAdapter);
        ((SimpleItemAnimator)newFollowView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newScanLinearLayoutManager = new LinearLayoutManager(this);
        newScanLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newScanView.setLayoutManager(newScanLinearLayoutManager);
        newScanAdapter = new NewAdapter(newScanList,MainActivity.this);
        newScanView.setAdapter(newScanAdapter);
        ((SimpleItemAnimator)newScanView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newPayLinearLayoutManager = new LinearLayoutManager(this);
        newPayLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newPayView.setLayoutManager(newPayLinearLayoutManager);
        newPayAdapter = new NewAdapter(newPayList,MainActivity.this);
        newPayView.setAdapter(newPayAdapter);
        ((SimpleItemAnimator)newPayView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    public void initData(){
        HttpGet.getData(ServerUrl.GET_GENERAL_DATA,new GeneralCallBack());
        HttpGet.getData(ServerUrl.GET_NEW_USER,new NewCallBack());
        HttpGet.getData(ServerUrl.GET_NEW_SCAN,new NewScanCallBack());
        HttpGet.getData(ServerUrl.GET_NEW_ORDER,new NewPayCallBack());
    }

    class GeneralCallBack implements HttpCallBack{

        Message msg = new Message();
        @Override
        public void onSuccess(String json,NetThead NetThead) {
            final BaseEntity<General> generalBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<General>>(){}.getType());

            generalList = generalBaseEntity.getData();
            msg.what = 0;
            msg.arg1 = 0;

            handler.sendMessage(msg);
        }

        @Override
        public void onFail(String message,NetThead NetThead) {
            Log.d(TAG, "onFail: general"+message);
            msg.what = 0;
            msg.arg1 = 1;

            handler.sendMessage(msg);
        }
    }

    class NewCallBack implements HttpCallBack{

        Message msg = new Message();

        @Override
        public void onSuccess(String json,NetThead NetThead) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newFollowList = newBaseEntity.getData();

            msg.what = 1;
            msg.arg1 = 0;

            handler.sendMessage(msg);
        }
        @Override
        public void onFail(String message,NetThead NetThead) {
            Log.d(TAG, "onFail: follow"+message);

            msg.what = 1;
            msg.arg1 = 1;

            handler.sendMessage(msg);
        }
    }

    class NewScanCallBack implements HttpCallBack{

        Message msg = new Message();
        @Override
        public void onSuccess(String json,NetThead NetThead) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newScanList = newBaseEntity.getData();

            msg.what = 2;
            msg.arg1 = 0;

            handler.sendMessage(msg);
        }
        @Override
        public void onFail(String message,NetThead NetThead) {
            Log.d(TAG, "onFail: scan"+message);
            msg.what = 2;
            msg.arg1 = 1;

            handler.sendMessage(msg);
        }
    }

    class NewPayCallBack implements HttpCallBack{

        Message msg = new Message();
        @Override
        public void onSuccess(String json,NetThead NetThead) {
            final BaseEntity<New> newBaseEntity = gson.fromJson(json,new TypeToken<BaseEntity<New>>(){}.getType());
            newPayList = newBaseEntity.getData();

            msg.what = 3;
            msg.arg1 = 0;

            handler.sendMessage(msg);
        }
        @Override
        public void onFail(String message,NetThead NetThead) {
            Log.d(TAG, "onFail: pay"+message);
            msg.what = 3;
            msg.arg1 = 1;

            handler.sendMessage(msg);
            
        }
    }

}
