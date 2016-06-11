package com.middleware.study.rpc.config;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.core.impl.RpcCluster;
import com.middleware.study.rpc.proxy.ProxyFactory;
import com.middleware.study.rpc.proxy.RefererInvocationHandler;
import com.middleware.study.rpc.proxy.spi.JdkProxyFactory;
import com.middleware.study.rpc.registry.ZookeeperDiscovery;
import com.middleware.study.rpc.registry.ZookeeperDiscoveryFactory;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:57
 */
public class RefererConfig<T> implements Config {
    private Class<T> clz;
    private T ref;
    private RpcCluster rpcCluster;
    private AtomicBoolean initialized = new AtomicBoolean(false);
    private ZookeeperDiscovery zookeeperDiscovery;

    public RefererConfig() {
    }

    public RefererConfig(Class<T> clz, String registryUrl) {
        this.clz = clz;
        zookeeperDiscovery = new ZookeeperDiscoveryFactory().getDiscovery(registryUrl);
    }

    public T getRef(){
        if (initialized.get() && ref != null){
            return ref;
        }
        Set<String> urls = zookeeperDiscovery.discover(clz.getName());
        rpcCluster = new RpcCluster(clz);
        rpcCluster.init(urls);
        zookeeperDiscovery.subscribe(clz.getName(), rpcCluster);
        ProxyFactory proxyFactory = new JdkProxyFactory();
        /**
         * new RefererInvocationHandler<T>(clz, referer)不能省略泛型T，否则报错
         */
        ref = proxyFactory.getProxy(clz, new RefererInvocationHandler<T>(clz, rpcCluster));
        initialized.set(true);
        return ref;
    }

    public synchronized void destroy() {
        zookeeperDiscovery.unsubscribe(clz.getName(), rpcCluster);
        List<Referer<T>> referers = rpcCluster.getReferers();
        for (Referer<T> referer : referers) {
            referer.destroy();
        }
        ref = null;
        initialized.set(false);
    }

    public Class<T> getClz() {
        return clz;
    }

    public void setClz(Class<T> clz) {
        this.clz = clz;
    }

    public AtomicBoolean getInitialized() {
        return initialized;
    }
}
