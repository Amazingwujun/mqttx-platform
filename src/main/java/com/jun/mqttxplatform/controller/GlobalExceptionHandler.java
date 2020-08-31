package com.jun.mqttxplatform.controller;

import com.jun.mqttxplatform.entity.Response;
import com.jun.mqttxplatform.exception.GlobalException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

import static com.jun.mqttxplatform.constants.ResponseCode.*;


/**
 * Controller层全局异常处理器
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {


    /**
     * 自定义异常处理器
     *
     * @param e
     * @return
     */
    @ExceptionHandler(GlobalException.class)
    public Response<Void> globalExceptionHandle(GlobalException e) {
        String msg = e.getMessage();
        log.info(msg, e);

        return Response.fail(SERVICE_BUSY_ERR);
    }

    /**
     * Http方法异常，目前全局使用POST
     *
     * @param e
     * @return
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Response<Void> httpRequestMethodNotSupportedException(HttpRequestMethodNotSupportedException e) {
        log.info(e.getMessage(), e);

        //用户使用的方法
        String method = e.getMethod();
        String supportMethods = "";

        //目前系统支持的方法
        String[] methods = e.getSupportedMethods();

        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                if (i == methods.length - 1) {
                    supportMethods = supportMethods.concat(methods[i]);
                } else {
                    supportMethods = supportMethods.concat(methods[i] + ",");
                }
            }
        }

        return Response.fail(BAD_REQUEST.getCode(),
                "请求方法: " + method + " 不被支持，当前请求支持的方法如下: " + supportMethods);
    }

    /**
     * 数据校检异常
     *
     * @param e
     * @return Response
     */
    @ExceptionHandler(BindException.class)
    public Response<Void> validExceptionHandle(BindException e) {
        log.info(e.getMessage(), e);

        String msg = getMessage(e);

        return Response.fail(BAD_REQUEST.getCode(), msg);
    }

    /**
     * 数据校检异常,当参数含有注解 {@link org.springframework.web.bind.annotation.ResponseBody} 时,
     * <p>
     * {@link org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor} 抛出的异常
     * {@link MethodArgumentNotValidException}
     *
     * @param e
     * @return Response
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Response<Void> methodArgumentNotValidExceptionHandle(MethodArgumentNotValidException e) {
        log.info(e.getMessage(), e);

        String msg = getMessage(e);

        return Response.fail(BAD_REQUEST.getCode(), msg);
    }

    /**
     * 请求参数丢失异常，当方法标记为 <code>@RequestParam(require = true)</code>
     *
     * @param e
     * @return
     */
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public Response<Void> missingServletRequestParameterExceptionHandle(MissingServletRequestParameterException e) {
        log.info(e.getMessage(), e);

        return Response.fail(BAD_REQUEST);
    }

    /**
     * HTTP 消息不可读异常
     *
     * @param e 异常
     * @return
     */
    @ExceptionHandler(HttpMessageNotReadableException.class)
    public Response<Void> httpMessageNotReadableException(HttpMessageNotReadableException e) {
        String message = e.getMessage() == null ? "" : e.getMessage();

        if (message.contains("request body is missing")) {
            return Response.fail(BAD_REQUEST.getCode(), "请求体不能为空");
        }

        return Response.fail(BAD_REQUEST.getCode(), message);
    }

    /**
     * 未知异常捕获，未知的异常直接返回告知客户服务忙,以免暴露内部过多细节
     *
     * @return 服务忙
     */
    @ExceptionHandler(Throwable.class)
    public Response<Void> throwableHandle(Throwable e) {
        String msg = e.getMessage();

        log.error(msg, e);
        return Response.error(SERVER_ERR);
    }

    /**
     * 异常消息获取
     *
     * @param e
     * @return
     */
    @SuppressWarnings("Duplicates")
    private String getMessage(Exception e) {
        if (e instanceof MethodArgumentNotValidException) {
            MethodArgumentNotValidException validException = (MethodArgumentNotValidException) e;

            String message = "";
            List<FieldError> fieldErrors = validException.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else if (e instanceof BindException) {
            BindException bindException = (BindException) e;

            String message = "";
            List<FieldError> fieldErrors = bindException.getBindingResult().getFieldErrors();
            for (FieldError error : fieldErrors) {
                message = error.getDefaultMessage();
            }
            return message;
        } else {
            log.error(e.getMessage(), e);
            return "异常类型无法捕获";
        }
    }
}
