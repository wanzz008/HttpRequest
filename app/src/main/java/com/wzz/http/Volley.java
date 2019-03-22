package com.wzz.http;

import com.wzz.http.http.HttpListener;
import com.wzz.http.http.HttpService;
import com.wzz.http.http.HttpTask;
import com.wzz.http.http.IDataListener;
import com.wzz.http.http.IHttpService;
import com.wzz.http.http.ThreadPoolManager;

public class Volley  {


    public static<M,T> void request( Class<M> clazz , String url , T info , IDataListener<M> dataListener ){

        IHttpService service = new HttpService();
        HttpListener httpListener = new HttpListener( clazz , dataListener  );

        HttpTask<T> task = new HttpTask(service , httpListener , url , info );

        ThreadPoolManager.getManager().execute( task );
    }
}
