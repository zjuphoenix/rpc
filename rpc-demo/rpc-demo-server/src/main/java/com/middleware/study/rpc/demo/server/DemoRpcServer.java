package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.config.ServiceConfig;

/**
 * @author wuhaitao
 * @date 2016/5/27 16:13
 */
public class DemoRpcServer {
    public static void main(String[] args) {
        //HelloWorldImpl instance = new HelloWorldImpl();
        ComplexServiceImpl instance = new ComplexServiceImpl();
        ServiceConfig serviceConfig = new ServiceConfig(instance, 8888);
        serviceConfig.export();
    }
}
