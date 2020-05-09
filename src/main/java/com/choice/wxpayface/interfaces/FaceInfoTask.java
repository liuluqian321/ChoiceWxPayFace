package com.choice.wxpayface.interfaces;


import com.choice.wxpayface.beans.UserInfo;

/**
 * 刷脸获取用户信息回调任务
 */
public interface FaceInfoTask {
    void execute(UserInfo userInfo);
    void fail(String errorMessage);
}
