package com.choice.wxpayface.beans;


import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "xml", strict=false)
public class WxResponse {
    @Element(name = "return_code")
    private String returnCode;
    @Element(name = "return_msg")
    private String returnMsg;
    @Element(name = "nonce_str",required = false)
    private String nonceStr;
    @Element(name = "sign",required = false)
    private String sign;
    @Element(name = "appid",required = false)
    private String appId;
    @Element(name = "mch_id",required = false)
    private String mchId;
    @Element(name = "authinfo",required = false)
    private String authinfo;
    @Element(name = "expires_in",required = false)
    private String expiresIn;
//    @Element(name = "open_id")
//    private String openId;
//    @Element(name = "face_code")
//    private String faceCode;
//    @Element(name = "face_sid")
//    private String faceSid;

    public String getReturnCode() {
        return returnCode;
    }

    public void setReturnCode(String returnCode) {
        this.returnCode = returnCode;
    }

    public String getReturnMsg() {
        return returnMsg;
    }

    public void setReturnMsg(String returnMsg) {
        this.returnMsg = returnMsg;
    }

    public String getNonceStr() {
        return nonceStr;
    }

    public void setNonceStr(String nonceStr) {
        this.nonceStr = nonceStr;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getMchId() {
        return mchId;
    }

    public void setMchId(String mchId) {
        this.mchId = mchId;
    }

    public String getAuthinfo() {
        return authinfo;
    }

    public void setAuthinfo(String authinfo) {
        this.authinfo = authinfo;
    }

    public String getExpiresIn() {
        return expiresIn;
    }

    public void setExpiresIn(String expiresIn) {
        this.expiresIn = expiresIn;
    }

    public String getSign() {
        return sign;
    }

    public void setSign(String sign) {
        this.sign = sign;
    }

    /*public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public String getFaceCode() {
        return faceCode;
    }

    public void setFaceCode(String faceCode) {
        this.faceCode = faceCode;
    }

    public String getFaceSid() {
        return faceSid;
    }

    public void setFaceSid(String faceSid) {
        this.faceSid = faceSid;
    }*/
}
