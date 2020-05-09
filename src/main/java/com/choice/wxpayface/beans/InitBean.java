package com.choice.wxpayface.beans;

import java.util.List;

/**
 * 首页初始化数据
 */
public class InitBean {

    /**
     * callBaseUrl : http://testfacepay.choicehc.cn:9003
     * displayName : 中南大学湘雅医院
     * equipmentCode : XYAO138H6Y8R7P430497325
     * equipmentName : 刘璐骞测试机
     * function : [{"delTab":"0","displatShort":0,"displayName":"核身打印","equipmentCode":"XYAO138H6Y8R7P430497325","equipmentName":"刘璐骞测试机","functionCode":"verifyPrint","functionIco":"hospital/medicalPicture/1573545608539.png","functionName":"核身打印","hospitalCode":"812019102478100045","hospitalName":"中南大学湘雅医院","id":1186,"useState":"1"},{"delTab":"0","displatShort":0,"displayName":"挂号","equipmentCode":"XYAO138H6Y8R7P430497325","equipmentName":"刘璐骞测试机","functionCode":"register","functionIco":"hospital/medicalPicture/1573545585017.png","functionName":"挂号","hospitalCode":"812019102478100045","hospitalName":"中南大学湘雅医院","id":1187,"useState":"1"},{"delTab":"0","displatShort":0,"displayName":"刷脸缴费","equipmentCode":"XYAO138H6Y8R7P430497325","equipmentName":"刘璐骞测试机","functionCode":"facePay","functionIco":"hospital/medicalPicture/1573545541352.png","functionName":"刷脸缴费","hospitalCode":"812019102478100045","hospitalName":"中南大学湘雅医院","id":1188,"useState":"1"},{"delTab":"0","displatShort":0,"displayName":"建档","equipmentCode":"XYAO138H6Y8R7P430497325","equipmentName":"刘璐骞测试机","functionCode":"archiveCreate","functionIco":"hospital/medicalPicture/1573545505841.png","functionName":"建档","hospitalCode":"812019102478100045","hospitalName":"中南大学湘雅医院","id":1189,"useState":"1"}]
     * hospitalCode : 812019102478100045
     * hospitalName : 中南大学湘雅医院
     * parameter : [{"firstValue":"中南大学湘雅医院","id":5,"parameCode":"wechatMerchantName","parameName":"微信支付商户名称","secondValue":""},{"firstValue":"wxd7623d14c8c88a45","id":3,"parameCode":"wechatPayAppid","parameName":"微信支付APPID","secondValue":""},{"firstValue":"1484088332","id":4,"parameCode":"wechatMerchantCode","parameName":"微信支付商户号","secondValue":""},{"firstValue":"","id":6,"parameCode":"wechatApiSignKey","parameName":"微信支付api密钥","secondValue":""}]
     * screen : []
     * screenTab : 0
     * screenTime : 0
     * token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlcXVpcG1lbnRDb2RlIjoiWFlBTzEzOEg2WThSN1A0MzA0OTczMjUiLCJkZWFkVGltZSI6IjE1NzczNDYyMzU3MDQiLCJpYXQiOjE1NzczNDUzMzUsImhvc3BpdGFsQ29kZSI6IjgxMjAxOTEwMjQ3ODEwMDA0NSJ9.n5quvdfUSTIF8nrHZKpw8jIForHOcuktDxE5soN-PGQ
     * webIndex : http://testfacepay.choicehc.cn:9003/index.html?module=ehcCreate_archiveCreate_register_facePa&picSource=img1_img2&stepSecond=100&sleepSecond=100&refreshSecond=100
     */

    private String callBaseUrl;
    private String displayName;
    private String equipmentCode;
    private String equipmentName;
    private String hospitalCode;
    private String hospitalName;
    private String screenTab;
    private int screenTime;
    private String token;
    private String webIndex;
    private List<FunctionBean> function;
    private List<ParameterBean> parameter;
    private List<?> screen;

    public String getCallBaseUrl() {
        return callBaseUrl;
    }

