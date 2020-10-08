package com.bnuz.aed.common.tools;

/**
 * @author Leia Liang
 * @description 响应码枚举，参考HTTP状态码的语义
 */
public enum ResponseCode {

    // 成功
    SUCCESS(200, "SUCCESS"),
    // 新建或者修改数据成功
    CREATED(201, "CREATED"),
    // 删除数据成功
    NOT_CONTENT(204, "NOT CONTENT"),
    //失败
    FAIL(400, "FAIL"),
    //未认证（签名错误）
    UNAUTHORIZED(401, "UNAUTHORIZED"),
    // 用户访问被禁止
    FORBIDDEN(403, "FORBIDDEN"),
    //接口不存在
    NOT_FOUND(404, "NOT_FOUND"),
    //服务器内部错误
    INTERNAL_SERVER_ERROR(500, "INTERNAL_SERVER_ERROR");

    private final int code;

    private final String desc;

    ResponseCode(int code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public String getDesc() {
        return desc;
    }
}
