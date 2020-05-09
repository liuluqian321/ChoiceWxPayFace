package com.choice.wxpayface.utils;

public class StringUtil {
    /**
     * 是否为空
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static Boolean isEmpty(String str) {
        if(str == null || str.equals("")) {
            return true;
        }

        return false;
    }

    /**
     * 是否为空
     * @param str 字符串
     * @return true 空 false 非空
     */
    public static Boolean isNotEmpty(String str) {
        if(str == null || str.equals("")) {
            return false;
        }

        return true;
    }
}
