package com.wzz.http.proxyhttp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.wzz.http.R;

/**
 * 使用代理模式，去实现网络请求
 */
public class MainActivity extends AppCompatActivity {

    public String url = "" ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /** 根据此处配置，选择用哪种网络请求 */
        ProxyHttp.getInstance().init( new VolleyUtil() );

    }

    public void onClick(View view ){

        ProxyHttp.getInstance().get( url, new ICallBack() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onFailed() {

            }
        });

    }
}
