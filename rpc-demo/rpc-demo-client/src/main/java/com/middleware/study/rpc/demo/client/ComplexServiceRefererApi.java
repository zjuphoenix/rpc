package com.middleware.study.rpc.demo.client;

import com.middleware.study.rpc.annotation.ServiceReferer;
import com.middleware.study.rpc.api.RefererApi;
import com.middleware.study.rpc.demo.api.ComplexService;

/**
 * @author wuhaitao
 * @date 2016/6/10 0:29
 */
@ServiceReferer(serviceInterface = ComplexService.class, registryUrl = "127.0.0.1:2181")
public class ComplexServiceRefererApi implements RefererApi {
}
