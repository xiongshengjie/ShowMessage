package cn.xiong.showmessage.utils;

import android.os.AsyncTask;

import cn.xiong.showmessage.activity.BaseActivity;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class BaseTask extends AsyncTask<Void,Void,Object> {

    private BaseActivity activity;

    public BaseTask(BaseActivity activity) {
        this.activity = activity;
    }

    @Override
    protected Object doInBackground(Void... params) {

        activity.doLongTsk();

        return null;
    }

    @Override
    protected void onPostExecute(Object result) {
        super.onPostExecute(result);
        activity.updateUI(result);
    }

    @Override
    protected void onCancelled() {
        super.onCancelled();
    }
}
