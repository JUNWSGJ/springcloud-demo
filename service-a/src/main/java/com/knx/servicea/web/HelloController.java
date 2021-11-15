package com.knx.servicea.web;


import com.knx.api.a.HelloApi;
import com.knx.common.base.exception.BusinessException;
import com.knx.common.base.exception.code.ErrorCode;
import com.knx.common.base.model.WebResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@Slf4j
@RequestMapping("/hello")
@RestController
public class HelloController implements HelloApi {

    @GetMapping("")
    public WebResponse<String> hello(){
        return WebResponse.returnSuccessData("success");
    }

    @Override
    public WebResponse<String> delay(@PathVariable Integer seconds){
        String uuid = UUID.randomUUID().toString();
        log.info("[{}]process delay request, seconds:{}, start...", uuid, seconds);
        if(seconds>=0){
            try {
                Thread.sleep(seconds* 1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        log.info("[{}]process delay request, seconds:{}, end...", uuid, seconds);
        return WebResponse.returnSuccessWithData("success");
    }


    @Override
    public WebResponse<String> success() {
        return WebResponse.returnSuccessData("success");
    }

    @Override
    public WebResponse<String> error() {
        throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
    }

}
