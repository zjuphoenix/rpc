package com.middleware.study.rpc.demo.client;


import com.middleware.study.rpc.config.RefererConfig;
import com.middleware.study.rpc.demo.api.ClassA;
import com.middleware.study.rpc.demo.api.ClassB;
import com.middleware.study.rpc.demo.api.ComplexService;
import com.middleware.study.rpc.demo.api.HelloWorld;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhaitao
 * @date 2016/5/27 16:14
 */
public class DemoRpcClient {
    public static void main(String[] args) {
        //RefererConfig refererConfig = new RefererConfig(HelloWorld.class, "127.0.0.1", 8888);
        RefererConfig refererConfig = new RefererConfig(ComplexService.class, "127.0.0.1:2181");
        ComplexService complexService = (ComplexService) refererConfig.getRef();
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
        /*for (int i = 0; i < 10; i++) {
            String res = null;
            try {
                res = helloWorld.sayHello("hello"+i);
                System.out.println("client:"+res);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }*/
    }
}
