package com.choice.wxpayface.interfaces;

import com.alibaba.fastjson.JSONObject;
import com.choice.wxpayface.beans.ChoiceResponse;
import io.reactivex.Single;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    /**
     * 初始化接口
     */
    @POST("equipmentinit")
    @Headers({"appId:choice_face_app"})
//    Call<MyInitBeans> init(@Body RequestBody body);
    Call<ChoiceResponse<String>> init(@Body RequestBody body);


    /**
     * 根据face_sid获取用户的姓名身份证信息
     * @param requestBody
     * @return
     */
    @POST("equipment/querywechatuser")
    @Headers({"appId:choice_face_app"})
    Call<ChoiceResponse<JSONObject>> getUserInfoByFaseSid(@Body RequestBody requestBody);

    /**
     * 创建实名认证二维码
     * @param requestBody
     * @return
     */
    @POST("equipment/wechatauth/createauthqrcode")
    @Headers({"appId:choice_face_app"})
    Single<String> createAuthQrcode(@Body RequestBody requestBody);

    /**
     * 查询实名认证结果
     * @param requestBody
     * @return
     */
    @POST("equipment/wechatauth/queryauthstate")
    @Headers({"appId:choice_face_app"})
    Single<String> queryAuthResult(@Body RequestBody requestBody);
}
