package com.middleware.study.rpc.filter.support;

import com.middleware.study.rpc.core.Provider;
import com.middleware.study.rpc.filter.Filter;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;

import java.util.List;

/**
 * @author wuhaitao
 * @date 2016/6/3 10:15
 */
public class FilterProviderDecorator<T> {
    private Provider<T> provider;
    private List<Filter> filters;

    public FilterProviderDecorator(Provider<T> provider, List<Filter> filters) {
        this.provider = provider;
        this.filters = filters;
    }

    public Provider<T> getFilterProvider(){
        Provider<T> curProvider = provider;
        if (filters != null) {
            for (Filter filter : filters) {
                Provider<T> lastProvider = curProvider;
                curProvider = new Provider<T>() {
                    @Override
                    public Class<T> getInterface() {
                        return lastProvider.getInterface();
                    }

                    @Override
                    public RpcResponse call(RpcRequest request) {
                        return filter.request(lastProvider, request);
                    }
                };
            }
        }
        return curProvider;
    }
}
