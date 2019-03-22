package com.wzz.http.proxyhttp;

import java.util.Map;

/**
 * Created by Administrator on 2019/3/6.
 */

public interface IHttp {
    void get(String url, ICallBack callBack);
    void post(String url, Map<String, String> map, ICallBack callBack);
}
