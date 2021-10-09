package com.ruoyi.common.core.domain;

import com.ruoyi.common.constant.HttpStatus;
import io.swagger.annotations.ApiModelProperty;
import java.util.HashMap;

/**
 * 操作消息提醒
 *
 * @author ruoyi
 */
public class Rest<T> {
    public static final String DEFAULT_SUCCESS_MSG = "操作成功";
    public static final String DEFAULT_ERROR_MSG = "操作失败";

    @ApiModelProperty(value = "响应码", example = "200")
    private Integer code;

    @ApiModelProperty(value = "响应消息", example = DEFAULT_SUCCESS_MSG)
    private String msg;

    @ApiModelProperty(value = "响应内容")
    private T data;

    public Rest() {
    }

    public Rest(String msg) {
        this.msg = msg;
    }

    public Rest(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Rest(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    /**
     * 返回成功消息
     *
     * @return 成功消息
     */
    public static Rest success()
    {
        return new Rest<>(HttpStatus.SUCCESS, DEFAULT_SUCCESS_MSG);
    }

    /**
     * 返回成功数据
     *
     * @return 成功消息
     */
//    public Rest<T> success(T data)
//    {
//        this.code = HttpStatus.SUCCESS;
//        this.msg = DEFAULT_SUCCESS_MSG;
//        this.data = data;
//        return this;
//    }

    public static <K> Rest<K> success(K data)
    {
        return new Rest<>(HttpStatus.SUCCESS, DEFAULT_SUCCESS_MSG, data);
    }
    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @return 成功消息
     */
    public static Rest success(String msg)
    {
        return new Rest<>(HttpStatus.SUCCESS, msg);
    }

    /**
     * 返回成功消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 成功消息
     */
    public static <K> Rest<K> success(String msg, K data)
    {
        return new Rest(HttpStatus.SUCCESS, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @return
     */
    public static Rest error()
    {
        return new Rest(HttpStatus.ERROR, DEFAULT_ERROR_MSG);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Rest error(String msg)
    {
        return new Rest(HttpStatus.ERROR, msg);
    }

    /**
     * 返回错误消息
     *
     * @param msg 返回内容
     * @param data 数据对象
     * @return 警告消息
     */
    public static <K> Rest<K> error(String msg, K data)
    {
        return new Rest(HttpStatus.ERROR, msg, data);
    }

    /**
     * 返回错误消息
     *
     * @param code 状态码
     * @param msg 返回内容
     * @return 警告消息
     */
    public static Rest error(int code, String msg)
    {
        return new Rest(code, msg);
    }

    /**
     * 添加值
     * @param key 键
     * @param value 值
     * @return 成功消息
     */
    public Rest<HashMap<String, Object>> put(String key, Object value)
    {
        Rest<HashMap<String, Object>> hashMapRest;
        HashMap<String, Object> dataMap;
        if(null == data){
            hashMapRest = (Rest<HashMap<String, Object>>) this;
            dataMap = new HashMap<>();
            hashMapRest.setData(dataMap);
        }else if (data instanceof  HashMap){
            hashMapRest = (Rest<HashMap<String, Object>>) this;
            dataMap = hashMapRest.getData();
        }else{
            throw new IllegalArgumentException();
        }
        dataMap.put(key,value);
        return hashMapRest;
    }
}
