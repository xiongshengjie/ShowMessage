package cn.xiong.showmessage.utils;

import android.os.AsyncTask;

import cn.xiong.showmessage.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class BaseNewScanTask  extends AsyncTask<Void,Void,Object>{

    private BaseActivity activity;

    public BaseNewScanTask(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    protected Object doInBackground(Void... params) {
        activity.doLongNewScanTask();
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        activity.updateNewScanUI(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
