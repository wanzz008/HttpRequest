package com.wzz.http.http;


import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class HttpService implements IHttpService{

    private String url ;
    private byte[] requestData ;
    private IHttpListener mListener;

    @Override
    public void setUrl(String url) {
        this.url = url ;
    }

    @Override
    public void setParams(byte[] params) {
        this.requestData = params ;
    }


    @Override
    public void setHttpCallBack(IHttpListener listener) {
        this.mListener = listener ;
    }

    @Override
    public void execute() {
        httpPost();
    }

    HttpURLConnection urlConnection = null;
    private void httpPost() {
        URL url = null;
        try {
            url = new URL( getUtf8Url( this.url ) ); // 先把url转换为utf-8的url，防止含有中文字符
            urlConnection = (HttpURLConnection) url.openConnection();//打开http连接
            urlConnection.setConnectTimeout(6000);//连接的超时时间
            urlConnection.setUseCaches(false);//不使用缓存
            urlConnection.setInstanceFollowRedirects(true);//是成员函数，仅作用于当前函数,设置这个连接是否可以被重定向
            urlConnection.setReadTimeout(3000);//响应的超时时间
            urlConnection.setDoInput(true);//设置这个连接是否可以写入数据
            urlConnection.setDoOutput(true);//设置这个连接是否可以输出数据
            urlConnection.setRequestMethod("POST");//设置请求的方式
            urlConnection.setRequestProperty("Content-Type", "application/json;charset=UTF-8");//设置消息的类型
            urlConnection.connect();// 连接，从上述至此的配置必须要在connect之前完成，实际上它只是建立了一个与服务器的TCP连接
            //-------------使用字节流发送数据--------------
            OutputStream out = urlConnection.getOutputStream();
            BufferedOutputStream bos = new BufferedOutputStream(out);//缓冲字节流包装字节流  // 或 DataOutputStream out = new DataOutputStream(urlConnection.getOutputStream());  out.writeBytes();

            if (requestData != null) {
                bos.write( requestData ); //post的参数 xx=xx&yy=yy
            }

            //把这个字节数组的数据写入缓冲区中
            bos.flush();//刷新缓冲区，发送数据
            out.close();
            bos.close();
            //------------字符流写入数据------------
            if(urlConnection.getResponseCode() == HttpURLConnection.HTTP_OK) {//得到服务端的返回码是否连接成功
                InputStream in = urlConnection.getInputStream();
                mListener.onSuccess(in);
            }else {
                mListener.onFailure("responseCode:" + urlConnection.getResponseCode() );
            }

        } catch (Exception e) {
            e.printStackTrace();
            mListener.onFailure("请求失败:" + e.getMessage() );
        }finally{
            urlConnection.disconnect();//使用完关闭TCP连接，释放资源
        }
    }

    private static String getUtf8Url(String url) {
        char[] chars = url.toCharArray();
        StringBuilder utf8Url = new StringBuilder();
        final int charCount = chars.length;
        for (int i = 0; i < charCount; i++) {
            byte[] bytes = ("" + chars[i]).getBytes();
            if (bytes.length == 1) {
                utf8Url.append(chars[i]);
            }else{
                try {
                    utf8Url.append(URLEncoder.encode(String.valueOf(chars[i]), "UTF-8"));
                } catch (UnsupportedEncodingException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        }
        return utf8Url.toString();
    }

}
