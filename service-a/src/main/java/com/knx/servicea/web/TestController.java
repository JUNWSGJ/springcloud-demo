package com.knx.servicea.web;

import com.knx.common.base.model.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {


    @GetMapping("test")
    WebResponse<?> test1(){
        return WebResponse.returnSuccessData("test1");
    }

    @GetMapping("Test")
    WebResponse<?> test2(){
        return WebResponse.returnSuccessData("test2");
    }
}
