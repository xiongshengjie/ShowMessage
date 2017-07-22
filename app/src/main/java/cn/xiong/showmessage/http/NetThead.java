package cn.xiong.showmessage.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/20 0020.
 */

public class NetThead extends Thread {

    private Request request;
    private HttpCallBack callBack;

    public NetThead(Request request,HttpCallBack callBack){
        this.request = request;
        this.callBack = callBack;
    }

    @Override
    public void run() {
        Response response = null;

        Call call = null;
            try {
                call = HttpGet.client.newCall(request);
                response = call.execute();
                if (response.isSuccessful()) {
                    callBack.onSuccess(response.body().string(),this);
                } else {
                    String message = "错误：" + response;
                    callBack.onFail(message,this);
                }
            } catch (IOException e) {
                callBack.onFail("connect reset",this);
            } finally {
                if (call != null) {
                    call.cancel();
                }
                if (response != null) {
                    response.close();
                }
            }
    }
}
