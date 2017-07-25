package cn.xiong.showmessage.utils;

import android.os.AsyncTask;

import cn.xiong.showmessage.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/25 0025.
 */

public class BaseNewPayTask extends AsyncTask<Void,Void,Object> {

    private BaseActivity activity;

    public BaseNewPayTask(BaseActivity activity) {
        this.activity = activity;
    }


    @Override
    protected Object doInBackground(Void... params) {
        activity.doLongNewPayTask();
        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        activity.updateNewPayUI(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
