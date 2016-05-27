package com.middleware.study.rpc.utils;


import com.middleware.study.rpc.transport.api.Request;

import java.lang.reflect.Method;

/**
 * @author wuhaitao
 * @date 2016/5/26 22:45
 */
public class ReflectUtil {
    public static String getMethodDesc(Request request){
        StringBuilder sb = new StringBuilder(request.methodName());
        String[] paramsType = request.getParamtersType();
        for (String paramType : paramsType){
            sb.append(paramType);
        }
        return sb.toString();
    }

    public static String getMethodDesc(Method method){
        StringBuilder sb = new StringBuilder(method.getName());
        Class<?>[] paramsType = method.getParameterTypes();
        for (Class<?> paramType : paramsType){
            sb.append(paramType.getName());
        }
        return sb.toString();
    }

    public static String[] getMethodParamsType(Method method){
        Class<?>[] paramsType = method.getParameterTypes();
        String[] paramsTypeStr = new String[paramsType.length];
        for (int i = 0; i < paramsType.length; i++) {
            paramsTypeStr[i] = paramsType[i].getName();
        }
        return paramsTypeStr;
    }
}
