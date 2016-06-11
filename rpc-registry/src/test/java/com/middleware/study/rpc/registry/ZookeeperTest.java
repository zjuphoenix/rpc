package com.middleware.study.rpc.registry;

import org.I0Itec.zkclient.DataUpdater;
import org.I0Itec.zkclient.IZkChildListener;
import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author wuhaitao
 * @date 2016/6/7 15:25
 */
public class ZookeeperTest {
    private ZkClient zkClient;
    @Before
    public void setUp(){
        int sessionTimeout = 30000;
        int connectionTimeout = 1000;
        zkClient = new ZkClient("127.0.0.1:2181", sessionTimeout, connectionTimeout);
        System.out.println("zookeeper session established!");
    }

    //@Test
    public void test() throws InterruptedException {
        String path = "/zk-book";
        zkClient.subscribeChildChanges(path, new IZkChildListener() {
            @Override
            public void handleChildChange(String parentPath, List<String> currentChilds) throws Exception {
                System.out.println(parentPath+" 's child changed, currentChilds:"+currentChilds);
            }
        });
        zkClient.createPersistent(path);
        Thread.sleep(1000);
        System.out.println(zkClient.exists(path));
        System.out.println(zkClient.getChildren(path));
        zkClient.createPersistent(path+"/c1");
        Thread.sleep(1000);
        zkClient.delete(path+"/c1");
        Thread.sleep(1000);
        zkClient.delete(path);
    }

    //@Test
    public void test2() throws InterruptedException {
        String path = "/zk-book";
        List<String> urls = new ArrayList<>();
        urls.add("123");
        zkClient.createEphemeral(path, urls);
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
                System.out.println("node "+dataPath+" changed, new data:"+data);
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                System.out.println("node "+dataPath+" deleted!");
            }
        });
        System.out.println(zkClient.readData(path).toString());
        zkClient.updateDataSerialized(path, new DataUpdater<List<String>>() {
            @Override
            public List<String> update(List<String> strings) {
                strings.add("456");
                return strings;
            }
        });
        /*urls.add("456");
        zkClient.writeData(path, urls);*/
        Thread.sleep(1000);
        System.out.println(zkClient.readData(path).toString());
        Thread.sleep(1000);
        zkClient.delete(path);
        Thread.sleep(1000);

    }

    //@Test
    public void test3() throws InterruptedException {
        String path = "/com.middleware.study.rpc.demo.api.ComplexService/server";
        zkClient.subscribeDataChanges(path, new IZkDataListener() {
            @Override
            public void handleDataChange(String s, Object o) throws Exception {
                System.out.println("data changed:"+o);
            }

            @Override
            public void handleDataDeleted(String s) throws Exception {
                System.out.println("delete path:"+s);
            }
        });
        System.out.println("create node:"+path);
        zkClient.createPersistent(path, true);
        Thread.sleep(1000);
        Set<String> data = new HashSet<>();
        data.add("123");
        System.out.println("add data:"+data);
        zkClient.writeData(path, data);
        Thread.sleep(1000);
        zkClient.updateDataSerialized(path, new DataUpdater<Set<String>>() {
            @Override
            public Set<String> update(Set<String> strings) {
                strings.add("456");
                System.out.println("update data:"+strings);
                return strings;
            }
        });
        Thread.sleep(1000);
        System.out.println("delete path:"+path);
        zkClient.delete(path);
    }

    @Test
    public void test4(){
        String path = "/com.middleware.study.rpc.demo.api.ComplexService/server";
        zkClient.delete(path);
    }

    @After
    public void stop(){
        zkClient.close();
    }
}
