package cn.xiong.showmessage.http;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/18.
 */

public class HttpGet {

    private static final OkHttpClient client = new OkHttpClient();
    public void getData(String url,final HttpCallBack callBack){

        final Request request = new Request.Builder()
                .get()
                .tag(this)
                .url(url)
                .build();

        new Thread(){
            @Override
            public void run() {
                Response response = null;

                Call call = null;

                try {
                    call = client.newCall(request);
                    response = call.execute();
                    if (response.isSuccessful()) {
                        callBack.onSuccess(response.body().string());
                    }else {
                        String message = "错误："+response;
                        callBack.onFail(message);
                    }
                }catch (IOException e){
                    callBack.onFail("connect reset");
                }finally {
                    if(call != null){
                        call.cancel();
                    }
                    if(response != null){
                        response.close();
                    }
                }
            }
        }.start();
    }
}
