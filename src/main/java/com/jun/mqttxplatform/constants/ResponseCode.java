package com.jun.mqttxplatform.constants;

/**
 * 请求响应枚举
 *
 * @author jun
 * @since 1.0.0
 */
public enum ResponseCode {

    SUCCESS(200, "操作成功"),

    BAD_REQUEST(400, "请求参数异常"),
    UNAUTHORIZED(401, "quan"),
    HTTP_METHOD_ERR(402, "请求方法错误"),
    DUPLICATE_DATA_ERR(403, "权限不足"),
    INFO_NOT_FOUND_ERR(404, "找不到您要的信息"),
    LOGIN_ERR(405, "用户名或密码错误"),
    SERVICE_BUSY_ERR(406, "服务繁忙，请稍后再试"),
    ILLEGAL_ARGS_ERR(407, "参数错误"),

    SERVER_ERR(500, "服务忙");

    private int code;

    private String value;

    ResponseCode(int code, String value) {
        this.code = code;
        this.value = value;
    }

    public int getCode() {
        return code;
    }

    public String getValue() {
        return value;
    }
}
