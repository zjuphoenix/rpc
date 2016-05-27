package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.demo.api.HelloWorld;

/**
 * @author wuhaitao
 * @date 2016/5/27 16:05
 */
public class HelloWorldImpl implements HelloWorld {
    @Override
    public String sayHello(String msg) throws Exception {
    if (msg.equals("hello3")){
        throw new Exception("test");
    }
    System.out.println("server:"+msg);
    return msg;
}
}
