package me.jkong.provider.constant;

/**
 * @author JKong
 * @version v1.0
 * @description 返回码枚举类
 * @date 2019/9/17 9:12.
 */
public enum CodeEnum {

    /**
     * 平台错误码
     */
    API_NOT_FOUND("1000", "访问API不存在"),
    MEDIA_NOT_SUPPORT("1001", "content-type只支持(text/xml,text/plain,application/json,application/soap+xml,application/xml,application/x-www-form-urlencoded,multipart/form-data)"),
    MISSING_PARAMETER("1002", "缺少参数"),
    INTERNAL_ERROR("1003", "系统错误"),
    NO_PERMISSION("1004", "没有权限访问"),
    FLOW_LIMIT("1005", "访问超出限制"),
    URL_MATCH_FAILED("1006", "URL匹配失败"),
    API_NOT_ACTIVE("1007", "api未发布"),
    API_REQUEST_ERROR("1008", "请求异常"),
    RESPONSE_PARSE_ERROR("1009", "响应参数解析错误"),
    REQUEST_METHOD_ERROR("1010", "请求方法错误");

    /**
     * 枚举码
     */
    private String code;

    /**
     * 枚举描述
     */
    private String info;

    CodeEnum(String code, String info) {
        this.code = code;
        this.info = info;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }
}