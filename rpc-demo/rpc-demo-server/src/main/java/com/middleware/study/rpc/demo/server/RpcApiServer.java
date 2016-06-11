package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.api.BootExporterApplication;

/**
 * @author wuhaitao
 * @date 2016/6/10 0:25
 */
public class RpcApiServer {
    public static void main(String[] args) {
        BootExporterApplication.export("com.middleware.study.rpc.demo.server");
    }
}
