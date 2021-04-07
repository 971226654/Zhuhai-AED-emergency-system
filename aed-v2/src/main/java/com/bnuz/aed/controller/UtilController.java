package com.bnuz.aed.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @author Leia Liang
 */
@Controller
@Api(tags = "Hello&LogTest")
public class UtilController {

    @GetMapping("/hello")
    @ResponseBody
    @ApiOperation("Hello To Two Client测试")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    @RequestMapping("/log")
    @ApiOperation("后台日志输出")
    public String log() {
        return "log-web";
    }

}
