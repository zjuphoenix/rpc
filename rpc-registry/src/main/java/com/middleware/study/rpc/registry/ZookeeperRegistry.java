package com.middleware.study.rpc.registry;

import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.ZkClient;
import java.util.HashSet;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:06
 */
public class ZookeeperRegistry implements RegistryService {
    private ZkClient zkClient;

    public ZookeeperRegistry(String registryUrl) {
        zkClient = new ZkClient(registryUrl);
    }

    @Override
    public void register(String service, String host, int port) {
        String serviceKey = getServiceKey(service);
        String serviceUrl = host+":"+port;
        if (zkClient.exists(serviceKey)){
            zkClient.updateDataSerialized(serviceKey, new DataUpdater<Set<String>>() {
                @Override
                public Set<String> update(Set<String> urls) {
                    if (urls == null){
                        Set<String> newUrls = new HashSet<String>();
                        newUrls.add(serviceUrl);
                        return newUrls;
                    }
                    if (!urls.contains(serviceUrl)){
                        urls.add(serviceUrl);
                    }
                    return urls;
                }
            });
        }
        else{
            zkClient.createPersistent(serviceKey, true);
            Set<String> urls = new HashSet<>();
            urls.add(serviceUrl);
            zkClient.writeData(serviceKey, urls);
            System.out.println("serviceKey:"+serviceKey);
            System.out.println(urls);
        }
    }

    @Override
    public void unregister(String service, String host, int port) {
        String serviceKey = getServiceKey(service);
        String serviceUrl = host+":"+port;
        if (zkClient.exists(serviceKey)){
            zkClient.updateDataSerialized(serviceKey, new DataUpdater<Set<String>>() {
                @Override
                public Set<String> update(Set<String> urls) {
                    if (urls.contains(serviceUrl)){
                        urls.remove(serviceUrl);
                    }
                    return urls;
                }
            });
        }
    }

    private String getServiceKey(String service){
        return "/"+service+"/server";
    }
}
