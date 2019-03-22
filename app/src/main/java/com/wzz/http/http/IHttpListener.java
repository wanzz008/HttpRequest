package com.wzz.http.http;

import java.io.InputStream;

public interface IHttpListener {
    void onSuccess(InputStream inputStream);
    void onFailure( String msg );
}
