package com.middleware.study.rpc.registry;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:14
 */
public interface DiscoveryFactory {
    ZookeeperDiscovery getDiscovery(String registryUrl);
}
