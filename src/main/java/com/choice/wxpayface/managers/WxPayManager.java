package com.choice.wxpayface.managers;

import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.os.RemoteException;
import android.os.SystemClock;

import androidx.annotation.RequiresApi;

import com.alibaba.fastjson.JSONObject;
import com.choice.wxpayface.beans.ChoiceResponse;
import com.choice.wxpayface.beans.UserInfo;
import com.choice.wxpayface.beans.WxResponse;
import com.choice.wxpayface.interceptors.TokenInterceptor;
import com.choice.wxpayface.interfaces.ApiService;
import com.choice.wxpayface.interfaces.FaceInfoTask;
import com.choice.wxpayface.interfaces.WxApi;
import com.choice.wxpayface.utils.AesEncryptUtils;
import com.choice.wxpayface.utils.DeviceUtil;
import com.choice.wxpayface.utils.HttpUrlUtil;
import com.choice.wxpayface.utils.StorageInfoUtil;
import com.choice.wxpayface.utils.StringUtil;
import com.choice.wxpayface.utils.UuIdUtil;
import com.choice.wxpayface.utils.WXPayUtil;
import com.tencent.wxpayface.IWxPayfaceCallback;
import com.tencent.wxpayface.WxPayFace;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.simplexml.SimpleXmlConverterFactory;

public class WxPayManager {
    private static Context context = null;
    //    public static volatile String rawData = null;
    public volatile static String authInfo = null;
    public static String OPEN_ID = "";
    public static String FACE_CODE = "";
    private static String FACE_SID = "";
    public static String IP = "";
    //    public static final String DEVICE_ID = "DEV001";
    public static final String DEVICE_ID = Build.SERIAL;
    public static String STORE_ID = "";
    public static String STORE_NAME = "";  //商户名称
    public static final String SIGN_TYPE = "MD5";
    public static final String VERSION = "1";

