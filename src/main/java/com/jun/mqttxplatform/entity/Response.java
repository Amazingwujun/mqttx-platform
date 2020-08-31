package com.jun.mqttxplatform.entity;

import com.jun.mqttxplatform.constants.ResponseCode;
import lombok.Data;

@Data
public class Response<T> {

    private static final int SUCCESS = 200, FAILURE = 400, ERROR = 500;

    private String msg;

    private T data;

    private int code;

    private Response() {
    }

    private Response(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    public static <T> Response<T> ok() {
        return new Response<>(SUCCESS, null, null);
    }

    public static <T> Response<T> ok(T data) {
        return new Response<>(SUCCESS, data, null);
    }

    public static <T> Response<T> fail(String msg) {
        return new Response<>(ResponseCode.BAD_REQUEST.getCode(), null, msg);
    }

    public static <T> Response<T> fail(ResponseCode responseCode) {
        return new Response<>(responseCode.getCode(), null, responseCode.getValue());
    }

    public static <T> Response<T> fail(int code, String msg) {
        return new Response<>(code, null, msg);
    }

    public static <T> Response<T> error(ResponseCode responseCode) {
        return new Response<>(responseCode.getCode(), null, responseCode.getValue());
    }
}
