package com.choice.wxpayface.utils;

import android.content.Context;
import android.util.Base64;

import org.json.JSONObject;

import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.Signature;
import java.util.Comparator;
import java.util.Map;
import java.util.Random;
import java.util.TreeMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class WXPayUtil {
//    private IWXAPI iwxapi; //微信支付api
    //appid
    public static final String APPID_JKY = "wx804cb1c756ed56a6";//健康云
//    public static final String APPID = "wxd7623d14c8c88a45";//湘雅医院
    public static String APPID = "";    //APPID
    //商户号
    public static final String MCHID_JKY = "1510622081";//健康云
//    public static final String MCHID = "1484088332";//湘雅医院
    public static String MCHID = "";    //商户号
//    public static String SUBMCHID = "1515309101";    //子商户号
    //私钥
//    public static final String PRIVATE_KEY = "choicehcchoicehcchoicehcchoicehc";//获取调用凭证(get_wxpayface_authinfo)健康云
    public static String PRIVATE_KEY = "";   //秘钥
    //微信后台v3接口私钥
    private static String PRIVATE_KEY_V3 = "";
    //商户API证书序列号
    private static final String CERTIFICATE_SERIAL_NO = "76CCCADA805B2BB93B27C8F111DC72FC89EFBF2A";//健康云
    //认证类型
    public static final String SCHEMA = "WECHATPAY2-SHA256-RSA2048";


    private WXPayBuilder builder;

    private WXPayUtil(WXPayBuilder builder) {
        this.builder = builder;
    }

    /**
     * map按键的首字母从小到大排序
     * @param map
     * @return
     */
    private static TreeMap<String,String> sortMap(Map<String,String> map){
        TreeMap<String,String> sortMap = new TreeMap<>(new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

    /**
     * 调起微信APP支付，签名
     * 生成签名
     */
    public static String genPackageSign(Map<String, String> params, String key) {
        //先将map按键排序
        TreeMap<String,String> treeMap = sortMap(params);
        StringBuilder sb = new StringBuilder();
        //拼接成参数字符串
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }
        sb.append("key=");
        sb.append(key);
        //md5加密
        String packageSign = getMessageDigest(sb.toString().getBytes()).toUpperCase();
        return packageSign;
    }

    /**
     * 获取排序后的map字符串
     * @param params
     * @return
     */
    public static String getSortedString(Map<String, String> params){
        //先将map按键排序
        TreeMap<String,String> treeMap = sortMap(params);
        StringBuilder sb = new StringBuilder();
        //拼接成参数字符串
        for (Map.Entry<String, String> entry : treeMap.entrySet()) {
            sb.append(entry.getKey());
            sb.append('=');
            sb.append(entry.getValue());
            sb.append('&');
        }

        return sb.toString().substring(0,sb.length()-1);
    }

    /**
     * md5加密
     *
     * @param buffer
     * @return
     */
    public static String getMessageDigest(byte[] buffer) {
        char hexDigits[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("MD5");
            mdTemp.update(buffer);
            byte[] md = mdTemp.digest();
            int j = md.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (int i = 0; i < j; i++) {
                byte byte0 = md[i];
                str[k++] = hexDigits[byte0 >>> 4 & 0xf];
                str[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(str);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取随机数
     *
     * @return
     */
    public static String genNonceStr() {
        Random random = new Random();
        return getMessageDigest(String.valueOf(random.nextInt(10000)).getBytes());
    }


    /**
     * 获取时间戳
     *
     * @return
     */
    public static long genTimeStamp() {
        return System.currentTimeMillis() / 1000;
    }

    public static class WXPayBuilder {
        public String appId;
        public String partnerId;
        public String prepayId;
        public String packageValue;
        public String nonceStr;
        public String timeStamp;
        public String sign;

        public WXPayUtil build() {
            return new WXPayUtil(this);
        }

        public String getAppId() {
            return appId;
        }

        public WXPayBuilder setAppId(String appId) {
            this.appId = appId;
            return this;
        }

        public String getPartnerId() {
            return partnerId;
        }

        public WXPayBuilder setPartnerId(String partnerId) {
            this.partnerId = partnerId;
            return this;
        }

        public String getPrepayId() {
            return prepayId;
        }

        public WXPayBuilder setPrepayId(String prepayId) {
            this.prepayId = prepayId;
            return this;
        }

        public String getPackageValue() {
            return packageValue;
        }

        public WXPayBuilder setPackageValue(String packageValue) {
            this.packageValue = packageValue;
            return this;
        }

        public String getNonceStr() {
            return nonceStr;
        }

        public WXPayBuilder setNonceStr(String nonceStr) {
            this.nonceStr = nonceStr;
            return this;
        }

        public String getTimeStamp() {
            return timeStamp;
        }

        public WXPayBuilder setTimeStamp(String timeStamp) {
            this.timeStamp = timeStamp;
            return this;
        }

        public String getSign() {
            return sign;
        }

        public WXPayBuilder setSign(String sign) {
            this.sign = sign;
            return this;
        }
    }

    /**
     * 构造签名串
     * @param method  请求方法：GET/POST
     * @param url 获取请求的绝对URL，并去除域名部分得到参与签名的URL。如果请求中有查询参数，
     *            URL末尾应附加有'?'和对应的查询字符串。
     * @param timestamp 获取发起请求时的系统当前时间戳
     * @param nonceStr 请求随机串
     * @param body 请求方法为GET时，报文主体为空。
     * 当请求方法为POST或PUT时，请使用真实发送的JSON报文。
     * 图片上传API，请使用meta对应的JSON报文。
     * @return
     */
    private static String buildMessage(String method, HttpUrl url, long timestamp, String nonceStr, String body) {
        String canonicalUrl = url.encodedPath();
        if (url.encodedQuery() != null) {
            canonicalUrl += "?" + url.encodedQuery();
        }

        return method + "\n"
                + canonicalUrl + "\n"
                + timestamp + "\n"
                + nonceStr + "\n"
                + body + "\n";
    }


    public static String replaceBlank(String str) {
        String dest = "";
        if (str!=null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
            Matcher m = p.matcher(str);
            dest = m.replaceAll("");
        }
        return dest;
    }

}
