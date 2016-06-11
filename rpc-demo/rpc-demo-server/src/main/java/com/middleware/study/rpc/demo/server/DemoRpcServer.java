package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.config.ServiceConfig;
import com.middleware.study.rpc.demo.api.ComplexService;

import java.net.UnknownHostException;

/**
 * @author wuhaitao
 * @date 2016/5/27 16:13
 */
public class DemoRpcServer {
    public static void main(String[] args) throws UnknownHostException {
        //HelloWorldImpl instance = new HelloWorldImpl();
        ComplexServiceImpl instance = new ComplexServiceImpl();
        ServiceConfig serviceConfig = new ServiceConfig(ComplexService.class.getName(), instance, 8888, "127.0.0.1:2181");
        serviceConfig.export();
    }
}
