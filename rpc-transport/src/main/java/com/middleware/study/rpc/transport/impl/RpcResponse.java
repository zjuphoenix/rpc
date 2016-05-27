package com.middleware.study.rpc.transport.impl;


import com.middleware.study.rpc.transport.api.Response;

/**
 * @author wuhaitao
 * @date 2016/5/26 15:15
 */
public class RpcResponse implements Response {

    private Object value;
    private Exception exception;
    private long requestId;

    @Override
    public Object getValue() {
        return value;
    }

    @Override
    public Exception getException() {
        return exception;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public void setException(Exception exception) {
        this.exception = exception;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
