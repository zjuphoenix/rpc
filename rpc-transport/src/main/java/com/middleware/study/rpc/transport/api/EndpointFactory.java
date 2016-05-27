package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/27 12:49
 */
public interface EndpointFactory {
    Server createServer(int port, RequestHandler requestHandler);
    Client createClient(String host, int port);
}
