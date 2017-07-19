package cn.xiong.showmessage.http;

/**
 * Created by Administrator on 2017/7/18.
 */

public interface HttpCallBack {

    void onSuccess(String json);

    void onFail(String message);
}
