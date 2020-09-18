package org.bnuz.aedweb.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Leia Liang
 */
@RestController
@Api(tags = "HelloTest")
public class HelloController {
    @GetMapping("/hello")
    @ApiOperation("hello To web测试")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }
}