    public void setCallBaseUrl(String callBaseUrl) {
        this.callBaseUrl = callBaseUrl;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEquipmentCode() {
        return equipmentCode;
    }

    public void setEquipmentCode(String equipmentCode) {
        this.equipmentCode = equipmentCode;
    }

    public String getEquipmentName() {
        return equipmentName;
    }

    public void setEquipmentName(String equipmentName) {
        this.equipmentName = equipmentName;
    }

    public String getHospitalCode() {
        return hospitalCode;
    }

    public void setHospitalCode(String hospitalCode) {
        this.hospitalCode = hospitalCode;
    }

    public String getHospitalName() {
        return hospitalName;
    }

    public void setHospitalName(String hospitalName) {
        this.hospitalName = hospitalName;
    }

    public String getScreenTab() {
        return screenTab;
    }

    public void setScreenTab(String screenTab) {
        this.screenTab = screenTab;
    }

    public int getScreenTime() {
        return screenTime;
    }

    public void setScreenTime(int screenTime) {
        this.screenTime = screenTime;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getWebIndex() {
        return webIndex;
    }

    public void setWebIndex(String webIndex) {
        this.webIndex = webIndex;
    }

    public List<FunctionBean> getFunction() {
        return function;
    }

    public void setFunction(List<FunctionBean> function) {
        this.function = function;
    }

    public List<ParameterBean> getParameter() {
        return parameter;
    }

    public void setParameter(List<ParameterBean> parameter) {
        this.parameter = parameter;
    }

    public List<?> getScreen() {
        return screen;
    }

    public void setScreen(List<?> screen) {
        this.screen = screen;
    }

    public static class FunctionBean {
        /**
         * delTab : 0
         * displatShort : 0
         * displayName : 核身打印
         * equipmentCode : XYAO138H6Y8R7P430497325
         * equipmentName : 刘璐骞测试机
         * functionCode : verifyPrint
         * functionIco : hospital/medicalPicture/1573545608539.png
         * functionName : 核身打印
         * hospitalCode : 812019102478100045
         * hospitalName : 中南大学湘雅医院
         * id : 1186
         * useState : 1
         */

        private String delTab;
        private int displatShort;
        private String displayName;
        private String equipmentCode;
        private String equipmentName;
        private String functionCode;
        private String functionIco;
        private String functionName;
        private String hospitalCode;
        private String hospitalName;
        private int id;
        private String useState;

        public String getDelTab() {
            return delTab;
        }

        public void setDelTab(String delTab) {
            this.delTab = delTab;
        }

        public int getDisplatShort() {
            return displatShort;
        }

        public void setDisplatShort(int displatShort) {
            this.displatShort = displatShort;
        }

        public String getDisplayName() {
            return displayName;
        }

        public void setDisplayName(String displayName) {
            this.displayName = displayName;
        }

        public String getEquipmentCode() {
            return equipmentCode;
        }

        public void setEquipmentCode(String equipmentCode) {
            this.equipmentCode = equipmentCode;
        }

        public String getEquipmentName() {
            return equipmentName;
        }

        public void setEquipmentName(String equipmentName) {
            this.equipmentName = equipmentName;
        }

        public String getFunctionCode() {
            return functionCode;
        }

        public void setFunctionCode(String functionCode) {
            this.functionCode = functionCode;
        }

        public String getFunctionIco() {
            return functionIco;
        }

        public void setFunctionIco(String functionIco) {
            this.functionIco = functionIco;
        }

        public String getFunctionName() {
            return functionName;
        }

        public void setFunctionName(String functionName) {
            this.functionName = functionName;
        }

        public String getHospitalCode() {
            return hospitalCode;
        }

        public void setHospitalCode(String hospitalCode) {
            this.hospitalCode = hospitalCode;
        }

        public String getHospitalName() {
            return hospitalName;
        }

        public void setHospitalName(String hospitalName) {
            this.hospitalName = hospitalName;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getUseState() {
            return useState;
        }

        public void setUseState(String useState) {
            this.useState = useState;
        }
    }

    public static class ParameterBean {
        /**
         * firstValue : 中南大学湘雅医院
         * id : 5
         * parameCode : wechatMerchantName
         * parameName : 微信支付商户名称
         * secondValue :
         */

        private String firstValue;
        private int id;
        private String parameCode;
        private String parameName;
        private String secondValue;

        public String getFirstValue() {
            return firstValue;
        }

        public void setFirstValue(String firstValue) {
            this.firstValue = firstValue;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getParameCode() {
            return parameCode;
        }

        public void setParameCode(String parameCode) {
            this.parameCode = parameCode;
        }

        public String getParameName() {
            return parameName;
        }

        public void setParameName(String parameName) {
            this.parameName = parameName;
        }

        public String getSecondValue() {
            return secondValue;
        }

        public void setSecondValue(String secondValue) {
            this.secondValue = secondValue;
        }
    }
}
