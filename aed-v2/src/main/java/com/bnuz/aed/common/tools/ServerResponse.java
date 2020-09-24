package com.bnuz.aed.common.tools;

import com.alibaba.fastjson.JSON;
import lombok.Getter;
import lombok.Setter;


/**
 * @author Leia Liang
 */
@Getter
@Setter
public class ServerResponse {

    /**
     * 状态码
     */
    private int code;

    /**
     * 消息
     */
    private String message;

    /**
     * 数据
     */
    private Object data;

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }

    public ServerResponse(int code) {
        this.code = code;
    }

    public ServerResponse(int code, Object data) {
        this.code = code;
        this.data = data;
    }

    public ServerResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ServerResponse(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public boolean isSuccess() {
        return this.code == ResponseCode.SUCCESS.getCode();
    }

    /**
     * 成功
     * @return code和默认msg
     */
    public static ServerResponse createBySuccess() {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc());
    }

    /**
     * 成功
     * @param msg 自定义msg
     * @return code和自定义msg
     */
    public static ServerResponse createBySuccess(String msg) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg);
    }

    /**
     * 成功
     * @param data 附带数据
     * @return code和默认msg和数据
     */
    public static ServerResponse createBySuccess(Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), ResponseCode.SUCCESS.getDesc(),data);
    }

    /**
     * 成功
     * @param msg 自定义msg
     * @param data 附带数据
     * @return code和自定义msg和数据
     */
    public static ServerResponse createBySuccess(String msg, Object data) {
        return new ServerResponse(ResponseCode.SUCCESS.getCode(), msg, data);
    }

    /**
     * 失败
     * @return code和默认msg
     */
    public static ServerResponse createByFail() {
        return new ServerResponse(ResponseCode.FAIL.getCode(), ResponseCode.FAIL.getDesc());
    }

    /**
     * 失败
     * @param msg 自定义msg
     * @return code和自定义msg
     */
    public static ServerResponse createByFail(String msg) {
        return new ServerResponse(ResponseCode.FAIL.getCode(), msg);
    }

    /**
     * 自定义创建_1
     * @param code 自定义code
     * @param msg 自定义msg
     * @return 自定义code和自定义msg
     */
    public static ServerResponse createByFreeStyle(int code, String msg) {
        return new ServerResponse(code, msg);
    }

    /**
     * 自定义创建_2
     * @param code 自定义code
     * @param msg 自定义msg
     * @param data 附带数据
     * @return 自定义code和自定义msg和附带数据
     */
    public static ServerResponse createByFreeStyle(int code, String msg, Object data) {
        return new ServerResponse(code, msg, data);
    }

}
