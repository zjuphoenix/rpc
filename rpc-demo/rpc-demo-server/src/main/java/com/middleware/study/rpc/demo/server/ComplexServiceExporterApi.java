package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.annotation.ServiceExporter;
import com.middleware.study.rpc.api.ExporterApi;
import com.middleware.study.rpc.demo.api.ComplexService;

/**
 * @author wuhaitao
 * @date 2016/6/10 0:26
 */
@ServiceExporter(serviceInterface = ComplexService.class, serviceImpl = ComplexServiceImpl.class, exportPort = 8888, registryUrl = "127.0.0.1:2181")
public class ComplexServiceExporterApi implements ExporterApi {
}
