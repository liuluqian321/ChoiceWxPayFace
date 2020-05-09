package com.choice.wxpayface.interfaces;

/**
 * 初始化设备回调
 */
public interface InitDeviceCallBack {
    void onSuccess();
    void onFailure(String message);
}
