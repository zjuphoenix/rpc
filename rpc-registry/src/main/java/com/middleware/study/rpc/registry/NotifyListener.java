package com.middleware.study.rpc.registry;

import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/7 18:22
 */
public interface NotifyListener {
    /**
     *
     * @param service:服务接口全限定名
     * @param urls:url格式host:port
     */
    void notify(String service, Set<String> urls);
}
