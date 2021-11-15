package com.knx.serviceb.web;

import com.knx.api.a.HelloApi;
import com.knx.common.base.model.WebResponse;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("/multi-step")
@RestController
public class MultiStepController {

    @Resource
    private HelloApi helloApi;

    @GetMapping("success")
    public WebResponse<String> success(){
        return helloApi.success();
    }

    @GetMapping("error")
    public WebResponse<String> error(){
        return helloApi.error();
    }

    @GetMapping("/delay/{seconds}")
    public WebResponse<String> delay(@PathVariable(value = "seconds") Integer seconds){
        return helloApi.delay(seconds);
    }


}
