package com.middleware.study.rpc.transport.api;

import java.io.IOException;

/**
 * @author wuhaitao
 * @date 2016/5/26 13:15
 */
public interface Serialization {

    byte[] serialize(Object obj) throws IOException;

    <T> T deserialize(byte[] bytes, Class<T> clz) throws IOException;
}
