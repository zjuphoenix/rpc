package com.middleware.study.rpc.registry;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:13
 */
public class ZookeeperDiscoveryFactory implements DiscoveryFactory {
    @Override
    public ZookeeperDiscovery getDiscovery(String registryUrl) {
        return new ZookeeperDiscovery(registryUrl);
    }
}
