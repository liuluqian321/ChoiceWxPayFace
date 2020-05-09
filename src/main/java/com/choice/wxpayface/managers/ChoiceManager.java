package com.choice.wxpayface.managers;

import android.app.Activity;
import android.content.Context;

import com.alibaba.fastjson.JSONObject;
import com.choice.wxpayface.beans.ChoiceResponse;
import com.choice.wxpayface.beans.InitBean;
import com.choice.wxpayface.interfaces.ApiService;
import com.choice.wxpayface.interfaces.ChoiceWxInterface;
import com.choice.wxpayface.interfaces.FaceInfoTask;
import com.choice.wxpayface.interfaces.InitApi;
import com.choice.wxpayface.interfaces.InitDeviceCallBack;
import com.choice.wxpayface.utils.AesEncryptUtils;
import com.choice.wxpayface.utils.DeviceUtil;
import com.choice.wxpayface.utils.HttpUrlUtil;
import com.choice.wxpayface.utils.StorageInfoUtil;
import com.choice.wxpayface.utils.StringUtil;
import com.choice.wxpayface.utils.WXPayUtil;
import com.tencent.wxpayface.WxPayFace;

import java.util.ArrayList;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 *
 */
public class ChoiceManager implements ChoiceWxInterface {

    private ChoiceManager(){}
    @Override
    public void initDevice(InitDeviceCallBack initDeviceCallBack) {
        RetrofitManager<Class<InitApi>,InitApi> manager = RetrofitManager.getInstance();
        InitApi request = manager.getRequest(InitApi.class, HttpUrlUtil.VERSION_URL);
//构造请求对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("equipmentCode", DeviceUtil.getSN());
        jsonObject.put("versionNumber", "1");
        //aes加密
        String encryptStr = AesEncryptUtils.encrypt(jsonObject.toJSONString(),AesEncryptUtils.KEY);
        RequestBody signBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), encryptStr);
        Call<ChoiceResponse<String>> call = request.initDevice(signBody);
        call.enqueue(new Callback<ChoiceResponse<String>>() {
            @Override
            public void onResponse(Call<ChoiceResponse<String>> call, Response<ChoiceResponse<String>> response) {
                ChoiceResponse<String> choiceResponse = response.body();
                boolean success = choiceResponse.isSuccess();
                String message = choiceResponse.getMessage();
                if(!success){
                    initDeviceCallBack.onFailure(message);
                }
                String encrypt = choiceResponse.getData();
                String decrypt = AesEncryptUtils.decrypt(encrypt,AesEncryptUtils.KEY);
                JSONObject resultObject = JSONObject.parseObject(decrypt);
                String apiUrl = resultObject.getString("apiUrl");
                if(StringUtil.isEmpty(apiUrl)){
                    initDeviceCallBack.onFailure("apiUrl未配置，请联系健康云管理员配置设备");
                    return;
                }
                HttpUrlUtil.API_URL = apiUrl + "/api/face/";
                getInitWxPayInfo(initDeviceCallBack);
            }

            @Override
            public void onFailure(Call<ChoiceResponse<String>> call, Throwable t) {
                initDeviceCallBack.onFailure(t.getMessage());
            }
        });
    }

    @Override
    public void getUserInfoByWxPayFace(Activity activity, FaceInfoTask faceInfoTask) {
        WxPayManager.initPayFace(activity,faceInfoTask);
    }

    @Override
    public void releaseWxPayFace(Context context) {
        WxPayFace.getInstance().releaseWxpayface(context);
    }

    /**
     * 初始化获取微信appid，商户号等信息
     * @param initDeviceCallBack
     */
    private void getInitWxPayInfo(InitDeviceCallBack initDeviceCallBack){
        RetrofitManager<Class<ApiService>,ApiService> manager = RetrofitManager.getInstance();
        ApiService request = manager.getRequest(ApiService.class, HttpUrlUtil.API_URL);
        //构造请求对象
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("equipmentCode", DeviceUtil.getSN());
        //aes加密
        String encryptStr = AesEncryptUtils.encrypt(jsonObject.toJSONString(),AesEncryptUtils.KEY);
        RequestBody signBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), encryptStr);
        Call<ChoiceResponse<String>> call = request.init(signBody);
        call.enqueue(new Callback<ChoiceResponse<String>>() {
            @Override
            public void onResponse(Call<ChoiceResponse<String>> call, Response<ChoiceResponse<String>> response) {
                ChoiceResponse<String> choiceResponse = response.body();
                boolean success = choiceResponse.isSuccess();
                String message = choiceResponse.getMessage();
                if(!success){
                    initDeviceCallBack.onFailure(message);
                }
                String encrypt = choiceResponse.getData();
                String decrypt = AesEncryptUtils.decrypt(encrypt,AesEncryptUtils.KEY);
                InitBean beans =JSONObject.parseObject(decrypt.trim(), InitBean.class);
                StorageInfoUtil.equipmentName = beans.getEquipmentName();
                StorageInfoUtil.hospitalName = beans.getHospitalName();
                StorageInfoUtil.hospitalCode = beans.getHospitalCode();
                StorageInfoUtil.equipmentCode = beans.getEquipmentCode();
                StorageInfoUtil.token = beans.getToken();
                List<InitBean.ParameterBean> parameterBeans = new ArrayList<>();
                parameterBeans.addAll(beans.getParameter());
                if (null != parameterBeans) {
                    for (int i = 0; i < parameterBeans.size(); i++) {
                        String name = parameterBeans.get(i).getParameCode();
                        switch (name) {
                            case "wechatMerchantName":
                                if(StringUtil.isEmpty(parameterBeans.get(i).getFirstValue())){
                                    initDeviceCallBack.onFailure("参数【wechatMerchantName】未配置");
                                }else{
                                    WxPayManager.STORE_NAME = parameterBeans.get(i).getFirstValue();
                                }
                                break;
                            case "wechatPayAppid":
                                if(StringUtil.isEmpty(parameterBeans.get(i).getFirstValue())){
                                    initDeviceCallBack.onFailure("参数【wechatPayAppid】未配置");
                                }else{
                                    WXPayUtil.APPID = parameterBeans.get(i).getFirstValue();
                                }
                                break;
                            case "wechatMerchantCode":
                                if(StringUtil.isEmpty(parameterBeans.get(i).getFirstValue())){
                                    initDeviceCallBack.onFailure("参数【wechatMerchantCode】未配置");
                                }else{
                                    WXPayUtil.MCHID = parameterBeans.get(i).getFirstValue();
                                }
                                break;
                            case "wechatApiSignKey":
                                if(StringUtil.isEmpty(parameterBeans.get(i).getFirstValue())){
                                    initDeviceCallBack.onFailure("参数【wechatApiSignKey】未配置");
                                }else{
                                    WXPayUtil.PRIVATE_KEY = parameterBeans.get(i).getFirstValue();
                                }
                                break;
                            case "wechatMerchantId":
                                if(StringUtil.isEmpty(parameterBeans.get(i).getFirstValue())){
                                    initDeviceCallBack.onFailure("参数【wechatMerchantId】未配置");
                                }else{
                                    WxPayManager.STORE_ID = parameterBeans.get(i).getFirstValue();
                                }
                                break;
                        }
                    }
                    initDeviceCallBack.onSuccess();
                }else{
                    initDeviceCallBack.onFailure("机构参数列表为空");
                }
            }

            @Override
            public void onFailure(Call<ChoiceResponse<String>> call, Throwable t) {
                initDeviceCallBack.onFailure(t.getMessage());
            }
        });
    }

    private static class Holder{
        private static ChoiceManager instance = new ChoiceManager();
    }

    public static ChoiceManager getInstance(){
        return Holder.instance;
    }
}
