package com.wzz.http.proxyhttp;

import java.util.Map;

/**
 * Created by Administrator on 2019/3/6.
 * 代理类
 */

public class ProxyHttp implements IHttp{

    private static ProxyHttp proxyHttp = new ProxyHttp();
    private ProxyHttp(){

    }
    public static ProxyHttp getInstance(){
        return proxyHttp ;
    }

    IHttp mIHttp ;
    public void init(IHttp http){
        mIHttp = http ;
    }


    @Override
    public void get(String url, ICallBack callBack) {
        mIHttp.get( url , callBack );
    }

    @Override
    public void post(String url, Map<String, String> map, ICallBack callBack) {
        mIHttp.post( url , map , callBack );
    }
}
