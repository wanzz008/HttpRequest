package com.wzz.http.okhttp2;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;

import java.io.IOException;


public interface BaseCallback2<M> {

    void onBeforeRequest(Request request);


    void onFailure(Request request, Exception e) ;

    /**
     *请求成功时调用此方法
     * @param response
     */
    void onResponse(Response response);

    /**
     *
     * 状态码大于200，小于300 时调用此方法
     * @param response
     * @param t
     * @throws IOException
     */
    void onSuccess(Response response,M t) ;

    /**
     * 状态码400，404，403，500等时调用此方法
     * @param response
     * @param code
     * @param e
     */
    void onError(Response response, int code,Exception e) ;

}