package com.middleware.study.rpc.transport.impl;


import com.middleware.study.rpc.transport.api.Request;

/**
 * @author wuhaitao
 * @date 2016/5/26 14:18
 */
public class RpcRequest implements Request {

    private String interfaceName;
    private String methodName;
    private String[] paramtersType;
    private Object[] paramters;
    private long requestId;

    @Override
    public String getInterfaceName() {
        return interfaceName;
    }

    @Override
    public String methodName() {
        return methodName;
    }

    @Override
    public String[] getParamtersType() {
        return paramtersType;
    }

    @Override
    public Object[] getParamters() {
        return paramters;
    }

    @Override
    public long getRequestId() {
        return requestId;
    }

    public void setInterfaceName(String interfaceName) {
        this.interfaceName = interfaceName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setParamtersType(String[] paramtersType) {
        this.paramtersType = paramtersType;
    }

    public void setParamters(Object[] paramters) {
        this.paramters = paramters;
    }

    public void setRequestId(long requestId) {
        this.requestId = requestId;
    }
}
