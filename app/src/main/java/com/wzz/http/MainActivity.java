package com.wzz.http;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.wzz.http.bean.CityData;
import com.wzz.http.http.IDataListener;
import com.wzz.http.okhttp1.OkHttpHelper;
import com.wzz.http.okhttp1.SpotsCallBack;
import com.wzz.http.okhttp2.BaseCallback2_Iml;
import com.wzz.http.okhttp2.OkHttpHelper2;

public class MainActivity extends AppCompatActivity {

    String url="http://www.mxnzp.com/api/lottery/ssq/aim_lottery?expect=2018135"; // 访问正常

    String url1="http://www.mxnzp.com/api/address/search?type=1&value=深圳"; // 访问正常，需要把含有中文的url转换为utf-8的url

    String url2="http://www.mxnzp.com/api/weather/current/深圳市"; // 查天气

    // post请求
    String url3="http://www.mxnzp.com/api/qrcode/create/logo?"; // 生成图片二维码  // content=你好&size=600&logo_size=500&type=0&logo_img=logo


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void click(View view) {

//        for ( int i = 0 ; i< 1000 ; i++){
//            Volley.request( DataResponce.class , url,  null , new IDataListener<DataResponce>() {
//
//                @Override
//                public void onSuccess(DataResponce responce) {
//                    Log.d("----" , responce.getData().getName() );
//                    Toast.makeText(MainActivity.this, responce.getData().getName() ,Toast.LENGTH_SHORT).show();
//                }
//
//                @Override
//                public void onFailure(String msg) {
//                    Log.d("----" , msg );
//                    Toast.makeText(MainActivity.this, msg,Toast.LENGTH_SHORT).show();
//                }
//            });
//        }


        // {"msg":"type和value都是必传项","code":0}

        /** 城市区域 */

        /** 用手写的Volley进行请求 */
        Volley.request(CityData.class, url1, null, new IDataListener<CityData>() {
            @Override
            public void onSuccess(CityData data) {
                Log.d("----" , data.data.get(0).pchilds.get(0).name );
            }

            @Override
            public void onFailure(String msg) {
                Log.d("----" , msg );
            }
        });

        /** 用封装的okhttp进行请求 */
        OkHttpHelper2.getInstance().get(url1, CityData.class, new BaseCallback2_Iml<CityData>(this ) {

            @Override
            public void onFailure(Request request, Exception e) {
                super.onFailure(request, e);

                Log.d("OkHttpHelper2----" , e.getMessage() );

            }

            @Override
            public void onSuccess(Response response, CityData msg) {
                Log.d("OkHttpHelper2----" , msg.msg );
                Toast.makeText(MainActivity.this,msg.data.get(0).name,Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Response response, int code, Exception e) {
                Log.d("OkHttpHelper2----" , e.getMessage() );
            }
        });

        OkHttpHelper.getInstance().get(url1, new SpotsCallBack<String>(this) {

            @Override
            public void onSuccess(Response response, String s) {

            }

            @Override
            public void onError(Response response, int code, Exception e) {

            }
        });

        /** 城市天气 */
//        Volley.request(WeatherData.class, url2, null, new IDataListener<WeatherData>() {
//            @Override
//            public void onSuccess(WeatherData weatherData) {
//                Log.d("----" , weatherData.getData().getAddress() );
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                Log.d("----" , msg );
//            }
//        });

        /** 生成二维码  post请求暂时有问题 */
//        HashMap<String,String> map = new HashMap<>();
//        map.put("content" , "aaa") ;
//        map.put("size" , "600") ;
//        map.put("logo_size" , "500") ;
//        map.put("type" , "0") ;
//        map.put("logo_img" , "logo") ;
//
//        Volley.request(PhotoData.class, url3, map, new IDataListener<PhotoData>() {
//            @Override
//            public void onSuccess(PhotoData photoData) {
//                if ( photoData != null ){
////                    Log.d("----" , photoData.getData().getContent()  );
//                }
//            }
//
//            @Override
//            public void onFailure(String msg) {
//                Log.d("----" , msg  );
//            }
//        });
    }
}
