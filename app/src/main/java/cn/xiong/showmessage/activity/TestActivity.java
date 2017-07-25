package cn.xiong.showmessage.activity;

import android.animation.AnimatorSet;
import android.media.Image;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SimpleItemAnimator;
import android.text.TextUtils;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;

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
import cn.xiong.showmessage.utils.BaseNewFollowTask;
import cn.xiong.showmessage.utils.BaseNewPayTask;
import cn.xiong.showmessage.utils.BaseNewScanTask;
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
    private BaseNewFollowTask newFollowTask;
    private BaseNewScanTask newScanTask;
    private BaseNewPayTask newPayTask;
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
        requestNewFollowData();
        requestNewScanData();
        requestNewPayData();

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

        ImageView imageViewTop = (ImageView) findViewById(R.id.star_back);
        ImageView imageViewBottom = (ImageView) findViewById(R.id.star_back_bottom);
        Animation animationTop = AnimationUtils.loadAnimation(this,R.anim.star_background);
        Animation animationBottom = AnimationUtils.loadAnimation(this,R.anim.star_back);

        imageViewTop.startAnimation(animationTop);
        imageViewBottom.startAnimation(animationBottom);

        ImageView background = (ImageView) findViewById(R.id.background);
        Animation animation = AnimationUtils.loadAnimation(this,R.anim.background);
        background.startAnimation(animation);
    }

    public void requestData(){
        task = new BaseTask(this);
        task.execute();

    }

    public void requestNewFollowData(){
        newFollowTask = new BaseNewFollowTask(this);
        newFollowTask.execute();
    }

    public void requestNewScanData(){
        newScanTask = new BaseNewScanTask(this);
        newScanTask.execute();
    }

    public void requestNewPayData(){
        newPayTask = new BaseNewPayTask(this);
        newPayTask.execute();
    }

    @Override
    public Object doLongTsk() {

        resultGeneral = HttpOperate.getInstance().HttpGet(ServerUrl.GET_GENERAL_DATA);
        return null;
    }

    @Override
    public Object doLongNewFollowTask() {
        resultNewFollow = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_USER);
        return null;
    }

    @Override
    public Object doLongNewScanTask() {
        resultNewScan = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_SCAN);
        return null;
    }

    @Override
    public Object doLongNewPayTask() {
        resultNewPay = HttpOperate.getInstance().HttpGet(ServerUrl.GET_NEW_ORDER);
        return super.doLongNewPayTask();
    }

    @Override
    public void updateUI(Object result) {
        super.updateUI(result);

        if(!TextUtils.isEmpty(resultGeneral)) {
            generalBaseEntity = gson.fromJson(resultGeneral, new TypeToken<BaseEntity<General>>() {}.getType());
            generalList = generalBaseEntity.getData();
            generalAdapter.setGeneralList(generalList);
        }
        requestData();

    }

    @Override
    public void updateNewFollowUI(Object result) {
        if(!TextUtils.isEmpty(resultNewFollow)) {
            newBaseEntity = gson.fromJson(resultNewFollow, new TypeToken<BaseEntity<New>>() {}.getType());
            newFollowList = newBaseEntity.getData();
            newFollowAdapter.setNewList(newFollowList);
        }
        requestNewFollowData();
    }

    @Override
    public void updateNewScanUI(Object result) {
        if (!TextUtils.isEmpty(resultNewScan)) {
            newBaseEntity = gson.fromJson(resultNewScan, new TypeToken<BaseEntity<New>>() {}.getType());
            newScanList = newBaseEntity.getData();
            newScanAdapter.setNewList(newScanList);
        }
        requestNewScanData();
    }

    @Override
    public void updateNewPayUI(Object result) {
        if(!TextUtils.isEmpty(resultNewPay)) {
            newBaseEntity = gson.fromJson(resultNewPay, new TypeToken<BaseEntity<New>>() {}.getType());
            newPayList = newBaseEntity.getData();
            newPayAdapter.setNewList(newPayList);
        }
        requestNewPayData();
    }

    @Override
    protected void onStop() {
        task.cancel(true);
        newFollowTask.cancel(true);
        newScanTask.cancel(true);
        newPayTask.cancel(true);
        super.onStop();
    }
}
