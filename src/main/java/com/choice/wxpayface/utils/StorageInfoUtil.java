package com.choice.wxpayface.utils;

/**
 * 用于存储变量值供所有页面使用
 */
public class StorageInfoUtil {
    //设备名称
    public static String equipmentName;
    //医院名称
    public static String hospitalName;
    //医院编码
    public static String hospitalCode;
    //token,每次请求都带上
    public static String token = "";
    //设备编号
    public static String equipmentCode = "";
    //是否是横屏
    public static boolean isLandScape = false;
    //是否是立式机
    public static boolean isUpRight = false;
    //MainActivity是否处于前台
    public static boolean mainIsForeGround = false;
    //医院配置的院内导航地址
    public static String navigationUrl = "";
    //打印小票标志，默认为0：打印  其他：不打印
    public static int printFlag = 0;
    //刷脸认证方式启用
    public static boolean faceWayEnabled;
    //电子健康卡认证方式启用
    public static boolean healthCardWayEnabled;
    //身份证认证方式启用
    public static boolean idCardWayEnabled;
}
