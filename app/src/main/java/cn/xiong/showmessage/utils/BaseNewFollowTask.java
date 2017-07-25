package cn.xiong.showmessage.utils;

import android.app.Activity;
import android.os.AsyncTask;

import cn.xiong.showmessage.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class BaseNewFollowTask extends AsyncTask<Void,Void,Object> {

    private BaseActivity activity;

    public BaseNewFollowTask(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Void... params) {

        activity.doLongNewFollowTask();
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        activity.updateNewFollowUI(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
