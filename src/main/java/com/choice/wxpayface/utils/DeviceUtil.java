package com.choice.wxpayface.utils;

import android.os.Build;

public class DeviceUtil {
    private static String SN;//设备序列号

    /**
     * 获取设备序列号
     * @return
     */
    public static String getSN(){
        if(SN == null){
            SN = Build.SERIAL;
        }
        return SN;
    }
}
