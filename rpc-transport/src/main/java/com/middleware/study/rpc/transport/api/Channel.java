package com.middleware.study.rpc.transport.api;

import com.middleware.study.rpc.transport.impl.ResponseFuture;

/**
 * 针对客户端而言
 * @author wuhaitao
 * @date 2016/5/25 23:20
 */
public interface Channel {
    /**
     * 打开通道
     * @return
     */
    boolean open();

    /**
     * 关闭通道
     * @return
     */
    boolean close();

    /**
     * 通道是否关闭
     * @return
     */
    boolean isClosed();

    /**
     * 通道是否可用
     * @return
     */
    boolean isAvailable();

    /**
     * 发送请求
     * @param request
     * @return
     */
    ResponseFuture sendRequest(Request request);
}
