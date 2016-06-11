package com.middleware.study.rpc.registry;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:09
 */
public interface RegistryFactory {
    ZookeeperRegistry getRegistry(String registryUrl);
}
