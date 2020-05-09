package com.choice.wxpayface.beans;

import androidx.annotation.NonNull;

import com.alibaba.fastjson.JSONObject;

import java.io.Serializable;

public class ChoiceResponse<T> implements Serializable {
    private T data;
    private int errorCode;
    private String message;
    private boolean success;
    private String refreshToken;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    @NonNull
    @Override
    public String toString() {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("errorCode",errorCode);
        jsonObject.put("message",message);
        jsonObject.put("success",success);
        jsonObject.put("data",data);
        jsonObject.put("refreshToken",refreshToken);
        return jsonObject.toJSONString();
    }
}
