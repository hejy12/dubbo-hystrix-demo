package com.dangdang.api.impl;
import com.alibaba.dubbo.config.annotation.Service;

import com.dangdang.api.ITestApi;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Service(interfaceName="com.dangdang.api.ITestApi",timeout=1000)
public class TestApi implements ITestApi {

    @Override
    public Map<String, String> queryOnePerson(Integer id) {
//        try {
//            TimeUnit.SECONDS.sleep(5000);
//        }catch (InterruptedException e){
//            e.printStackTrace();
//        }
        Map<String, String> m = new HashMap<>();
        m.put("name","zy");
        return m;
    }

}
