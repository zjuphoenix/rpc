package com.middleware.study.rpc.registry;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:08
 */
public class ZookeeperRegistryFactory implements RegistryFactory {
    @Override
    public ZookeeperRegistry getRegistry(String registryUrl) {
        return new ZookeeperRegistry(registryUrl);
    }
}
