package com.choice.wxpayface.interfaces;

import com.choice.wxpayface.beans.ChoiceResponse;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface InitApi {
    /**
     * 初始化设备信息，获取API_URL
     * @param body
     * @return
     */
    @POST("equipment/version")
    @Headers({"appId:choice_face_app"})
    Call<ChoiceResponse<String>> initDevice(@Body RequestBody body);
}
