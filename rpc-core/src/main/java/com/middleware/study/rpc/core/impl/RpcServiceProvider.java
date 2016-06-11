package com.middleware.study.rpc.core.impl;

import com.middleware.study.rpc.core.Provider;
import com.middleware.study.rpc.transport.exception.ServiceException;
import com.middleware.study.rpc.transport.impl.RpcRequest;
import com.middleware.study.rpc.transport.impl.RpcResponse;
import com.middleware.study.rpc.utils.ReflectUtil;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * @author wuhaitao
 * @date 2016/5/25 20:59
 */
public class RpcServiceProvider<T> implements Provider<T> {

    private T instance;
    private Map<String, Method> methodMap = new HashMap<String, Method>();

    public RpcServiceProvider(T instance) {
        this.instance = instance;
        Method[] methods = instance.getClass().getMethods();
        for (Method method : methods){
            methodMap.put(ReflectUtil.getMethodDesc(method), method);
        }
    }

    @Override
    public Class<T> getInterface() {
        return (Class<T>) instance.getClass();
    }

    @Override
    public RpcResponse call(RpcRequest request) {
        RpcResponse response = new RpcResponse();
        response.setRequestId(request.getRequestId());
        Method method = methodMap.get(ReflectUtil.getMethodDesc(request));
        if (method == null){
            response.setException(new ServiceException("method not found"));
            return response;
        }
        try {
            Object value = method.invoke(instance, request.getParamters());
            response.setValue(value);
        } catch (Exception e) {
            e.printStackTrace();
            response.setException(new ServiceException(e));
        } catch (Throwable t){
            t.printStackTrace();
            response.setException(new ServiceException(t));
        }
        return response;
    }
}
