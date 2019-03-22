package com.wzz.http.http;

import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.UnsupportedEncodingException;

public class HttpTask<T> implements Runnable{

    private IHttpService  mIHttpService ;
    private IHttpListener mIHttpListener ;

    public HttpTask( IHttpService service , IHttpListener listener , String url , T requestInfo ) {

        this.mIHttpService = service ;
        this.mIHttpListener = listener ;

        mIHttpService.setUrl( url );
        mIHttpService.setHttpCallBack( mIHttpListener );

        if ( requestInfo != null ){
            // 把请求参数转换为json格式
            String requestContent = JSON.toJSONString(requestInfo);
            Log.d("requestInfo------" , requestContent );
            try {
                mIHttpService.setParams( requestContent.getBytes("UTF-8") );
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void run() {
        mIHttpService.execute();
    }
}
