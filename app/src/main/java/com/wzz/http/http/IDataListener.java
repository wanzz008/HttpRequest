package com.wzz.http.http;

public interface IDataListener<M> {

    void onSuccess(M m);
    void onFailure(String msg );
}
