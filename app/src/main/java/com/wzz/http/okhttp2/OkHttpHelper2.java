package com.wzz.http.okhttp2;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.google.gson.Gson;
import com.squareup.okhttp.Callback;
import com.squareup.okhttp.FormEncodingBuilder;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * 同OkHttpHelper 只是用了泛型
 * @param <M>
 */
public class OkHttpHelper2<M> {

    public static final String TAG = "OkHttpHelper....";

    private static OkHttpHelper2 mInstance;
    private OkHttpClient mHttpClient;
    private Gson mGson;

    private Handler mHandler;


    static {
        mInstance = new OkHttpHelper2();
    }

    public OkHttpHelper2() {

        mHttpClient = new OkHttpClient();
        mHttpClient.setConnectTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setReadTimeout(10, TimeUnit.SECONDS);
        mHttpClient.setWriteTimeout(30, TimeUnit.SECONDS);

        mGson = new Gson();

        mHandler = new Handler(Looper.getMainLooper());

    }

    public static OkHttpHelper2 getInstance() {
        return mInstance;
    }



    public void get(String url, Class<M> clazz, BaseCallback2<M> callback) {

        Request request = buildGetRequest(url);

        request(request, clazz, callback);

    }


    public void post(String url, Map<String, String> param, Class<M> clazz, BaseCallback2<M> callback) {

        Request request = buildPostRequest(url, param);
        request(request, clazz, callback);
    }


    private void request(final Request request, final Class<M> clazz, final BaseCallback2<M> callback) {

        callback.onBeforeRequest(request);

        mHttpClient.newCall(request).enqueue( new Callback() {

            @Override
            public void onFailure(Request request, IOException e) {
                callback.onFailure(request, e);

            }

            @Override
            public void onResponse(Response response) throws IOException {

                callback.onResponse(response); // 这里会取消对话框

                if (response.isSuccessful()) {

                    String resultStr = response.body().string();

                    Log.d(TAG, "result=" + resultStr);

                    try {
//
                        M obj = mGson.fromJson(resultStr, clazz);
                        callbackSuccess(callback, response, obj);

                    } catch (com.google.gson.JsonParseException e) { // Json解析的错误
                        callback.onError(response, response.code(), e);
                    }


                } else {
                    callbackError(callback, response, null);
                }

            }
        });


    }


    private void callbackSuccess(final BaseCallback2 callback, final Response response, final Object obj) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onSuccess(response, obj);
            }
        });
    }


    private void callbackError(final BaseCallback2 callback, final Response response, final Exception e) {

        mHandler.post(new Runnable() {
            @Override
            public void run() {
                callback.onError(response, response.code(), e);
            }
        });
    }


    private Request buildPostRequest(String url, Map<String, String> params) {

        return buildRequest(url, HttpMethodType.POST, params);
    }

    private Request buildGetRequest(String url) {

        return buildRequest(url, HttpMethodType.GET, null);
    }

    private Request buildRequest(String url, HttpMethodType methodType, Map<String, String> params) {


        Request.Builder builder = new Request.Builder()
                .url(url);

        if (methodType == HttpMethodType.POST) {
            RequestBody body = builderFormData(params);
            builder.post(body);
        } else if (methodType == HttpMethodType.GET) {
            builder.get();
        }


        return builder.build();
    }


    private RequestBody builderFormData(Map<String, String> params) {


        FormEncodingBuilder builder = new FormEncodingBuilder();

        if (params != null) {

            for (Map.Entry<String, String> entry : params.entrySet()) {

                builder.add(entry.getKey(), entry.getValue());
            }
        }

        return builder.build();

    }


    enum HttpMethodType {

        GET,
        POST,

    }


}
