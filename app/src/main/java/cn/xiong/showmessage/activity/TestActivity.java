package cn.xiong.showmessage.activity;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;
import java.util.List;

import cn.xiong.showmessage.activity.TestActivity;
import cn.xiong.showmessage.R;
import cn.xiong.showmessage.adpter.GeneralAdapter;
import cn.xiong.showmessage.adpter.NewAdapter;
import cn.xiong.showmessage.constant.ServerUrl;
import cn.xiong.showmessage.entity.BaseEntity;
import cn.xiong.showmessage.entity.General;
import cn.xiong.showmessage.entity.New;
import cn.xiong.showmessage.http.HttpGet;
import cn.xiong.showmessage.utils.BaseTask;
import cn.xiong.showmessage.utils.HttpOperate;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class TestActivity extends BaseActivity {

    private RecyclerView leftView,newFollowView,newScanView,newPayView;
    private Gson gson = new Gson();
    private List<General> generalList = new ArrayList<General>();
    private List<New> newFollowList = new ArrayList<New>();
    private List<New> newScanList = new ArrayList<New>();
    private List<New> newPayList = new ArrayList<New>();
    private GeneralAdapter generalAdapter;
    private NewAdapter newFollowAdapter,newScanAdapter,newPayAdapter;
    private BaseTask task;
    private String resultGeneral,resultNewScan,resultNewPay,resultNewFollow;
    private BaseEntity<General> generalBaseEntity = null;
    private BaseEntity<New> newBaseEntity = null;


    private static final String TAG = "TestActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
        requestData();

    }

    public void initView(){
        leftView = (RecyclerView) findViewById(R.id.left_view);
        newFollowView = (RecyclerView) findViewById(R.id.new_follow_view);
        newScanView = (RecyclerView) findViewById(R.id.new_scan_view);
        newPayView = (RecyclerView) findViewById(R.id.new_pay_view);

        LinearLayoutManager leftLinearLayoutManager = new LinearLayoutManager(this);
        leftView.setLayoutManager(leftLinearLayoutManager);
        generalAdapter = new GeneralAdapter(TestActivity.this,generalList);
        leftView.setAdapter(generalAdapter);
        ((SimpleItemAnimator)leftView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newLinearLayoutManager = new LinearLayoutManager(this);
        newLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newFollowView.setLayoutManager(newLinearLayoutManager);
        newFollowAdapter = new NewAdapter(newFollowList,TestActivity.this);
        newFollowView.setAdapter(newFollowAdapter);
        ((SimpleItemAnimator)newFollowView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newScanLinearLayoutManager = new LinearLayoutManager(this);
        newScanLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newScanView.setLayoutManager(newScanLinearLayoutManager);
        newScanAdapter = new NewAdapter(newScanList,TestActivity.this);
        newScanView.setAdapter(newScanAdapter);
        ((SimpleItemAnimator)newScanView.getItemAnimator()).setSupportsChangeAnimations(false);

        LinearLayoutManager newPayLinearLayoutManager = new LinearLayoutManager(this);
        newPayLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        newPayView.setLayoutManager(newPayLinearLayoutManager);
        newPayAdapter = new NewAdapter(newPayList,TestActivity.this);
        newPayView.setAdapter(newPayAdapter);
        ((SimpleItemAnimator)newPayView.getItemAnimator()).setSupportsChangeAnimations(false);

    }

    public void requestData(){
        task = new BaseTask(this);
        task.execute();
    }

    @Override
    public Object doLongTsk() {

        resultGeneral = HttpOperate.getInstance().HttpGet(ServerUrl.GET_GENERAL_DATA);
        resultNewFollow = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_USER);
        resultNewPay = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_ORDER);
        resultNewScan = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_SCAN);

        return null;
    }

    @Override
    public void updateUI(Object result) {
        super.updateUI(result);

        if(!TextUtils.isEmpty(resultGeneral)) {
            generalBaseEntity = gson.fromJson(resultGeneral, new TypeToken<BaseEntity<General>>() {}.getType());
            generalList = generalBaseEntity.getData();
            generalAdapter.setGeneralList(generalList);
        }

        if(!TextUtils.isEmpty(resultNewFollow)) {
            newBaseEntity = gson.fromJson(resultNewFollow, new TypeToken<BaseEntity<New>>() {}.getType());
            newFollowList = newBaseEntity.getData();
            newFollowAdapter.setNewList(newFollowList);
        }


        if (!TextUtils.isEmpty(resultNewScan)) {
            newBaseEntity = gson.fromJson(resultNewScan, new TypeToken<BaseEntity<New>>() {}.getType());
            newScanList = newBaseEntity.getData();
            newScanAdapter.setNewList(newScanList);
        }

        if(!TextUtils.isEmpty(resultNewPay)) {
            newBaseEntity = gson.fromJson(resultNewPay, new TypeToken<BaseEntity<New>>() {}.getType());
            newPayList = newBaseEntity.getData();
            newPayAdapter.setNewList(newPayList);
        }

        requestData();

    }

    @Override
    protected void onStop() {
        task.cancel(true);
        super.onStop();
    }
}
