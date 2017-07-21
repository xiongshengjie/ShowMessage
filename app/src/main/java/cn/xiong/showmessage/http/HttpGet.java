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

    public static final OkHttpClient client = new OkHttpClient();

    public static void getData(String url, final HttpCallBack callBack) {

        final Request request = new Request.Builder()
                .get()
                .url(url)
                .build();


        NetThead thread = new NetThead(request, callBack);
        thread.start();

    }
}