    /**
     * 1. 程序启动时初始化 initWxpayface
     */
    public static void initPayFace(Activity activity,FaceInfoTask faceInfoTask) {
        Map<String, String> m1 = new HashMap<>();
        WxPayFace.getInstance().initWxpayface(activity, m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("调用initWxpayface方法返回为空");
                    });
                    return;
                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                if (code == null || !code.equals("SUCCESS")) {
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("调用initWxpayface方法返回失败，原因【"+msg+"】");
                    });
                    return;
                }
                //延迟一秒等待wxapp service连接
                SystemClock.sleep(1000);
                getWxpayfaceRawdata(activity,faceInfoTask);
            }
        });
    }

    /**
     * 2. 获取数据 getWxpayfaceRawdata
     */
    public static void getWxpayfaceRawdata(Activity activity,FaceInfoTask faceInfoTask) {
        WxPayFace.getInstance().getWxpayfaceRawdata(new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("调用getWxpayfaceRawdata方法返回为空");
                    });
                    return;
                }
                String code = (String) info.get("return_code");
                String msg = (String) info.get("return_msg");
                if (!"SUCCESS".equals(code)) {
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("调用getWxpayfaceRawdata方法返回失败，原因【"+msg+"】");
                    });
                    return;
                }
                String rawData = info.get("rawdata").toString();
                getWxpaySign(activity, rawData,faceInfoTask);
            }
        });
    }

    public static void getWxpaySign(Activity activity, String rawData,FaceInfoTask faceInfoTask) {
        String nonceStr = WXPayUtil.genNonceStr();
        String now = String.valueOf(WXPayUtil.genTimeStamp());
        Map<String, String> map = new HashMap<>();
        map.put("appid", WXPayUtil.APPID);
        map.put("device_id", DEVICE_ID);
        map.put("mch_id", WXPayUtil.MCHID);
        map.put("nonce_str", nonceStr);
        map.put("now", now);
        map.put("rawdata", rawData);
        map.put("sign_type", SIGN_TYPE);
        map.put("store_id", STORE_ID);
        map.put("store_name", STORE_NAME);
        map.put("version", VERSION);
        //本地签名
        String sign = WXPayUtil.genPackageSign(map,WXPayUtil.PRIVATE_KEY);
        getWxPayfaceAuthinfo(activity,rawData,nonceStr,now,sign,faceInfoTask);
    }

    public static void getWxPayfaceAuthinfo(Activity activity, String rawData,String nonceStr,String now,String sign,FaceInfoTask faceInfoTask) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(HttpUrlUtil.WX_BASE_URL) // 设置网络请求baseUrl
                .addConverterFactory(SimpleXmlConverterFactory.create()) //设置使用SimpleXml解析
                .build();

        // 步骤5:创建网络请求接口的实例
        WxApi request = retrofit.create(WxApi.class);
        String xml = "<xml>" +
                "<appid>" + WXPayUtil.APPID + "</appid>" +
                "<device_id>" + DEVICE_ID + "</device_id>" +
                "<mch_id>" + WXPayUtil.MCHID + "</mch_id>" +
                "<nonce_str>" + nonceStr + "</nonce_str>" +
                "<now>" + now + "</now>" +
                "<rawdata>" + rawData + "</rawdata>" +
                "<sign>" + sign + "</sign>" +
                "<sign_type>" + SIGN_TYPE + "</sign_type>" +
                "<store_id>" + STORE_ID + "</store_id>" +
                "<store_name>" + STORE_NAME + "</store_name>" +
                "<version>" + VERSION + "</version>" +
                "</xml>";
        //步骤6：对送请求进行封装:
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/xml;charset=UTF-8"), xml);
        Call<WxResponse> authInfoCall = request.getWxpayfaceAuthinfo(requestBody);
        //步骤7:发送网络请求(异步)
        authInfoCall.enqueue(new Callback<WxResponse>() {
            //请求成功时回调
            @Override
            public void onResponse(Call<WxResponse> call, Response<WxResponse> response) {
                WxResponse data = response.body();
                String returnCode = data.getReturnCode();
                String returnMessage = data.getReturnMsg();
                if(!"SUCCESS".equals(returnCode)){
                    faceInfoTask.fail(returnMessage);
                }
                authInfo = data.getAuthinfo();
                getWxpayFaceInfo(activity,faceInfoTask);
            }

            //请求失败时回调
            @Override
            public void onFailure(Call<WxResponse> call, Throwable throwable) {
                faceInfoTask.fail(throwable.getMessage());
            }
        });
    }

    /**
     * 刷脸获取用户实名信息
     *
     * @param faceInfoTask
     */
    public static void getWxpayFaceInfo(Activity activity, FaceInfoTask faceInfoTask) {
        //获取付款金额
        Map<String, String> m1 = new HashMap<String, String>();
        final String outTradeNo = UuIdUtil.getUuid();//商户自定义订单号
        m1.put("appid", WXPayUtil.APPID); // 公众号，必填
        m1.put("mch_id", WXPayUtil.MCHID); // 商户号，必填
//        m1.put("sub_appid", "xxxxxxxxxxx"); // 子商户公众账号ID(非服务商模式不填)
//        m1.put("sub_mch_id", "xxxxxxxxxxx"); // 子商户号(非服务商模式不填)
        m1.put("store_id", STORE_ID); // 门店编号，必填
//        m1.put("telephone", "用户手机号"); // 用户手机号，用于传递会员手机号到界面输入栏，非必填
        m1.put("out_trade_no", outTradeNo); // 商户订单号， 必填
        m1.put("total_fee", "1"); // 订单金额（数字），单位：分，必填
        m1.put("face_authtype", "FACE_AUTH"); // FACEPAY：人脸凭证，常用于人脸支付    FACEPAY_DELAY：延迟支付   必填
        m1.put("ask_face_permit", "0"); // 展开人脸识别授权项，详情见上方接口参数，必填
//        m1.put("face_code_type","1");
        m1.put("authinfo", authInfo);
//        m1.put("ask_ret_page", "0"); // 是否展示微信支付成功页，可选值："0"，不展示；"1"，展示，非必填
        WxPayFace.getInstance().getWxpayfaceCode(m1, new IWxPayfaceCallback() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void response(final Map info) throws RemoteException {
                if (info == null) {
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("调用getWxpayfaceCode方法返回为空");
                    });
                    return;
                }
                String code = (String) info.get("return_code"); // 错误码
                String msg = (String) info.get("return_msg"); // 错误码描述
                JSONObject jsonObject = new JSONObject();
                if (!"SUCCESS".equals(code)) {//刷脸失败
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail(msg);
                    });
                    return;
                }
                String faceSid = info.get("face_sid").toString();
                String openid = info.get("openid").toString(); // openid
                OPEN_ID = openid;
                getUserAuth(activity,faceInfoTask,openid,faceSid);
            }

        });
    }


    /**
     * 获取用户授权
     * @param faceInfoTask
     * @param openId
     * @param faceSid
     */
    private static void getUserAuth(Activity activity,FaceInfoTask faceInfoTask,String openId,String faceSid){
        Map authMap = new HashMap();
        authMap.put("authinfo", WxPayManager.authInfo);
        authMap.put("face_sid", faceSid);
        WxPayFace.getInstance().getWxpayAuth(authMap, new IWxPayfaceCallback() {
            @Override
            public void response(Map map) throws RemoteException {
                String returnCode = (String) map.get("return_code");
                String returnMessage = (String) map.get("return_msg");
                if (!"SUCCESS".equals(returnCode)) {//授权失败
                    activity.runOnUiThread(()->{
                        faceInfoTask.fail("用户授权失败，原因【"+returnMessage+"】");
                    });
                    return;
                }
                //获取实名信息
                getRealNameInformation(faceInfoTask,openId,faceSid);
            }
        });
    }

    /**
     * 获取实名信息
     */
    private static void getRealNameInformation(FaceInfoTask faceInfoTask,String openId,String faceSid){
        //创建client用于添加header
        OkHttpClient client = getHttpClient();
        //请求获取用户身份信息
        Retrofit retrofit = new Retrofit.Builder()
                .client(client)
                .baseUrl(HttpUrlUtil.API_URL) // 设置网络请求baseUrl
                .addConverterFactory(GsonConverterFactory.create()) //设置使用Gson解析
                .build();
        ApiService request = retrofit.create(ApiService.class);
        JSONObject paramJson = new JSONObject();
        paramJson.put("openId",openId );
        paramJson.put("hospitalCode", StorageInfoUtil.hospitalCode);
        paramJson.put("faceToken", faceSid);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), paramJson.toJSONString());
        Call<ChoiceResponse<JSONObject>> call = request.getUserInfoByFaseSid(requestBody);
        call.enqueue(new Callback<ChoiceResponse<JSONObject>>() {
            @Override
            public void onResponse(Call<ChoiceResponse<JSONObject>> call, Response<ChoiceResponse<JSONObject>> response) {
                ChoiceResponse<JSONObject> choiceResponse = response.body();
                //获取返回结果中的sign
                //刷新token
                String refreshToken = choiceResponse.getRefreshToken();
                if (StringUtil.isNotEmpty(refreshToken)) {
                    StorageInfoUtil.token = refreshToken;
                }
                //获取返回状态
                boolean success = choiceResponse.isSuccess();
                String resultMessage = choiceResponse.getMessage();
                if (!success) {//请求失败
                    faceInfoTask.fail("获取用户实名信息失败，原因：【" + resultMessage + "】");
                    return;
                }
                JSONObject resultJsonObject = choiceResponse.getData();
                //获取姓名
                String userRealName = resultJsonObject.getString("userRealName");
                //获取身份证号
                String userIdCardNo = resultJsonObject.getString("userIdCardNo");
                //获取手机号
                String userMobile = resultJsonObject.getString("userMobile");
                UserInfo userInfo = new UserInfo(userRealName, userIdCardNo, userMobile, null, openId);
                faceInfoTask.execute(userInfo);
            }

            @Override
            public void onFailure(Call<ChoiceResponse<JSONObject>> call, Throwable t) {
                faceInfoTask.fail("获取用户信息后台接口调用失败，【errorMessage】=【" + t.getMessage() + "】");
            }
        });
    }

    /**
     * 获取用户openId等基本信息
     */
    public static void doFaceRecognize(FaceInfoTask faceInfoTask) {
        // 详细的参数配置表可见上方的“接口参数表”
        Map<String, String> m1 = new HashMap<String, String>();
        m1.put("appid", WXPayUtil.APPID); // 公众号，必填
        m1.put("mch_id", WXPayUtil.MCHID); // 商户号，必填
//        m1.put("sub_appid", "xxxxxxxxxxxxxx"); // 子商户公众账号ID(非服务商模式不填)
//        m1.put("sub_mch_id", "填您的子商户号"); // 子商户号(非服务商模式不填)
        m1.put("store_id", STORE_ID); // 门店编号，必填
        m1.put("face_authtype", "FACEID-LOOP"); // 人脸识别模式， FACEID-ONCE`: 人脸识别(单次模式) FACEID-LOOP`: 人脸识别(循环模式), 必填
        m1.put("authinfo", authInfo); // 调用凭证，详见上方的接口参数
//        m1.put("ask_unionid", "1"); // 是否获取union_id    0：获取    1：不获取
        WxPayFace.getInstance().getWxpayfaceUserInfo(m1, new IWxPayfaceCallback() {
            @Override
            public void response(Map info) throws RemoteException {
                if (info == null) {
                    faceInfoTask.fail("调用返回为空");
                    return;
                }
                String code = (String) info.get("return_code"); // 错误码
                String msg = (String) info.get("return_msg"); // 错误码描述
                String openid = info.get("openid").toString(); // openid
                String sub_openid = "";
                if (!"SUCCESS".equals(code)) {//刷脸失败
                    faceInfoTask.fail(msg);
                    return;
                }
                UserInfo info1 = new UserInfo();
                info1.setOpenId(openid);
                faceInfoTask.execute(info1);
            }
        });
    }

    public static String getToken() {
        Retrofit retrofit = new Retrofit.Builder()
                .client(getHttpClient())
                .baseUrl(HttpUrlUtil.API_URL) // 设置网络请求baseUrl
                .addConverterFactory(SimpleXmlConverterFactory.create()) //设置使用SimpleXml解析
                .build();

        // 步骤5:创建网络请求接口的实例
        ApiService request = retrofit.create(ApiService.class);
        //请求获取用户身份信息
        JSONObject paramJson = new JSONObject();
        paramJson.put("equipmentCode", DeviceUtil.getSN());
        String encrypt = AesEncryptUtils.encrypt(paramJson.toJSONString(), AesEncryptUtils.KEY);
        RequestBody requestBody = RequestBody.create(MediaType.parse("text/plain;charset=UTF-8"), encrypt);
        Call<ChoiceResponse<String>> resultString = request.init(requestBody);
        Response<ChoiceResponse<String>> result = null;
        try {
            result = resultString.execute();
        } catch (IOException e) {
            e.printStackTrace();
        }
        ChoiceResponse<String> choiceResponse = result.body();
        String dataStr = choiceResponse.getData();
        boolean success = choiceResponse.isSuccess();
        String resultMessage = choiceResponse.getMessage();
        if (!success) {
            return "";
        }
        JSONObject dataObject = JSONObject.parseObject(AesEncryptUtils.decrypt(dataStr, AesEncryptUtils.KEY), JSONObject.class);
        String token = dataObject.getString("token");
        return token;
    }

    public static OkHttpClient getHttpClient() {
        //创建client用于添加header
        OkHttpClient.Builder clientBuilder = new OkHttpClient.Builder();
        clientBuilder.addInterceptor(new TokenInterceptor());
        OkHttpClient client = clientBuilder.build();
        return client;
    }
}
