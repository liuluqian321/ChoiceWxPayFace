package com.choice.wxpayface.utils;

import java.util.UUID;

/**
 * 获取不带-的uuid字符串
 * @author duhuo
 * Created by duhuo on 2017/9/4.
 */
public class UuIdUtil {
    public static String getUuid(){
        UUID uuid = UUID.randomUUID();
        String uuidStr =  uuid.toString();
        uuidStr = uuidStr.replace("-", "");
        return uuidStr;
    }
}
