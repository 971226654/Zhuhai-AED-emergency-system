package com.bnuz.aed.common.tools.utils;

import com.bnuz.aed.common.tools.ServerResponse;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author Leia Liang
 * 自定义异常
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CdxException.class)
    @ResponseBody
    public ServerResponse error(CdxException e) {
        return ServerResponse.createByFreeStyle(e.getCode(), e.getMsg());
    }

}
