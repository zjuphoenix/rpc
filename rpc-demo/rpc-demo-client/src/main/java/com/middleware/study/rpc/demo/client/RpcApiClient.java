package com.middleware.study.rpc.demo.client;

import com.middleware.study.rpc.api.BootRefererApplication;
import com.middleware.study.rpc.demo.api.ClassA;
import com.middleware.study.rpc.demo.api.ClassB;
import com.middleware.study.rpc.demo.api.ComplexService;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhaitao
 * @date 2016/6/10 0:25
 */
public class RpcApiClient {
    public static void main(String[] args) {
        BootRefererApplication.init("com.middleware.study.rpc.demo.client");
        ComplexService complexService = (ComplexService) BootRefererApplication.getRef(ComplexService.class);
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "11");
        map.put(2, "22");
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        ClassA classA = new ClassA(1,"test",map,list);
        ClassB classB = new ClassB(123, classA);
        ClassB res = complexService.test(classB);
        System.out.println(res.toString());
    }
}
