package com.me.game.util;


import lombok.Data;

/**
 * @author wu_hc 【whuancai@163.com】
 */
@Data
public class Result<T> implements java.io.Serializable {

    private int code;
    private String msg;
    private T data;

    /**
     * @param
     * @desc 自定义code，message，也可以在ResultCodeEnum中提前规定
     */
    public static <T> Result<T> custom(int code, String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        result.setData(data);
        return result;
    }

    /**
     * @param
     * @desc 自定义code，message，也可以在ResultCodeEnum中提前规定
     */
    public static Result<Object> custom(int code, String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }


    public static <T> Result<T> success(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(msg == null ? ResultCodeEnum.SUCCESS.getMessage() : msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(ResultCodeEnum.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static Result<Object> success(String msg) {
        Result<Object> result = new Result<>();
        result.setCode(ResultCodeEnum.SUCCESS.getCode());
        result.setMsg(msg == null ? ResultCodeEnum.SUCCESS.getMessage() : msg);
        return result;
    }

    public static <T> Result<T> fail(String msg, T data) {
        Result<T> result = new Result<>();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(msg == null ? ResultCodeEnum.FAIL.getMessage() : msg);
        result.setData(data);
        return result;
    }

    public static Result<Object> fail(String msg) {
        Result<Object> result = new Result<>();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg(msg == null ? ResultCodeEnum.FAIL.getMessage() : msg);
        return result;
    }

    public static Result<Throwable> error(Throwable e) {
        Result<Throwable> result = new Result<>();
        result.setCode(ResultCodeEnum.FAIL.getCode());
        result.setMsg("服务器繁忙，请稍候再试");
        result.setData(e);
        return result;
    }


}