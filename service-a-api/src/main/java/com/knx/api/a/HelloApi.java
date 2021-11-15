package com.knx.api.a;

import com.knx.common.base.model.WebResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-a", path = "/hello")
public interface HelloApi {

    @GetMapping("success")
    WebResponse<String> success();

    @GetMapping("/error")
    WebResponse<String> error();

    @GetMapping("/delay/{seconds}")
    WebResponse<String> delay(@PathVariable(value = "seconds") Integer seconds);


}
