package com.choice.wxpayface.managers;

import com.alibaba.fastjson.JSON;
import com.choice.wxpayface.interceptors.TokenInterceptor;
import com.choice.wxpayface.interfaces.ApiService;
import com.choice.wxpayface.utils.HttpUrlUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class RetrofitManager<T extends Class,K> {
    public K getRequest(T clazz,String url){
        Retrofit retrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(url) // 设置网络请求baseUrl
                .addConverterFactory(GsonConverterFactory.create()) //设置使用SimpleXml解析
                .build();

        // 步骤5:创建网络请求接口的实例
        K request =  (K)retrofit.create(clazz);
        return request;
    }

    private static OkHttpClient getHttpClient() {
        //创建client用于添加header
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new TokenInterceptor());
        OkHttpClient client = clientBuilder.build();
        return client;
    }

    private RetrofitManager(){}

    private static class Holder{
        private static RetrofitManager instance = new RetrofitManager();
    }

    public static RetrofitManager getInstance(){
        return Holder.instance;
    }
}
