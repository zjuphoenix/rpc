package com.middleware.study.rpc.transport.api;

/**
 * @author wuhaitao
 * @date 2016/5/25 22:21
 */
public interface Request {
    String getInterfaceName();
    String methodName();
    String[] getParamtersType();
    Object[] getParamters();
    long getRequestId();
}
