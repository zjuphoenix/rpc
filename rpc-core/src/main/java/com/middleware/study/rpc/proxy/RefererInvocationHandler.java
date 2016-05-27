package com.middleware.study.rpc.proxy;

import com.middleware.study.rpc.core.Referer;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;
import com.middleware.study.rpc.utils.ReflectUtil;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * @author wuhaitao
 * @date 2016/5/26 23:18
 */
public class RefererInvocationHandler<T> implements InvocationHandler {

    private Class<T> clz;
    private Referer<T> referer;

    public RefererInvocationHandler(Class<T> clz, Referer<T> referer) {
        this.clz = clz;
        this.referer = referer;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        RpcRequest request = new RpcRequest();
        request.setRequestId(RequestIdGenerator.getRequestId());
        request.setInterfaceName(clz.getName());
        request.setMethodName(method.getName());
        request.setParamtersType(ReflectUtil.getMethodParamsType(method));
        request.setParamters(args);
        RpcResponse response = referer.call(request);
        if (response.getException() != null){
            throw response.getException();
        }
        return response.getValue();
    }
}
