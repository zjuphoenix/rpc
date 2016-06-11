package com.middleware.study.rpc.registry;

import java.util.List;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/7 18:13
 */
public interface DiscoveryService {
    void subscribe(String service, NotifyListener notifyListener);
    void unsubscribe(String service, NotifyListener notifyListener);
    Set<String> discover(String service);
}
