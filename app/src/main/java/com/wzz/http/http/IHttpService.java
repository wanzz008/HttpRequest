package com.wzz.http.http;

public interface IHttpService {

    void setUrl( String url );
    void setParams( byte[] params );
    void execute();//用来执行网络操作
    void setHttpCallBack(IHttpListener listener);

}
