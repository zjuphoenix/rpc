package com.middleware.study.rpc.demo.server;

import com.middleware.study.rpc.demo.api.ClassA;
import com.middleware.study.rpc.transport.api.Serialization;
import com.middleware.study.rpc.transport.impl.FastJsonSerialization;
import com.middleware.study.rpc.transport.impl.MessagePackSerialization;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wuhaitao
 * @date 2016/5/28 23:44
 */
public class SerializationTest {
    private ClassA classA;

    @Before
    public void setUp(){
        Map<Integer, String> map = new HashMap<>();
        map.put(1, "11");
        map.put(2, "22");
        List<String> list = new ArrayList<>();
        list.add("123");
        list.add("456");
        classA = new ClassA(1,"test",map,list);
    }

    @Test
    public void testFastJsonSerialization() throws IOException {
        Serialization serialization = new FastJsonSerialization();
        byte[] bytes = serialization.serialize(classA);
        ClassA ins = serialization.deserialize(bytes, ClassA.class);
        System.out.println(ins);
    }
}
