package com.middleware.study.rpc.demo.client;


import com.middleware.study.rpc.config.RefererConfig;
import com.middleware.study.rpc.demo.api.HelloWorld;

/**
 * @author wuhaitao
 * @date 2016/5/27 16:14
 */
public class DemoRpcClient {
    public static void main(String[] args) {
        RefererConfig refererConfig = new RefererConfig(HelloWorld.class, "127.0.0.1", 8888);
        HelloWorld helloWorld = (HelloWorld) refererConfig.getRef();
        for (int i = 0; i < 10; i++) {
            String res = null;
            try {
                res = helloWorld.sayHello("hello"+i);
                System.out.println("client:"+res);
            } catch (Exception e) {
                e.printStackTrace();
            } catch (Throwable t) {
                t.printStackTrace();
            }
        }
    }
}
