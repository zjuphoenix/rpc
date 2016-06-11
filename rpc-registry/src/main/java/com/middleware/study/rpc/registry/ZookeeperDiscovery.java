package com.middleware.study.rpc.registry;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import java.util.*;

/**
 * @author wuhaitao
 * @date 2016/6/8 20:12
 */
public class ZookeeperDiscovery implements DiscoveryService {
    private ZkClient zkClient;

    public ZookeeperDiscovery(String registryUrl) {
        zkClient = new ZkClient(registryUrl);
    }

    @Override
    public void subscribe(String service, NotifyListener notifyListener) {
        String serviceKey = getServiceKey(service);
        zkClient.subscribeDataChanges(serviceKey, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                if (o instanceof Set){
                    Set<String> urls = (Set<String>) o;
                    System.out.println("urls:"+urls);
                    notifyListener.notify(service, urls);
                }
                else{
                    System.out.println("urls type not support!");
                }
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("service "+s+" not available");
            }
        });
    }

    @Override
    public void unsubscribe(String service, NotifyListener notifyListener) {
        String serviceKey = getServiceKey(service);
        zkClient.unsubscribeDataChanges(serviceKey, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {

            }

            @Override
            public void handleDataDeleted(String s) throws Exception {

            }
        });
    }

    @Override
    public Set<String> discover(String service) {
        Object res = zkClient.readData(getServiceKey(service));
        System.out.println("discover:"+res);
        if (res == null){
            return new HashSet<>();
        }
        if (res instanceof Set){
            Set<String> urlSet = (Set<String>)res;
            /*List<String> urls = new ArrayList<>();
            Iterator<String> iter = urlSet.iterator();
            while (iter.hasNext()){
                urls.add(iter.next());
            }*/
            return urlSet;
        }
        return new HashSet<>();
    }

    private String getServiceKey(String service){
        return "/"+service+"/server";
    }
}
