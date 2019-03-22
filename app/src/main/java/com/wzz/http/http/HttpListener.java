package com.wzz.http.http;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.alibaba.fastjson.JSON;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HttpListener<M> implements IHttpListener{

    private IDataListener<M> dataListener;
    private Class<M> mClass ;

    public HttpListener( Class<M> clazz ,IDataListener<M> dataListener ) {
        this.mClass = clazz ;
        this.dataListener = dataListener ;
    }

    //用于线程的切换
    Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void onSuccess(InputStream inputStream) {

        //把获取到的byte数据转换成String
        String content = getContent(inputStream);

        // 把string数据转换成bean对象

        Log.d("HttpListener onSuccess:" , content );

        if ( content != null ){

            final M m = JSON.parseObject(content, mClass );

            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onSuccess( m );
                }
            });
        }else {
            handler.post(new Runnable() {
                @Override
                public void run() {
                    dataListener.onFailure( "获取数据为空" );
                }
            });

        }

    }

    @Override
    public void onFailure(final String msg) {

        Log.d("HttpListener onFailure:" , msg );

        handler.post(new Runnable() {
            @Override
            public void run() {
                dataListener.onFailure( msg );
            }
        });
    }


    private String getContent(InputStream inputStream) {
        String content = null;
        try {
            BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuilder sb = new StringBuilder();
            String line = null;
            try {
                while ((line = reader.readLine()) != null) {
                    sb.append(line + "\n");
                }
            } catch (IOException e) {
                System.out.println("Error=" + e.toString());
            } finally {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    System.out.println("Error=" + e.toString());
                }
            }
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content;
    }

}
