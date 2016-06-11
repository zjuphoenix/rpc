package com.middleware.study.rpc.config;

import com.middleware.study.rpc.core.Exporter;
import com.middleware.study.rpc.core.Provider;
import com.middleware.study.rpc.core.impl.RpcExporter;
import com.middleware.study.rpc.core.impl.RpcServiceProvider;
import com.middleware.study.rpc.filter.Filter;
import com.middleware.study.rpc.filter.impl.MonitorFilter;
import com.middleware.study.rpc.filter.support.FilterProviderDecorator;
import com.middleware.study.rpc.registry.ZookeeperRegistry;
import com.middleware.study.rpc.registry.ZookeeperRegistryFactory;
import io.netty.channel.local.LocalAddress;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * @author wuhaitao
 * @date 2016/5/27 15:56
 */
public class ServiceConfig<T> implements Config {
    private String service;
    private T instance;
    private int port;
    private String registryUrl;
    private Exporter<T> exporter;
    private AtomicBoolean exported = new AtomicBoolean(false);
    private ZookeeperRegistry zookeeperRegistry;
    private String serviceIP;

    public ServiceConfig() {
    }

    public ServiceConfig(String service, T instance, int port, String registryUrl) {
        this.service = service;
        this.instance = instance;
        this.port = port;
        this.registryUrl = registryUrl;
        zookeeperRegistry = new ZookeeperRegistryFactory().getRegistry(registryUrl);
    }

    public void export() throws UnknownHostException {
        serviceIP = InetAddress.getLocalHost().getHostAddress();
        Provider provider = new RpcServiceProvider(instance);
        List<Filter> filters = new ArrayList<>();
        filters.add(new MonitorFilter());
        FilterProviderDecorator<T> filterProviderDecorator = new FilterProviderDecorator<>(provider, filters);
        exporter = new RpcExporter(filterProviderDecorator.getFilterProvider(), port);
        exporter.init();
        exporter.export();
        exported.set(true);
        zookeeperRegistry.register(service, serviceIP, port);
    }

    public void unexport() throws UnknownHostException {
        exporter.unexport();
        exported.set(false);
        if (serviceIP == null) {
            serviceIP = InetAddress.getLocalHost().getHostAddress();
        }
        zookeeperRegistry.unregister(service, serviceIP, port);
    }

    public T getInstance() {
        return instance;
    }

    public void setInstance(T instance) {
        this.instance = instance;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public AtomicBoolean getExported() {
        return exported;
    }

    public String getRegistryUrl() {
        return registryUrl;
    }

    public void setRegistryUrl(String registryUrl) {
        this.registryUrl = registryUrl;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }
}
