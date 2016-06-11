package com.middleware.study.rpc.registry;

/**
 * @author wuhaitao
 * @date 2016/6/7 18:17
 */
public interface RegistryService {
    void register(String service, String host, int port);
    void unregister(String service, String host, int port);
    /*void online(String service, String host, int port);
    void offine(String service, String host, int port);*/
}
