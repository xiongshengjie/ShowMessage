package cn.xiong.showmessage.http;

/**
 * Created by Administrator on 2017/7/18.
 */

public interface HttpCallBack {

    void onSuccess(String json,NetThead thread);

    void onFail(String message,NetThead thread);
}
