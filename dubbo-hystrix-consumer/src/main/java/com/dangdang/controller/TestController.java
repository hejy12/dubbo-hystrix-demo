package com.dangdang.controller;

import com.alibaba.dubbo.config.annotation.Reference;

import com.dangdang.api.ITestApi;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {
    @Reference(interfaceName="com.dangdang.api.ITestApi")
    private ITestApi testApi;

    @GetMapping("/test/getPerson")
    @ResponseBody
    @HystrixCommand(groupKey="dangdangApi", commandKey = "commandTest",fallbackMethod = "getPersonCallback",
            commandProperties = {
                    @HystrixProperty(name = "execution.isolation.thread.timeoutInMilliseconds", value = "3000"),//指定多久超时，单位毫秒。超时进fallback
                    @HystrixProperty(name = "circuitBreaker.requestVolumeThreshold", value = "1"),//判断熔断的最少请求数，默认是10；只有在一个统计窗口内处理的请求数量达到这个阈值，才会进行熔断与否的判断
                    @HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "2"),//判断熔断的阈值，默认值50，表示在一个统计窗口内有50%的请求处理失败，会触发熔断
            },
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "1"),
                    @HystrixProperty(name = "maxQueueSize", value = "1"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "2"),
                    @HystrixProperty(name = "queueSizeRejectionThreshold", value = "1"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "1"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "1440")
            })
    public Map<String,String> getOnePerson(){
        return testApi.queryOnePerson(1);
    }

    public Map<String,String> getPersonCallback(){
        Map<String, String> m = new HashMap<>();
        m.put("name","zy2");
        return m;
    }
}
