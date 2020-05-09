package com.choice.wxpayface.interfaces;

import com.choice.wxpayface.beans.WxResponse;

import java.util.Map;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;

/**
 * TODO 微信相关后台接口
 */
public interface WxApi {
    //get请求,获取调用凭证(get_wxpayface_authinfo)
    @POST("face/get_wxpayface_authinfo")
    Call<WxResponse> getWxpayfaceAuthinfo(@Body RequestBody requestBody);

    //获取用户实名信息
    @GET("v3/facemch/users/{face_sid}")
    Call<ResponseBody> getUserInfo(@Path("face_sid") String faceSid, @QueryMap Map<String, String> map);

//    *
//     * 刷脸支付后台接口
//     * @param requestBody
//     * @return

    @POST("pay/facepay")
    Call<ResponseBody> doFacePay(@Body RequestBody requestBody);

}
