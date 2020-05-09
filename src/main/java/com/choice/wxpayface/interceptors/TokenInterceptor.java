package com.choice.wxpayface.interceptors;


import com.choice.wxpayface.managers.WxPayManager;
import com.choice.wxpayface.utils.HttpUrlUtil;
import com.choice.wxpayface.utils.StorageInfoUtil;
import com.choice.wxpayface.utils.StringUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class TokenInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request()
                .newBuilder()
                .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                .addHeader("Accept-Encoding", "gzip, deflate")
                .addHeader("Connection", "keep-alive")
                .addHeader("Accept", "*/*")
                .addHeader("Cookie", "add cookies here")
                .addHeader("token", StorageInfoUtil.token)
                .addHeader("equipmentCode",StorageInfoUtil.equipmentCode)
                .build();
        Response response = chain.proceed(request);
        int httpCode = response.code();
        if(httpCode == HttpUrlUtil.HTTP_CODE_TOKEN_INVALID){
            //同步获取token
            String token = WxPayManager.getToken();
            if(StringUtil.isNotEmpty(token)){
                StorageInfoUtil.token = token;
                request = chain.request()
                        .newBuilder()
                        .addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
                        .addHeader("Accept-Encoding", "gzip, deflate")
                        .addHeader("Connection", "keep-alive")
                        .addHeader("Accept", "*/*")
                        .addHeader("Cookie", "add cookies here")
                        .addHeader("token", StorageInfoUtil.token)
                        .addHeader("equipmentCode",StorageInfoUtil.equipmentCode)
                        .build();
                return chain.proceed(request);
            }
        }
        return response;
    }
}
