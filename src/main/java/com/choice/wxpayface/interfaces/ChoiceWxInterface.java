package com.choice.wxpayface.interfaces;

import android.app.Activity;
import android.content.Context;

public interface ChoiceWxInterface {
    /**
     * 初始化设备信息
     * @param initDeviceCallBack
     */
    void initDevice(InitDeviceCallBack initDeviceCallBack);

    /**
     * 刷脸获取用户信息
     */
    void getUserInfoByWxPayFace(Activity activity,FaceInfoTask faceInfoTask);

    /**
     * 释放微信刷脸app，解除摄像头占用
     */
    void releaseWxPayFace(Context context);
}
